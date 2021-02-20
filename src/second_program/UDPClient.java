package second_program;

import java.net.*;
import java.util.Scanner;

public class UDPClient {
    //Declaring variables
    private InetAddress ipAddress = null;
    private DatagramSocket clientSocket = null;
    private DatagramPacket sendingPacket = null;

    //Client side programming
    public UDPClient(int port) {
        //Sending Data
        try {
            System.out.println("Connection is going to be setup");
            ipAddress = InetAddress.getLocalHost();
            clientSocket = new DatagramSocket();
            System.out.println("Connection Successful");

            //Add Data
            System.out.print("Enter your name: ");
            String name = new Scanner(System.in).nextLine();
            byte[] sendBytes = name.getBytes();

            //Sending Data Packet to Server
            System.out.println("Sending information to Server...\n");
            sendingPacket = new DatagramPacket(sendBytes,sendBytes.length,ipAddress,port);
            clientSocket.send(sendingPacket);

        } catch (Exception e) {
            System.out.println("Error : " +e.getMessage());
        }
        //Closing Socket
        try {
            System.out.println("Trying to Close Socket..");
            clientSocket.close();
            System.out.println("Socket CLosed Successfully");
        } catch (Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 6666;

        new UDPClient(port);
    }
}
