package chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connect extends Thread {

    public Server s;
    public Socket cleint;
    public InputStream input;
    public OutputStream output;
    public String esm;
    public String ID;
    public String content;
    public String on = "on";

    public Connect(Socket client, Server s) {
        this.cleint = client;
        this.s = s;

        ID = Integer.toString(client.getPort());

        try {
            input = client.getInputStream();
            output = client.getOutputStream();

        } catch (IOException e) {

        }
    }

    @Override
    public void run() {
        boolean flag = false;
        while (!flag) {
            byte[] bcontent = new byte[400];
            try {
                int i = input.read(bcontent);

                if (i == -1) {
                    flag = true;
                    continue;
                }
            } catch (IOException e) {

            }

            String gcontent = new String(bcontent);
            String[] spl = gcontent.split("_");
            esm = spl[0];
            on = spl[1];
            content = new String(bcontent);
            s.send(content.trim(), esm, ID);
        }
    }
}
