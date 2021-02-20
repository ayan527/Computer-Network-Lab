package second_program;

import java.net.*;

public class UDPServer {
    //Declaring variables
    private DatagramSocket serverSocket = null;
    private DatagramPacket receivingPacket = null;

    //Server side programming
    public UDPServer(int port) {
        //Receiving Data
        try {
            System.out.println("Setting Server");
            serverSocket = new DatagramSocket(port);
            System.out.println("Server is waiting for Client Data");

            //Receiving Data
            System.out.println("Receiving information from Client...\n");
            byte[] receiveBytes = new byte[1024];
            receivingPacket = new DatagramPacket(receiveBytes,receiveBytes.length);
            serverSocket.receive(receivingPacket);

            //Extracting Data
            String name = new String(receivingPacket.getData());
            System.out.println("Hello, " +name);
        } catch (Exception e) {
            System.out.println("Error : " +e.getMessage());
        }
        //closing socket
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
