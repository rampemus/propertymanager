package controlserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import RemoteServer.clientInterface;
import RemoteServer.RMIClient;


public class ControlServer implements Serializable {

    private static final long serialVersionUID = 7213783635860354476L;

    //GUI variables, Do not edit
    private JTextField temperature;
    private JPanel mainPanel;
    private JLabel temperatureLabel;
    private JButton light7;
    private JButton light8;
    private JButton light9;
    private JButton light4;
    private JButton light1;
    private JButton light5;
    private JButton light2;
    private JButton light6;
    private JButton light3;
    //End of GUI variables

    public enum Mode {OFF, ON, NOTCONNECTED}
    private Mode[] lightstatus = new Mode[10];
    private LightswitchServer[] lightservers = new LightswitchServer[10];
    private ConcurrentHashMap<Integer, JButton> lights;

    private RMIServer rmiServer;
    private clientInterface client;

    public ControlServer() {
        //constructor
        light1.addActionListener(new buttonAction());
        light2.addActionListener(new buttonAction());
        light3.addActionListener(new buttonAction());
        light4.addActionListener(new buttonAction());
        light5.addActionListener(new buttonAction());
        light6.addActionListener(new buttonAction());
        light7.addActionListener(new buttonAction());
        light8.addActionListener(new buttonAction());
        light9.addActionListener(new buttonAction());

        lights = new ConcurrentHashMap();
        lights.put(1, light1);
        lights.put(2, light2);
        lights.put(3, light3);
        lights.put(4, light4);
        lights.put(5, light5);
        lights.put(6, light6);
        lights.put(7, light7);
        lights.put(8, light8);
        lights.put(9, light9);

        for ( int i = 0; i < 10; i++) {
            lightstatus[i] = Mode.NOTCONNECTED;
        }

        startServers();

    }

    private class buttonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton tempB = (JButton)e.getSource();
            int ID = Integer.parseInt(tempB.getName());
            toggleLightstatus(ID);
        }

    }

    /**
     * This will send lightStatusArray that has the temperature in slot 0
     * and light with index 1 in slot 1.
     * @return
     */
    public int[] getLightStatusArray() {
        int[] result = new int[10];
        for (int i = 0; i < 9; i++) {
            System.out.println("Light" + i + ":" + lightstatus[i].ordinal());
            result[i+1] = lightstatus[i].ordinal();
        }
        result[0] = Integer.parseInt(temperature.getText());
        return result;
    }

    /**
     * Sets temperature
     * TODO: update temperature in a room also
     * @param value
     */
    public void setTemperature(int value) {
        temperature.setText(value + "");
        System.out.println("New temperature will be" + value);
    }

    public void toggleLightstatus(int ID) {
        int arrayid = ID -1;
        if(lightstatus[arrayid] == Mode.ON) {
            lights.get(ID).setText("Light "+ ID +" OFF");
            lightstatus[arrayid] = Mode.OFF;
            sendLightStatus(ID);
        }
        else if (lightstatus[arrayid] == Mode.OFF) {
            lights.get(ID).setText("Light "+ ID +" ON");
            lightstatus[arrayid] = Mode.ON;
            sendLightStatus(ID);
        } else {
            lights.get(ID).setText("Light "+ ID +" ON");
            lightstatus[arrayid] = Mode.ON;
            sendLightStatus(ID);
        }
        System.out.println("Printing lightstatus-array after update");
        for ( int i = 1; i < 10; i++) {
            System.out.println("lightstatus" + i + ": " + lightstatus[i-1]);
        }
    }

    private void markAsNotConnected(int ID) {
        int arrayid = ID -1;
        lights.get(ID).setText("Light "+ ID +" N/A");
        lightstatus[arrayid] = Mode.NOTCONNECTED;
    }

    /**
     * sends lightstatus to lightswitch that has certain ID
     * @param ID
     */
    public void sendLightStatus(int ID) {
        if ( lightservers[ID].isStarted()) {
            lightservers[ID].sendNewState();
        } else {
            //todo:change status to NOTCONNECTED
            markAsNotConnected(ID);
        }
    }

    public void initLightStatus(int ID) {
        lightstatus[ID-1] = Mode.OFF;
    }
    //Getter for Lightstatus
    public Mode getLightstatus(int ID) {
        return lightstatus[ID-1];
    }


    //Getter for temperature
    public String getTemperature() {
        return temperature.getText();
    }

    private void startServers() {
        //Start socket-servers here
        for ( int i = 0 ; i < lightservers.length; i++) {
            lightservers[i] = new LightswitchServer("localhost", 50000 + i, this);
            lightservers[i].start();
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        //TODO: Start RMI-server
        try {
            RMIServer server = new RMIServer(this);
            Naming.rebind("RMIControlServer", server);
        } catch (MalformedURLException | RemoteException e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        //No need to edit main method, start your servers in  startServers() method

        JFrame frame = new JFrame("Controlserver");
        frame.setContentPane(new ControlServer().mainPanel);
        frame.setTitle("Controlserver");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}

