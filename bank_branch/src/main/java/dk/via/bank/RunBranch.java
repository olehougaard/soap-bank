package dk.via.bank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dk.via.bank.dao.HeadQuarters;

import javax.xml.ws.Endpoint;

public class RunBranch {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		HeadQuarters hq = new HQClient();
		RemoteBranch branch = new RemoteBranch(1234, hq);
		Endpoint.publish("http://localhost:8090/branch", branch);
	}
}
