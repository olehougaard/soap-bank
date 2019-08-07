package dk.via.bank.dao;

import dk.via.bank.model.Account;
import dk.via.bank.model.AccountNumber;
import dk.via.bank.model.Customer;

import java.util.Collection;

public interface AccountDAO {
	public Account create(int regNumber, Customer customer, String currency);
	public Account read(AccountNumber accountNumber);
	public Collection<Account> readAccountsFor(Customer customer);
	public void update(Account account);
	public void delete(Account account);
}
