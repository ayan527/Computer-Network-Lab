package eighth_program.second;

import java.io.*;
import java.net.*;
import java.util.Random;

public class GBNClient {
    //Declaring Variables
    private Socket clientSocket = null;

    private DataInputStream input = null;
    private DataOutputStream output = null;

    private Random random = new Random();

    //Client Side Programming
    public GBNClient(String ipAddress, int port) {
        //Setting Connections
        try {
            System.out.println("Connection is going to be setup");
            clientSocket = new Socket(ipAddress,port);
            System.out.println("Connection Successful");

            //Initializing DataInputStream & DataOutputStream
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch(Exception e) {
            System.out.println("Error in Connection : " + e.getMessage());
        }
        //Receiving Packets from Server
        try {
            int size = input.read();
            System.out.println("Number of packets going to receive : " +size);
            int[] receivedPackets = new int[size];

            int index = 0;
            int nackIndex = random.nextInt(size+1);

            for (int i = index; i < size; i++) {
                receivedPackets[i] = input.read();
                if (i >= nackIndex) receivedPackets[i] = -1;
            }

            System.out.println("Received Packets Before--->\n");
            showReceivedPackets(receivedPackets);

            output.write(nackIndex);
            output.flush();

            if (nackIndex != size) {
                System.out.println("Packet not recived : " +(nackIndex+1));

                index = nackIndex;
                for (int i = index; i < size; i++) {
                    receivedPackets[i] = input.read();
                }

                output.write(size);
                output.flush();
            }

            System.out.println("Received Packets After--->\n");
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
            System.out.println("All Closed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    private void showReceivedPackets(int[] receivedPackets) {
        for (int i = 0; i < receivedPackets.length; i++) {
            if (receivedPackets[i] != -1) {
                System.out.println("Packet " +(i+1)+ " : " +receivedPackets[i]);
            }
        }
    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 8011;

        new GBNClient(ipAddress,port);
    }
}
