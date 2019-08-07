package dk.via.bank.dao;

import dk.via.bank.model.Account;
import dk.via.bank.model.transaction.AbstractTransaction;
import dk.via.bank.model.transaction.Transaction;

import java.util.List;

public interface TransactionDAO {
	AbstractTransaction read(int transactionId);
	List<AbstractTransaction> readAllFor(Account account);
	void create(AbstractTransaction transaction);
	void deleteFor(Account account);
}
