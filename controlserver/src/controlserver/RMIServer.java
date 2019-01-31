package controlserver;

import RemoteServer.clientInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements serverInterface, Serializable {

    private static final long serialVersionUID = 7504653635860151476L;

    private clientInterface client;

    public RMIServer() throws RemoteException {
        super();
        //Constructor
        //You will need Security manager to make RMI work
        //Remember to add security.policy to your run time VM options
        //-Djava.security.policy=[YOUR PATH HERE]\server.policy
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }
        ///Users/rampemus/IdeaProjects/propertymanager/controlserver/src/server.policy

//        try {
//            LocateRegistry.createRegistry(50040);
//        } catch ( RemoteException e) {
//
//        }

//        this.controller = controller;

        ///Users/rampemus/IdeaProjects/propertymanager/out/production/controlserver
        //rmiregistry
    }

    @Override
    public void toggleLightState(int ID) {
//        controller.toggleLightstatus(ID);
    }

    @Override
    public void sendInitStates() {
//        int[] offStates = new int[10];
//        try {
//            client.setStates(offStates);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        System.out.println("Sending init states");
    }

//    public void run() {
//        try {
//            //TODO:listening
//
//            Thread.sleep(100);
//        } catch ( InterruptedException e ) {
//
//        }
//    }
}

