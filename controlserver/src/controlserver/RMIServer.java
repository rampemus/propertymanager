package controlserver;

import RemoteServer.clientInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements serverInterface {

//    private static final long serialVersionUID = 7504653635860151476L;

    private clientInterface client;
    private ControlServer controller;

    public RMIServer(ControlServer controller) throws RemoteException {
        super();
        this.controller = controller;

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
    public int[] receiveInitStates() {
//        int[] offStates = new int[10];
//        try {
//            client.setStates(offStates);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
        System.out.println("Sending init states");
        int[] result = controller.getLightStatusArray();
        for ( int i = 0; i < 10; i++) {
            System.out.print("State" + i + "=" + result[i] + "&");
        }
        return result;
    }

    @Override
    public int[] updateStates(int[] states) {
        int[] oldStates = controller.getLightStatusArray();

        if ( oldStates[0] != states[0] && states[0] != Integer.MIN_VALUE) controller.setTemperature(states[0]);

        for ( int i = 1; i < 10; i++) {
            if ( oldStates[i] != states[i]) {

                if ( controller.getLightstatus(i).ordinal() != 2) {
                    System.out.println("Toggle light number " + i);
                    controller.toggleLightstatus(i);
                }
            }
        }

        return controller.getLightStatusArray();
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

