package dk.via.bank.dao;

import dk.via.bank.model.ExchangeRate;

public interface ExchangeRateDAO {
	ExchangeRate getExchangeRate(String fromCurrency, String toCurrency);
}
