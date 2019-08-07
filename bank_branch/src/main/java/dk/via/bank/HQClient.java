package dk.via.bank;

import dk.via.bank.dao.*;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class HQClient implements HeadQuarters {
    private String baseURL;
    private ExchangeRateDAO exchangeRateDAO;
    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;

    public HQClient(String baseURL) {
        this.baseURL = baseURL;
    }

    public HQClient() {
        this("http://localhost:8080/");
    }

    private<T> T createServiceClient(String path, String serviceName, Class<T> targetClass) {
        try {
            URL wsdl = new URL(baseURL + path + "?wsdl");
            QName name = new QName("http://dao.bank.via.dk/", serviceName);
            Service service = Service.create(wsdl, name);
            return service.getPort(targetClass);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized ExchangeRateDAO getExchangeDAO() {
        if (exchangeRateDAO == null) {
            exchangeRateDAO = createServiceClient("exchange-rate", "ExchangeRateDAOServiceService", ExchangeRateDAO.class);
        }
        return exchangeRateDAO;
    }

    @Override
    public synchronized AccountDAO getAccountDAO() {
        if (accountDAO == null) {
            accountDAO = createServiceClient("account", "AccountDAOServiceService", AccountDAO.class);
        }
        return accountDAO;
    }

    @Override
    public synchronized CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = createServiceClient("customer", "CustomerDAOServiceService", CustomerDAO.class);
        }
        return customerDAO;
    }

    @Override
    public synchronized TransactionDAO getTransactionDAO() {
        if (transactionDAO == null) {
            transactionDAO = createServiceClient("transaction", "TransactionDAOServiceService", TransactionDAO.class);
        }
        return transactionDAO;
    }
}
