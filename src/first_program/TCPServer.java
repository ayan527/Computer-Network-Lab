package first_program;

import java.net.*;
import java.io.*;

public class TCPServer {
    //Declaring Variables
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    private BufferedReader input = null;

    //Server side programming
    public TCPServer(int port) {
        //setting connections
        try {
            System.out.println("Setting Server");
            serverSocket = new ServerSocket(port);
            System.out.println("Server is waiting for Client Request");

            clientSocket = serverSocket.accept();
            System.out.println("Server sets the connection with Client");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //receiving informations
        try {
            System.out.println("Receiving information from Client...\n");
            String name = input.readLine();
            System.out.println("Hello, " +name);
        } catch (Exception e) {
            System.out.println("Error : " +e.getMessage());
        }
        //closing connections
        try {
            System.out.println("Trying to Close..");
            input.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("All CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 9999;

        new TCPServer(port);
    }
}
