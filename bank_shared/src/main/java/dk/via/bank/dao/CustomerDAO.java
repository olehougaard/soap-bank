package dk.via.bank.dao;

import dk.via.bank.model.Customer;

public interface CustomerDAO {
	Customer create(String cpr, String name, String address);
	Customer read(String cpr);
	void update(Customer customer);
	void delete(Customer customer);
}
