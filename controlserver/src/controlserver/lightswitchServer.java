package controlserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LightswitchServer extends Thread implements Serializable {

    private static final long serialVersionUID = 7213283645866351676L;

    private String ip;
    private int port;
    private ServerSocket ss;

    private ControlServer controller;
    private Socket client;
    private int lightID;
    private boolean started;

    private DataInputStream in;
    private DataOutputStream out;

    public LightswitchServer(String IP, int port, ControlServer controlserver) {
        //constructor, create server here and bind it to IP & port
        this.ip = IP;
        this.port = port;
        this.controller = controlserver;
    }

    private void initializeSocket() {
        try {
            System.out.println("Starts to listen port: " + port);

            ss = new ServerSocket(port);

            client = ss.accept();
            System.out.println("client connected");

            in = new DataInputStream(client.getInputStream());

            lightID = in.readInt();

            //TODO:clean this one:

            System.out.println("client says to server that it has ID: " + lightID);

            out = new DataOutputStream(client.getOutputStream());

            controller.initLightStatus(lightID);

            System.out.println("We should give the light status: " + controller.getLightstatus(lightID));

            out.writeInt(controller.getLightstatus(lightID).ordinal());
            out.flush();

            started = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listens when the server sends new state as integer:
     * 0: OFF
     * 1: ON
     * 2: NOT_CONNECTED
     */
    private void listenSocket() {
        try {
            int newState = in.readInt();

            if (controller.getLightstatus(lightID).ordinal() != newState) {
                controller.toggleLightstatus(lightID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch ( InterruptedException e) {}
    }

    public void run() {
        while (!started) {
            initializeSocket();
        }
        while (started) {
            listenSocket();
        }
    }

    /**
     * Recieves automaticly new state from controlserver and sends it
     */
    public void sendNewState() {
        try {
            out.writeInt(controller.getLightstatus(lightID).ordinal());
            out.flush();
        } catch (IOException e){}

        System.out.println("Sent the new light status " + controller.getLightstatus(lightID));
    }
}
