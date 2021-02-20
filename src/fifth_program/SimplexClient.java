package fifth_program;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimplexClient {
    //Declaring variables
    private Socket clientSocket = null;

    private OutputStreamWriter output = null;
    private PrintWriter writer = null;

    private boolean isStopped = false;

    //Client side programming
    public SimplexClient(String ipAddress, int port) {
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
        //Sending Information
        try {
            System.out.println("Press 0 to stop sending data...\n");
            Scanner input = new Scanner(System.in);

            while (!isStopped) {
                System.out.print("Enter a number: ");
                int number = input.nextInt();

                if (number == 0) {
                    isStopped = !isStopped;
                } else {
                    System.out.println("Sending information to Server...\n");
                }

                writer.println(number);
                writer.flush();
            }
        } catch(Exception e) {
            System.out.println("Error in Sending : " +e.getMessage());
        }
        //Closing Connections
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

        new SimplexClient(ipAddress,port);
    }
}
