package RemoteServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface clientInterface extends Remote {
     void setState(int ID, int state) throws RemoteException;
     void setStates(int[] state) throws RemoteException;
}