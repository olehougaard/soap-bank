package dk.via.bank.dao;

import dk.via.bank.model.Account;
import dk.via.bank.model.transaction.Transaction;

import java.util.List;

public interface TransactionDAO {
	Transaction read(int transactionId);
	List<Transaction> readAllFor(Account account);
	void create(Transaction transaction);
	void deleteFor(Account account);
}
