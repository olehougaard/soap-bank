package dk.via.bank;

import dk.via.bank.model.Account;
import dk.via.bank.model.AccountNumber;
import dk.via.bank.model.Customer;
import dk.via.bank.model.Money;
import dk.via.bank.model.transaction.AbstractTransaction;

import java.util.Collection;
import java.util.List;

public interface Branch {
	Customer createCustomer(String cpr, String name, String address);
	Customer getCustomer(String cpr);
	Account createAccount(Customer customer, String currency);
	Account getAccount(AccountNumber accountNumber);
	void cancelAccount(Account account);
	Collection<Account> getAccounts(Customer customer);
	void execute(AbstractTransaction t);
	Money exchange(Money amount, String targetCurrency);
	List<AbstractTransaction> getTransactionsFor(Account account);
}
