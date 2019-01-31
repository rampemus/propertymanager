package RemoteServer;

import controlserver.RMIServer;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import controlserver.serverInterface;

/**
 * Will work as RMI client and HTTP remote
 */
public class RemoteServer {



    public RemoteServer() {
        //TODO: create and start your servers, make connection needed

        try {
            String rmiServerURL = "rmi://localhost/RMIControlServer";
            serverInterface rmiServer = (serverInterface) Naming.lookup(rmiServerURL);

            new Thread(new RMIClient(rmiServer)).start();


        } catch (MalformedURLException | RemoteException | NotBoundException e ) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RemoteServer RS = new RemoteServer();

    }

}
