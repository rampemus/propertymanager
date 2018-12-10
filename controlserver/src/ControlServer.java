import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlServer {

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
    private enum Mode {OFF, ON, NOTCONNECTED}

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
            startServers();
    }

    private class buttonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton tempB = (JButton)e.getSource();
            int ID = Integer.parseInt(tempB.getName());
            if(tempB.getText().compareTo("Light "+ tempB.getName() +" ON") == 0) {
                tempB.setText("Light "+ tempB.getName() +" OFF");
                sendLightStatus(ID, Mode.OFF);
            }
            else if (tempB.getText().compareTo("Light "+ tempB.getName() +" OFF") == 0) {
                tempB.setText("Light "+ tempB.getName() +" ON");
                sendLightStatus(ID, Mode.ON);
            }
            else {
                tempB.setText("Light "+ tempB.getName() +" ON");
                sendLightStatus(ID, Mode.ON);
            }
        }

    }

    public void sendLightStatus(int ID, Mode input) {
        //TODO: Send change to lightswitches

    }

    public String getTemperature() {
        return temperature.getText();
    }

    private void startServers() {
        //TODO: Start your RMI- and socket-servers here
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

