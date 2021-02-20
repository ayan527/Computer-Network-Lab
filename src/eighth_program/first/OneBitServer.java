package eighth_program.first;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class OneBitServer {
    //Declaring Variables
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    private DataOutputStream output = null;
    private DataInputStream input = null;

    private Scanner inputScanner;

    //Server Side Programming
    public OneBitServer(int port) {
        inputScanner = new Scanner(System.in);

        //Setting Connections
        try {
            System.out.println("Setting Server");
            serverSocket = new ServerSocket(port);
            System.out.println("Server is waiting for Client Request");

            clientSocket = serverSocket.accept();
            System.out.println("Server sets the connection with Client");

            //Initializing DataInputStream & DataOutputStream
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch(Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //Sending Packets to Client
        try {
            System.out.print("Enter the number of packets : ");
            int size = inputScanner.nextInt();

            System.out.println("Sending number of packets to be sent to Client...\n");
            output.write(size);
            output.flush();

            System.out.println("Sending window size to Client...\n");
            output.write(1);
            output.flush();

            int[] packetsArray = new int[size];
            insertDataIntoPackets(packetsArray);

            int acknowledge;
            int index = 0;
            while (index < size) {
                System.out.println("Sending packet " +(index+1)+" to Client...\n");
                output.write(packetsArray[index]);
                output.flush();

                acknowledge = input.read();
                if (acknowledge == 0) {
                    System.out.println("The packet " +(index+1)+ " needs to be resent\n");
                    System.out.println("Sending missing packet to Client...\n");
                } else {
                    index++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in Sending : " +e.getMessage());
        }

        //Closing Connections
        try {
            System.out.println("Trying to Close..");
            output.close();
            input.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("All CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    private void insertDataIntoPackets(int[] packetsArray) {
        for (int i = 0; i < packetsArray.length; i++) {
            System.out.print("Enter " +(i+1)+ "th number : ");
            packetsArray[i] = inputScanner.nextInt();
        }
    }

    public static void main(String[] args) {
        int port = 8011;

        new OneBitServer(port);
    }
}
