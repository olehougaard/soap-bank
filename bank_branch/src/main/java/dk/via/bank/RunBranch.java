package dk.via.bank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dk.via.bank.dao.HeadQuarters;

public class RunBranch {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(1099);
		HeadQuarters hq = (HeadQuarters) registry.lookup("HQ");
	}
}
