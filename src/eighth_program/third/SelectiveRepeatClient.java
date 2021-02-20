package eighth_program.third;

import java.io.*;
import java.net.*;
import java.util.Random;

public class SelectiveRepeatClient {
    //Declaring Variables
    private Socket clientSocket = null;

    private DataInputStream input = null;
    private DataOutputStream output = null;

    private Random random = new Random();

    //Client Side Programming
    public SelectiveRepeatClient(String ipAddress, int port) {
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
            int index = random.nextInt(size);

            for (int i = 0; i < size; i++) {
                receivedPackets[i] = input.read();
                if (i != index) {
                    System.out.println("Packet received : " +(i+1));
                } else {
                    receivedPackets[i] = -1;
                    System.out.println("Packet not recived : " +(i+1));
                }
            }

            System.out.println("Received Packets Before--->\n");
            showReceivedPackets(receivedPackets);

            System.out.println("Requesting Server to resend packet " +(index + 1));
            output.write(index);
            output.flush();

            System.out.println("Accepting missing packet from Server...\n");
            receivedPackets[index] = input.read();

            System.out.println("Received Packets After--->\n");
            showReceivedPackets(receivedPackets);

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error in Receiving : " +e.getMessage());
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
            if (receivedPackets[i] != -1) {
                System.out.println("Packet " +(i+1)+ " : " +receivedPackets[i]);
            }
        }
    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 8011;

        new SelectiveRepeatClient(ipAddress,port);
    }
}
