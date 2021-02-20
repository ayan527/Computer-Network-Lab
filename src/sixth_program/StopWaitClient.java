package sixth_program;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class StopWaitClient {
    //Declaring Variables
    private Socket clientSocket = null;

    private DataInputStream input = null;
    private DataOutputStream output = null;

    //Client Side Programming
    public StopWaitClient(String ipAddress, int port) {
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
            Arrays.fill(receivedPackets,-1);

            int acknowledge;
            int index = 0;
            while (index < size) {
                receivedPackets[index] = input.read();
                acknowledge = 1;

                System.out.println("Packet received : " +(index+1));
                System.out.println("Sending Acknowledgement for packet " +(index+1));
                sendAcknowledge(acknowledge);
                index++;
            }

            System.out.println("Received Packets --->\n");
            showReceivedPackets(receivedPackets);

        } catch (Exception e) {
            System.out.println("Error in Receiving : " +e.getMessage());
        }
        //Closing Connections
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

    private void sendAcknowledge(int ack) throws IOException{
        output.write(ack);
        output.flush();
    }

    private void showReceivedPackets(int[] receivedPackets) {
        for (int i = 0; i < receivedPackets.length; i++) {
            System.out.println("Packet " +(i+1)+ " : " +receivedPackets[i]);
        }
    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 9999;

        new StopWaitClient(ipAddress,port);
    }
}
