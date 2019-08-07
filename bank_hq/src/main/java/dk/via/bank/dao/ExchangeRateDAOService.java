package dk.via.bank.dao;

import dk.via.bank.model.ExchangeRate;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class ExchangeRateDAOService implements ExchangeRateDAO {
	private String jdbcURL;
	private String username;
	private String password;

	public ExchangeRateDAOService(String jdbcURL, String username, String password) {
		this.jdbcURL = jdbcURL;
		this.username = username;
		this.password = password;
	}

	@WebMethod
	public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) {
		DatabaseHelper<BigDecimal> helper = new DatabaseHelper<>(jdbcURL, username, password);
		BigDecimal rate = helper.mapSingle((rs)->rs.getBigDecimal(1), "SELECT rate FROM Exchange_rates WHERE from_currency = ? AND to_currency = ?", fromCurrency, toCurrency);
		return new ExchangeRate(fromCurrency, toCurrency, rate);
	}
}
