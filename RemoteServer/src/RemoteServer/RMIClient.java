package RemoteServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import controlserver.serverInterface;

/**
 * For future use. This implementation is for making socket connection to http-client
 *
 * Not part of the exercise, please ignore
 */
public class RMIClient extends UnicastRemoteObject implements clientInterface, Runnable {

    serverInterface server;
    RemoteServer host;

    public RMIClient(serverInterface server, RemoteServer host) throws RemoteException {
        //Security manager is needed. Remember policy file and VM parameter again.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        this.host = host;

        System.out.println("Host added, everything is fine");

        //TODO: RMI client connection
        this.server = server;
        server.receiveInitStates();

        //TODO: Create needed requests to control server
    }

    @Override
    public void setState(int ID, int state) {
        System.out.println(ID + ":New state is " + state);
    }

    @Override
    public void setStates(int[] states) {
        for ( int i = 0; i < states.length ; i++) {
            System.out.println(i + ":New state is " + states[i]);
        }
    }

    @Override
    public void run() {
        //for future socket listening
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){

        }
    }
}


