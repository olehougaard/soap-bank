package dk.via.bank;

import dk.via.bank.dao.AccountDAO;
import dk.via.bank.dao.AccountDAOService;
import dk.via.bank.dao.CustomerDAO;
import dk.via.bank.dao.CustomerDAOService;
import dk.via.bank.dao.ExchangeRateDAO;
import dk.via.bank.dao.ExchangeRateDAOService;
import dk.via.bank.dao.HeadQuarters;
import dk.via.bank.dao.TransactionDAO;
import dk.via.bank.dao.TransactionDAOService;

public class RemoteHQ implements HeadQuarters {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "password";
	
	private ExchangeRateDAO exchangeDao;
	private AccountDAOService accountDAO;
	private CustomerDAOService customerDAO;
	private TransactionDAO transactionDAOService;

	public RemoteHQ() {
		exchangeDao = new ExchangeRateDAOService(JDBC_URL, USERNAME, PASSWORD);
		accountDAO = new AccountDAOService(JDBC_URL, USERNAME, PASSWORD);
		transactionDAOService = new TransactionDAOService(accountDAO, JDBC_URL, USERNAME, PASSWORD);
		customerDAO = new CustomerDAOService(JDBC_URL, USERNAME, PASSWORD, accountDAO);
	}

	@Override
	public ExchangeRateDAO getExchangeDAO() {
		return exchangeDao;
	}

	@Override
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	@Override
	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		return transactionDAOService;
	}
}
