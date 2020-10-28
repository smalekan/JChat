package chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    InputStream input;
    OutputStream output;
    ServerSocket ss;
    String st;
    Vector<Connect> v;

    public Server() {
        v = new Vector<Connect>();
        try {
            ss = new ServerSocket(5000);
        } catch (IOException e) {
         
        }
    }

    private void execute() {

        while (true) {

            try {

                Socket client = ss.accept();
                Connect w = new Connect(client, this);
                System.out.print(client.getPort());
                v.addElement(w);
                w.start();

            } catch (IOException e) {
                
            }
        }
    }

    public synchronized void send(String msg, String esm, String ID) {
        for (int i = 0; i < v.size(); i++) {
            try {
                if (esm.equals(v.get(i).ID)) {
                    v.get(i).output.write(msg.getBytes());
                    for (int j = 0; j < v.size(); j++) {
                        if (ID.equals(v.get(j).ID)) {
                            try {
                                if(v.get(i).on.equals("on"))
                                   st ="deliverd";
                                if(v.get(i).on.equals("off"))
                                   st ="not deliverd";
                                v.get(j).output.write((v.get(i).on + "_" + " " + "_" +st + "_" + " ").getBytes());
                            } catch (IOException e) {
                              
                            }
                        }
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    public static void main(String[] args) {
        (new Server()).execute();
    }
}