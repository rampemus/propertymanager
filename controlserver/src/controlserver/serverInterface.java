package controlserver;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface serverInterface extends Remote, Serializable {

    static final long serialVersionUID = 7219743635560256476L;

    //TODO: refactor to be int[] receiveInitStates() and get return value
    int[] receiveInitStates() throws RemoteException;
    int[] updateStates(int[] states) throws RemoteException;
}
