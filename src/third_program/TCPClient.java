package third_program;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    //Declaring Variables
    private Socket clientSocket = null;

    private OutputStreamWriter output = null;
    private PrintWriter writer = null;
    private BufferedReader input = null;

    //Client-Side Programming
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
        //sending information
        try {
            System.out.print("Enter your name: ");
            String name = new Scanner(System.in).nextLine();

            System.out.println("Sending information to Server...\n");
            writer.println(name);
            writer.flush();
        } catch(Exception e) {
            System.out.println("Error in Sending : " +e.getMessage());
        }
        //receiving information
        try {
            //Initializing BufferedReader
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Receiving information from Server...\n");
            String[] name = input.readLine().trim().split(":");

            System.out.println("First Name : " +name[0]);
            System.out.println("Last Name : " +name[1]);
        } catch(Exception e) {
            System.out.println("Error in Getting : " +e.getMessage());
        }
        //closing connections
        try {
            System.out.println("Trying to Close..");
            writer.close();
            output.close();
            input.close();
            clientSocket.close();
            System.out.println("All CLosed Successfully");
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
