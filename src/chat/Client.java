package chat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Client extends JFrame implements Runnable, KeyListener {
    
    InputStream input;
    OutputStream output;
     Socket mySocket;
    JTextField typing;
    JTextField toWho;
    JTextField sending;
    JTextArea typed;
    JButton exit;
    JScrollPane sp;
    
    
    public String name;
    public String state = "on";
    public String s2;
    String s;
    boolean off = true;
     int ID;
    ImageIcon picBack;
    JLabel background;

    public Client() {
        
        this.setLayout(null);
        setSize(800, 800);
        setLocation(200, 200);
        typing = new JTextField();
        typing.addKeyListener(this);
        typing.setSize(500, 70);
        typing.setLocation(120, 670);
        
        sending = new JTextField();
        sending.addKeyListener(this);
        sending.setSize(100,70);
        sending.setLocation(670,300);
   
        toWho = new JTextField();
        toWho.setSize(100, 40);
        toWho.setLocation(670, 70);
        this.getContentPane().add(toWho);
        
        exit = new JButton();
        exit.setSize(90, 50);
        exit.setLocation(680, 600);
        exit.setText("Exit");
        
        name = JOptionPane.showInputDialog(null);
        state = "on";
      
        picBack = new ImageIcon("11.jpg");
        background = new JLabel(picBack);
        background.setSize(800, 800);
        background.setLocation(0, 0);
        add(background);
        typed = new JTextArea();
        typed.setSize(400,400);
        typed.setLocation(110,20);
        getContentPane().add(typing);
        getContentPane().add(sending);
        getContentPane().add(exit);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLayeredPane layered = new JLayeredPane();
        sp= new JScrollPane(typed);
        sp.setSize(400,400);
        sp.setLocation(110,20);
        setContentPane(layered);
        layered.add(background, 1);
        layered.add(toWho, 0);
        layered.add(exit, 0);
        layered.add(typing, 0);
        layered.add(sending,0);
        layered.add(sp,0);
        try {
            mySocket = new Socket(InetAddress.getLocalHost(), 5000);
            input = mySocket.getInputStream();
            output = mySocket.getOutputStream();
        } catch (IOException ex) {
            
        }
       
        ID = mySocket.getPort();
        exit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent event) {
                try {
                    off = !off;
                    if (off == false) {
                        state = "off";
                    }
                    output.write((s2 + "_" + state + "_" + "notsend" + "_" + s).getBytes());
                } catch (IOException ex) {
                   
                }
                exit();
            }
        });
        this.setVisible(true);
    }

    public void exit() {
        this.setVisible(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            s = typing.getText();
            s2 = toWho.getText();
            try {
                output.write((s2 + "_" + state + "_" + "send" + "_" + s).getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }             
            
            typing.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void run() {
        int i = 0;
        while (i != -1) {
            byte[] b = new byte[400];
            try {
                i = input.read(b);
                String gcontent = new String(b);
                String[] spi = gcontent.split("_");
                if (i != -1) {
                    if (spi[2].equals("send") || spi[2].equals("deliverd") ||spi[2].equals("not deliverd")) {
                        {
                            if(spi[2].equals("send"))
                            {
                             typed.append("from "+name+spi[2]+"    message :"+spi[3].trim()+"   lenght of message:");
                             typed.append((new String(Integer.toString(i))).trim()+"\n");
                        }
                            if(spi[2].equals("deliverd") || spi[2].equals("not deliverd"))
                            {
                                //ending.
                              sending.setText(spi[2].trim());
                            }
                        }
                        
                    }
                }
            } catch (IOException ex) {
               
            } 
        }
    }
}
