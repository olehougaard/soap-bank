package dk.via.bank.dao;

import dk.via.bank.model.Customer;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface CustomerDAO {
	@WebMethod Customer create(String cpr, String name, String address);
	@WebMethod Customer read(String cpr);
	@WebMethod void update(Customer customer);
	@WebMethod void delete(Customer customer);
}
