package chat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.ArrayList;

/**
 *
 * @author BAHARAN1
 */
public class Login extends JFrame {

    private JButton signUp = new JButton();
    private JButton signIn = new JButton();
    String getName =  null;
    JTextField check = new JTextField();
    private ImageIcon picBack;
    private JLabel background;

    public Login() {
        picBack = new ImageIcon("10.jpg");
        background = new JLabel(picBack);
        background.setSize(400, 400);
        background.setLocation(0, 0);
        add(background);
        
        this.setLayout(null);
        this.setSize(400, 400);
        this.setLocation(700, 250);
        this.setResizable(false);
        
        signIn.setSize(90, 50);
        signIn.setLocation(60, 150);      
        signIn.setText("sign in");       
        signIn.setBackground(Color.pink);
        getContentPane().add(signIn); 
        
        ArrayList<Client> ID = new ArrayList<Client>();
        signIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Client td = new Client();
                (new Thread(td)).start();
                ID.add(td);
            }
        });
        
        signUp.setSize(90, 50);
        signUp.setLocation(250, 150);
        signUp.setText("sign Up");
        signUp.setBackground(Color.pink);
        getContentPane().add(signUp);
        
        signUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                getName = JOptionPane.showInputDialog(null);

                for (int i = 0; i < ID.size(); i++) {
                    if (getName.equals(ID.get(i).name)) {
                        if (ID.get(i).off == false) {
                            try {
                                ID.get(i).off = !ID.get(i).off;
                                if (ID.get(i).off == true) {
                                    ID.get(i).state = "on";
                                }
                                ID.get(i).output.write((ID.get(i).s2 + "_" + ID.get(i).state + "_" + "notsend" + "_" + ID.get(i).s).getBytes());
                                ID.get(i).setVisible(true);
                            } catch (IOException ex) {

                            }
                        }
                    }
                }
            }
        });

        JLayeredPane layered = new JLayeredPane();
        setContentPane(layered);
        layered.add(background, 1);
        layered.add(signIn, 0);
        layered.add(signUp, 0);
        
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}
