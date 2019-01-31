package controlserver;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface serverInterface extends Remote, Serializable {

    static final long serialVersionUID = 7219743635560256476L;

    void toggleLightState(int ID) throws RemoteException;

    //TODO: refactor to be int[] recieveInitStates() and get return value
    void sendInitStates() throws RemoteException;
}
