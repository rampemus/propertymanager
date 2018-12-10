
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Lightswitch {
    private JPanel mainPanel;
    private JButton switchbutton;
    private JLabel statuslabel;
    private Mode lightstatus;
    private enum Mode {OFF, ON, NOTCONNECTED}
    private static int ID;

    public Lightswitch() {
        switchbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendChange(ID);
            }
        });

        connectSwitch(ID);
    }

    protected void connectSwitch(int ID) {
        //TODO: Create an socket connection connection to server

    }

    protected void sendChange(int ID) {} {
        //TODO: Send lightswitch action pressed to server

    }

    protected void receiveStatus() {
        //Set default to not connected
        Mode receivedMode = Mode.NOTCONNECTED;
        //TODO: receive status of the light from the server


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
        else {ID = 0; }

        JFrame frame = new JFrame("Lightswitch");
        frame.setContentPane(new Lightswitch().mainPanel);
        frame.setTitle("Lightswitch");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
