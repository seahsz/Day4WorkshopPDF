package fc;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket sock;
    private Cookie cookie;

    public ClientHandler(Socket sock, Cookie cookie) {
        this.sock = sock;
        this.cookie = cookie;
    }

    @Override
    public void run() {

        try {
            // Open input stream to get input from Client
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            // Open output stream to return cookie to Client
            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            while (true) {
                // Get input from Client
                String input = br.readLine();

                if (input == null || input.equals("close")) {
                    bw.write("Server is closing\n");
                    bw.flush();
                    sock.close();
                    break; // exit inner loop to accept a new connection
                }

                if (input.equals("get-cookie")) {
                    bw.write(cookie.returnCookie() + "\n");
                    bw.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
