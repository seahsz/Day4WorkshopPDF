package fc;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Please input both the port number & the cookie file path");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);
        String filePath = args[1];

        Cookie cookie = new Cookie(filePath);
        cookie.openFile();

        // Open Server Port
        ServerSocket server = new ServerSocket(port);

        while (true) {

            // Wait for connection
            System.out.println("Waiting for connection");
            Socket sock = server.accept();
            System.out.println("New connection!");

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
                    break;  // exit inner loop to accept a new connection
                }

                if (input.equals("get-cookie")) {
                    bw.write(cookie.returnCookie() + "\n");
                    bw.flush();
                }

            }
        }

    }

}
