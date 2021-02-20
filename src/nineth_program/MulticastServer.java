package nineth_program;

import java.net.*;

public class MulticastServer {
    //Declaring Variables
    private InetAddress group = null;
    private MulticastSocket serverSocket = null;
    private DatagramPacket receivedPacket = null;

    //Server Side Programming
    public MulticastServer(String ipAddress, int port) {
        //Setting Connections
        try {
            System.out.println("Setting Server");
            group = InetAddress.getByName(ipAddress);
            serverSocket = new MulticastSocket(port);
            serverSocket.joinGroup(group);
            System.out.println("Server is waiting for Client Data");
        } catch (Exception e) {
            System.out.println("Error in Connections : " +e.getMessage());
        }
        //Receiving Message from Client
        try {
            while(true){
                System.out.println("Waiting for multicast message...");
                byte[] receiveBytes = new byte[1024];
                receivedPacket = new DatagramPacket(receiveBytes, receiveBytes.length);

                System.out.println("Receiving information from Client...\n");
                serverSocket.receive(receivedPacket);

                String message = new String(receivedPacket.getData()).trim();
                System.out.println("Message received ---> "+message);
                // Check if the message is an exit message
                if("OK".equals(message)) {
                    System.out.println("No more message. Exiting : "+message+ "\n");
                    break;
                }
            }
            serverSocket.leaveGroup(group);
        } catch (Exception e) {
            System.out.println("Error in Receiving : " +e.getMessage());
        }
        //Closing Connections
        try {
            System.out.println("Trying to Close Socket..");
            serverSocket.close();
            System.out.println("Socket Closed Successfully");
        } catch (Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 3456;
        String ipAddress = "225.4.5.6";

        new MulticastServer(ipAddress,port);
    }
}
