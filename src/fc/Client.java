package fc;

import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Please input the port number");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);

        // Create a connection to the server
        System.out.println("Connecting to the server");
        Socket sock = new Socket("localhost", port);

        System.out.println("Connected!");

        Console cons = System.console();

        // Get the output stream
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        // Get the input stream (to retrieve cookie from server)
        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        while (true) {
            // Write a message to the server
            String theMessage = cons.readLine("command to server: ");

            // Send the command out
            bw.write(theMessage + "\n");
            bw.flush();

            // Read cookie from server
            String fromServer = br.readLine();

            int firstIndex = fromServer.indexOf(" ") + 1;

            String cookie = fromServer.substring(firstIndex);
            System.out.println(cookie);

            if (theMessage.equals("close"))
                break;
        }

        sock.close();
        System.out.println("Connection closed by client");
    }

}
