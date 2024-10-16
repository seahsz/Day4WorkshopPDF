package fc;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class MultiServer {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Please input both the port number & the cookie file path");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);
        String filePath = args[1];

        // Thread pool
        ExecutorService threadpool = Executors.newFixedThreadPool(5);

        Cookie cookie = new Cookie(filePath);
        cookie.openFile();

        // Open Server Port
        ServerSocket server = new ServerSocket(port);

        while (true) {

            // Wait for connection
            System.out.println("Waiting for connection");
            Socket sock = server.accept();
            System.out.println("New connection!");

            // Giving the handler the task
            ClientHandler handler = new ClientHandler(sock, cookie);

            // Send the handler to work
            threadpool.submit(handler);
        }

    }

}
