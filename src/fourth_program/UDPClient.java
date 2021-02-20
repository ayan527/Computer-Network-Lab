package fourth_program;

import java.net.*;
import java.util.Scanner;

public class UDPClient {
    //Declaring variables
    private InetAddress ipAddress = null;
    private DatagramSocket clientSocket = null;
    private DatagramPacket sendingPacket = null;
    private DatagramPacket receivingPacket = null;

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
            String nameS = new Scanner(System.in).nextLine();
            byte[] sendBytes = nameS.getBytes();

            //Sending Data Packet to Server
            System.out.println("Sending information to Server...\n");
            sendingPacket = new DatagramPacket(sendBytes,sendBytes.length,ipAddress,port);
            clientSocket.send(sendingPacket);

            //Receiving Data Packet from Server
            System.out.println("Receiving information from Server...\n");
            byte[] receiveBytes = new byte[1024];
            receivingPacket = new DatagramPacket(receiveBytes,receiveBytes.length);
            clientSocket.receive(receivingPacket);

            //Printing data
            String[] nameR = new String(receivingPacket.getData()).trim().split(":");
            System.out.println("First Name : " +nameR[0]);
            System.out.println("Last Name : " +nameR[1]);

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
