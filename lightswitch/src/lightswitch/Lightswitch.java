package lightswitch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class Lightswitch extends Thread {
    private JPanel mainPanel;
    private JButton switchbutton;
    private JLabel statuslabel;
    private Mode lightstatus;
    private enum Mode {
        OFF, ON, NOTCONNECTED
    }
    private static int ID;

    private Socket s;
    private DataOutputStream out;
    private DataInputStream in;

    public Lightswitch() {


        System.out.println("Saying to the lightserver ID: " + ID);
        connectSwitch(ID);

        start();
    }

    protected void connectSwitch(int ID) {
        try {
            //for now everything will happen in ports 50000-50010
            //TODO: only use port 50000 for initializing
            s = new Socket("localhost", 50000 + ID);
            //now we use this port for everything and it depends on the ID

            out = new DataOutputStream(s.getOutputStream());
            out.writeInt(ID);
            out.flush();

            in = new DataInputStream(s.getInputStream());

            int mode = in.readInt();

            System.out.println("Server said that we are " + Mode.values()[mode].name());

            setLightStatus(Mode.values()[mode]);

            System.out.println("Make that button functional");
            switchbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button pressed");
                    sendChange(ID);
                }
            });
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends changed state to the server
     * Server checks if it is different from current state and
     * sends new state back to DataInputStream
     * @param ID
     */
    protected void sendChange(int ID) {
        try {
            if(lightstatus.equals(Mode.OFF)) {
                out.writeInt(1);
            } else {
                out.writeInt(0);
            }

            out.flush();
            System.out.println("Output sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void receiveStatus() {
        //Set default to not connected
        Mode receivedMode = Mode.NOTCONNECTED;
        //TODO: receive status of the light from the server
        try {
            int mode = in.readInt();

            System.out.println("Server said that we are " + Mode.values()[mode].name());

            receivedMode = Mode.values()[mode];
        } catch (IOException e){
            e.printStackTrace();
        }
        //Update view to correspond the received status
        setLightStatus(receivedMode);
    }


//Setter for the status of the light
// Modes are OFF, ON, NOTCONNECTED
    public void setLightStatus(Mode input) {
            if (input == Mode.ON) {
                lightstatus = Mode.ON;
                statuslabel.setText("Lights on");
                statuslabel.setBackground(Color.green);
            }
            else if (input == Mode.OFF){
                lightstatus = Mode.OFF;
                statuslabel.setText("Lights off");
                statuslabel.setBackground(Color.red);
            }
            else if (input == Mode.NOTCONNECTED){
                lightstatus = Mode.NOTCONNECTED;
                statuslabel.setText("Not connected");
                statuslabel.setBackground(Color.yellow);
            }
    }

    public static void main(String[] args) {
        //No need to edit main.
        //ID number is read from th first command line parameter
        if (args.length >0) {
            try {
                ID = Integer.parseInt(args[0]);
            } catch (Exception ex) {
                System.err.println("Error reading arguments");
                ID = 0;
            }
        }
        else {ID = 1; }

        Lightswitch l = new Lightswitch();

        JFrame frame = new JFrame("Lightswitch");
        frame.setContentPane(l.mainPanel);
        frame.setTitle("Lightswitch");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        System.out.println("Main finished...");
    }

    public void run() {
        while (true) {
            try {
                receiveStatus();
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}
