package third_program;

import java.io.*;
import java.net.*;

public class TCPServer {
    //Declaring Variables
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    private OutputStreamWriter output = null;
    private PrintWriter writer = null;
    private BufferedReader input = null;

    public TCPServer(int port) {
        //setting connections
        try {
            System.out.println("Setting Server");
            serverSocket = new ServerSocket(port);
            System.out.println("Server is waiting for Client Request");

            clientSocket = serverSocket.accept();
            System.out.println("Server sets the connection with Client");

            //Initializing BufferedReader, OutputStreamWriter & PrintWriter
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new OutputStreamWriter(clientSocket.getOutputStream());
            writer = new PrintWriter(output);
        } catch(Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //receiving & sending information
        try {
            System.out.println("Receiving information from Client...\n");
            String name = input.readLine();
            System.out.println("Hello, " +name);

            String[] divideName = name.split(" ");
            String modifiedName = divideName[0]+ ":" +divideName[1];

            System.out.println("Sending information to Client...\n");
            writer.println(modifiedName.trim());
            writer.flush();
        } catch (Exception e) {
            System.out.println("Error : " +e.getMessage());
        }
        //closing connections
        try {
            System.out.println("Trying to Close..");
            writer.close();
            output.close();
            input.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("All CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 9999;

        new TCPServer(port);
    }

}
