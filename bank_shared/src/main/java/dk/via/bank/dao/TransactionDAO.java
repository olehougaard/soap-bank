package dk.via.bank.dao;

import dk.via.bank.model.Account;
import dk.via.bank.model.transaction.AbstractTransaction;
import dk.via.bank.model.transaction.Transaction;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface TransactionDAO {
	@WebMethod AbstractTransaction read(int transactionId);
	@WebMethod List<AbstractTransaction> readAllFor(Account account);
	@WebMethod void create(AbstractTransaction transaction);
	@WebMethod void deleteFor(Account account);
}
