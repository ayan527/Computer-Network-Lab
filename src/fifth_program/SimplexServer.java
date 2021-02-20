package fifth_program;

import java.io.*;
import java.net.*;

public class SimplexServer {
    //Declaring Variables
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    private BufferedReader input = null;

    private boolean isStopped = false;

    //Server side programming
    public SimplexServer(int port) {
        //setting connections
        try {
            System.out.println("Setting Server");
            serverSocket = new ServerSocket(port);
            System.out.println("Server is waiting for Client Request");

            clientSocket = serverSocket.accept();
            System.out.println("Server sets the connection with Client");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //Receiving Information
        try {
            while (!isStopped) {
                System.out.println("Receiving information from Client...\n");
                int number = Integer.parseInt(input.readLine());
                if (number == 0) {
                    isStopped = !isStopped;
                } else {
                    boolean isPrime = checkForPrime(number);
                    if (isPrime) System.out.println("Number " +number+ " is Prime Number.\n");
                    else System.out.println("Number " +number+ " is Composite Number.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error : " +e.getMessage());
        }
        //Closing Connections
        try {
            System.out.println("Trying to Close..");
            input.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("All CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    private boolean checkForPrime(int number) {
        for (int i = 2; i <= Math.sqrt((double) number); i++){
            if (number % i == 0) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int port = 9999;

        new SimplexServer(port);
    }
}
