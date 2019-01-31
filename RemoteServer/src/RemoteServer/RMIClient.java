package RemoteServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import controlserver.serverInterface;

public class RMIClient extends UnicastRemoteObject implements clientInterface, Runnable {

    serverInterface server;
    clientInterface comp;

    public RMIClient(serverInterface server) throws RemoteException {
        //Security manager is needed. Remember policy file and VM parameter again.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        //TODO: RMI client connection
        this.server = server;
        server.sendInitStates();

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
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){

        }
    }
}


