package dk.via.bank.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import dk.via.bank.model.Account;
import dk.via.bank.model.Customer;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class CustomerDAOService implements CustomerDAO {
	private DatabaseHelper<Customer> helper;
	private AccountDAO accountDAO;
	
	public CustomerDAOService(String jdbcURL, String username, String password, AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
		this.helper = new DatabaseHelper<>(jdbcURL, username, password);
	}
	
	private static class CustomerMapper implements DataMapper<Customer> {
		@Override
		public Customer create(ResultSet rs) throws SQLException {
			String cpr = rs.getString("cpr");
			String name = rs.getString("name");
			String address = rs.getString("address");
			return new Customer(cpr, name, address);
		}
	}
	
	@WebMethod
	public Customer create(String cpr, String name, String address) {
		helper.executeUpdate("INSERT INTO Customer VALUES (?, ?, ?)", cpr, name, address);
		return new Customer(cpr, name, address);
	}

	@WebMethod
	public Customer read(String cpr) {
		CustomerMapper mapper = new CustomerMapper();
		Customer customer = helper.mapSingle(mapper, "SELECT * FROM Customer WHERE cpr = ?;", cpr);
		Collection<Account> accounts = accountDAO.readAccountsFor(customer);
		for(Account account: accounts) {
			customer.addAccount(account);
		}
		return customer;
	}

	@WebMethod
	public void update(Customer customer) {
		helper.executeUpdate("UPDATE Customer set name = ?, address = ? WHERE cpr = ?", customer.getName(), customer.getAddress(), customer.getCpr());
	}

	@WebMethod
	public void delete(Customer customer) {
		helper.executeUpdate("DELETE FROM Customer WHERE cpr = ?", customer.getCpr());
	}
}
