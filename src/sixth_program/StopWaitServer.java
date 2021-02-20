package sixth_program;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class StopWaitServer {
    //Declaring Variables
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    private DataOutputStream output = null;
    private DataInputStream input = null;

    private Scanner inputScanner;

    //Server Side Programming
    public StopWaitServer(int port) {
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

            int[] packetsArray = new int[size];
            insertDataIntoPackets(packetsArray);

            int acknowledge;
            int index = 0;
            while (index < size) {
                System.out.println("Sending packet " +(index+1)+ " to Client");
                output.write(packetsArray[index]);
                output.flush();

                acknowledge = input.read();
                if (acknowledge == 0) {
                    System.out.println("Acknowledgement for packet " +(index+1)+ " not received\n");
                    System.out.println("Stop sending packets to Client...\n");
                    break;
                } else {
                    System.out.println("Acknowledgement for packet " +(index+1)+ " received\n");
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
        int port = 9999;

        new StopWaitServer(port);
    }
}
