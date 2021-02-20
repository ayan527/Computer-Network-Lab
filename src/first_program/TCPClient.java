package first_program;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    //Declaring variables
    private Socket clientSocket = null;

    private OutputStreamWriter output = null;
    private PrintWriter writer = null;

    //Client side programming
    public TCPClient(String ipAddress, int port) {
        //setting connections
        try {
            System.out.println("Connection is going to be setup");
            clientSocket = new Socket(ipAddress,port);
            System.out.println("Connection Successful");

            //Initializing OutputStreamWriter & PrintWriter
            output = new OutputStreamWriter(clientSocket.getOutputStream());
            writer = new PrintWriter(output);
        } catch(Exception e) {
            System.out.println("Error in Connection: " +e.getMessage());
        }
        //sending informations
        try {
            System.out.print("Enter your name: ");
            String name = new Scanner(System.in).nextLine();

            System.out.println("Sending information to Server...\n");
            writer.println(name.trim());
            writer.flush();
        } catch(Exception e) {
            System.out.println("Error in Sending : " +e.getMessage());
        }
        //closing connections
        try {
            System.out.println("Trying to Close Connections..");
            writer.close();
            output.close();
            clientSocket.close();
            System.out.println("All Connections CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }


    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 9999;

        new TCPClient(ipAddress,port);
    }
}
