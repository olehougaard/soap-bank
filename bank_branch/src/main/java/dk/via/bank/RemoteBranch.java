package dk.via.bank;

import dk.via.bank.dao.*;
import dk.via.bank.model.*;
import dk.via.bank.model.transaction.*;

import java.util.Collection;
import java.util.List;

public class RemoteBranch implements Branch, TransactionVisitor {
	private int regNumber;
	private AccountDAO accountDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private ExchangeRateDAO exchangeDAO;
	
	public RemoteBranch(int regNumber, HeadQuarters hq) {
		this.regNumber = regNumber;
		this.accountDAO = hq.getAccountDAO();
		this.customerDAO = hq.getCustomerDAO();
		this.transactionDAO = hq.getTransactionDAO();
		this.exchangeDAO = hq.getExchangeDAO();
	}

	@Override
	public Customer createCustomer(String cpr, String name, String address) {
		return customerDAO.create(cpr, name, address);
	}

	@Override
	public Customer getCustomer(String cpr) {
		return customerDAO.read(cpr);
	}

	@Override
	public Account createAccount(Customer customer, String currency) {
		return accountDAO.create(regNumber, customer, currency);
	}

	@Override
	public Account getAccount(AccountNumber accountNumber) {
		return accountDAO.read(accountNumber);
	}
	
	@Override
	public void cancelAccount(Account account) {
		accountDAO.delete(account);
	}

	@Override
	public Collection<Account> getAccounts(Customer customer) {
		return accountDAO.readAccountsFor(customer);
	}
	
	@Override
	public void execute(AbstractTransaction t) {
		t.accept(this);
		transactionDAO.create(t);
	}
	
	private Money translateToSettledCurrency(Money amount, Account account) {
		if (!amount.getCurrency().equals(account.getSettledCurrency())) {
			ExchangeRate rate = exchangeDAO.getExchangeRate(amount.getCurrency(), account.getSettledCurrency());
			amount = rate.exchange(amount);
		}
		return amount;
	}

	@Override
	public void visit(DepositTransaction transaction) {
		Account account = transaction.getAccount();
		Money amount = transaction.getAmount();
		amount = translateToSettledCurrency(amount, account);
		account.deposit(amount);
		accountDAO.update(account);
	}
	
	@Override
	public void visit(WithdrawTransaction transaction) {
		Account account = transaction.getAccount();
		Money amount = transaction.getAmount();
		amount = translateToSettledCurrency(amount, account);
		account.withdraw(amount);
		accountDAO.update(account);
	}
	
	@Override
	public void visit(TransferTransaction transaction) {
		visit(transaction.getDepositTransaction());
		visit(transaction.getWithdrawTransaction());
	}
	
	@Override
	public Money exchange(Money amount, String targetCurrency) {
		if (targetCurrency.equals(amount.getCurrency()))
			return amount;
		ExchangeRate rate = exchangeDAO.getExchangeRate(amount.getCurrency(), targetCurrency);
		return rate.exchange(amount);
	}
	
	@Override
	public List<AbstractTransaction> getTransactionsFor(Account primaryAccount) {
		return transactionDAO.readAllFor(primaryAccount);
	}
}
