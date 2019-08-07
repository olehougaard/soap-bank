package dk.via.bank.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import dk.via.bank.model.Account;
import dk.via.bank.model.AccountNumber;
import dk.via.bank.model.Customer;
import dk.via.bank.model.Money;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.*;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class AccountDAOService implements AccountDAO {
	private DatabaseHelper<Account> helper;
	
	public AccountDAOService(String jdbcURL, String username, String password) {
		helper = new DatabaseHelper<>(jdbcURL, username, password);
	}

	@WebMethod
	public Account create(int regNumber, Customer customer, String currency) {
		final List<Integer> keys = helper.executeUpdateWithGeneratedKeys("INSERT INTO Account(reg_number, customer, currency) VALUES (?, ?, ?)", 
				regNumber, customer.getCpr(), currency);
		return read(new AccountNumber(regNumber, keys.get(0)));
	}
	
	public static class AccountMapper implements DataMapper<Account>{
		@Override
		public Account create(ResultSet rs) throws SQLException {
			AccountNumber accountNumber = new AccountNumber(rs.getInt("reg_number"), rs.getLong("account_number"));
			BigDecimal balance = rs.getBigDecimal("balance");
			String currency = rs.getString("currency");
			return new Account(accountNumber, new Money(balance, currency));
		}
		
	}

    @WebMethod
    public Account read(AccountNumber accountNumber) {
		return helper.mapSingle(new AccountMapper(), "SELECT * FROM Account WHERE reg_number = ? AND account_number = ? AND active", 
				accountNumber.getRegNumber(), accountNumber.getAccountNumber());
	}

    @WebMethod
    public Collection<Account> readAccountsFor(Customer customer) {
		return helper.map(new AccountMapper(), "SELECT * FROM Account WHERE customer = ? AND active", customer.getCpr()) ;
	}

    @WebMethod
    public void update(Account account) {
		helper.executeUpdate("UPDATE ACCOUNT SET balance = ?, currency = ? WHERE reg_number = ? AND account_number = ? AND active", 
				account.getBalance().getAmount(), account.getSettledCurrency(), account.getAccountNumber().getRegNumber(), account.getAccountNumber().getAccountNumber());
	}

    @WebMethod
    public void delete(Account account) {
		helper.executeUpdate("UPDATE ACCOUNT SET active = FALSE WHERE reg_number = ? AND account_number = ?", 
				account.getAccountNumber().getRegNumber(), account.getAccountNumber().getAccountNumber());
	}
}
