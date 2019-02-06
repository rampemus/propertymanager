package RemoteServer;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * For future use to make socket connection to the http-client
 * so that web form will be updated if light states are changed
 * from control server.
 *
 * This is not part of excersize... please ignore
 */
public interface clientInterface extends Remote {
     void setState(int ID, int state) throws RemoteException;
     void setStates(int[] state) throws RemoteException;
}