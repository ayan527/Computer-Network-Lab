package fourth_program;

import java.net.*;

public class UDPServer {
    //Declaring variables
    private InetAddress ipAddress = null;
    private DatagramSocket serverSocket = null;
    private DatagramPacket sendingPacket = null;
    private DatagramPacket receivingPacket = null;

    //Server-Side programming
    public UDPServer(int port) {
        //Receiving & Sending Data
        try {
            System.out.println("Setting Server");
            ipAddress = InetAddress.getLocalHost();
            serverSocket = new DatagramSocket(port);
            System.out.println("Server is waiting for Client Data");

            //Receiving Data Packet from Client
            byte[] receiveBytes = new byte[1024];
            receivingPacket = new DatagramPacket(receiveBytes,receiveBytes.length);
            serverSocket.receive(receivingPacket);

            //extracting data
            System.out.println("Receiving information from Client...\n");
            String name = new String(receivingPacket.getData());
            System.out.println("Hello, " +name);

            String[] divideName = name.split(" ");
            String modifiedName = divideName[0]+ ":" +divideName[1];

            //Sending Data Packet to CLient
            System.out.println("Sending information to Client...\n");
            byte[] sendingBytes = modifiedName.trim().getBytes();
            sendingPacket = new DatagramPacket(sendingBytes,sendingBytes.length,ipAddress,receivingPacket.getPort());
            serverSocket.send(sendingPacket);
        } catch (Exception e) {
            System.out.println("Error : " +e.getMessage());
        }
        //Closing Socket
        try {
            System.out.println("Trying to Close Socket..");
            serverSocket.close();
            System.out.println("Socket CLosed Successfully");
        } catch (Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 6666;

        new UDPServer(port);
    }
}
