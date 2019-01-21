package remoteserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface remoteInterface extends Remote {
     String executeTask(String id)  throws RemoteException;
}