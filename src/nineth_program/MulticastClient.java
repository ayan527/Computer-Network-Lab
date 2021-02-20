package nineth_program;

import java.net.*;
import java.util.Scanner;

public class MulticastClient {
    //Declaring Variables
    private InetAddress group = null;
    private MulticastSocket clientSocket = null;
    private DatagramPacket sendingPacket = null;

    private Scanner scanner = null;
    //Client Side Programming
    public MulticastClient(String ipAddress, int port) {
        //Settng Connections
        try {
            System.out.println("Connection is going to be setup");
            group = InetAddress.getByName(ipAddress);
            clientSocket = new MulticastSocket();
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //Sending Message to Server
        try {
            //Add Data
            scanner = new Scanner(System.in);
            String message = "";
            while(!(message.equals("OK"))) {
                System.out.println("Enter the message of type OK to exit : ");
                message = scanner.nextLine();

                byte[] sendBytes = message.getBytes();
                System.out.println("Sending information to Server...\n");
                sendingPacket = new DatagramPacket(sendBytes,sendBytes.length,group,port);
                clientSocket.send(sendingPacket);
            }
        } catch (Exception e) {
            System.out.println("Error in Sending : " +e.getMessage());
        }
        //Closing Connections
        try {
            System.out.println("Trying to Close Socket..");
            clientSocket.close();
            System.out.println("Socket Closed Successfully");
        } catch (Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 3456;
        String ipAddress = "225.4.5.6";

        new MulticastClient(ipAddress,port);
    }
}
