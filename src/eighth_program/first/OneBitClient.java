package eighth_program.first;

import java.io.*;
import java.net.*;
import java.util.Random;

public class OneBitClient {
    //Declaring Variables
    private Socket clientSocket = null;

    private DataInputStream input = null;
    private DataOutputStream output = null;

    private Random random = new Random();

    //Client Side Programming
    public OneBitClient(String ipAddress, int port) {
        //Setting Connections
        try {
            System.out.println("Connection is going to be setup");
            clientSocket = new Socket(ipAddress,port);
            System.out.println("Connection Successful");

            //Initializing DataInputStream & DataOutputStream
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch(Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //Receiving Packets from Server
        try {
            int size = input.read();
            System.out.println("Number of packets going to receive : " +size);

            int[] receivedPackets = new int[size];

            System.out.println("Received window size : " +input.read());

            int acknowledge;
            int index = 0;
            while (index < size) {
                receivedPackets[index] = input.read();
                acknowledge = random.nextInt(2);
                if (acknowledge == 0) {
                    receivedPackets[index] = -1;
                    System.out.println("Packet not recived : " +(index+1));
                    System.out.println("Requesting Server to resend packet " +(index + 1));
                } else {
                    System.out.println("Packet received : " +(index+1));
                    index++;
                }
                output.write(acknowledge);
                output.flush();
            }

            System.out.println("Received Packets --->\n");
            showReceivedPackets(receivedPackets);

        } catch (Exception e) {
            System.out.println("Error in Receiving : " +e.getMessage());
        }
        //Close Connections
        try {
            System.out.println("Trying to Close..");
            output.close();
            input.close();
            clientSocket.close();
            System.out.println("All CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    private void showReceivedPackets(int[] receivedPackets) {
        for (int i = 0; i < receivedPackets.length; i++) {
            System.out.println("Packet " +(i+1)+ " : " +receivedPackets[i]);
        }
    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 8011;

        new OneBitClient(ipAddress,port);
    }
}
