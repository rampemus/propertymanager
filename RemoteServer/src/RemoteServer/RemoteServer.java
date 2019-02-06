package RemoteServer;

import controlserver.RMIServer;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import controlserver.serverInterface;

/**
 * Will work as RMI client and HTTP remote
 */
public class RemoteServer {

    private WWWServer wwwServer;
    private serverInterface rmiServer;

    public RemoteServer() {
        //TODO: create and start your servers, make connection needed
        startRMIServer();
        try {
            wwwServer = new WWWServer(new InetSocketAddress(8888),rmiServer);
            System.out.println("wwwServer is on now");
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void startRMIServer() {
        try {
            String rmiServerURL = "rmi://localhost/RMIControlServer";
            rmiServer = (serverInterface) Naming.lookup(rmiServerURL);

//            For future socket connections:
            new Thread(new RMIClient(rmiServer,this)).start();


        } catch (MalformedURLException | RemoteException | NotBoundException e ) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RemoteServer RS = new RemoteServer();

    }

}
