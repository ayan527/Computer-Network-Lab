package tenth_program;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class FileClient {
    //Declaring Variables
    private Socket clientSocket = null;

    private BufferedInputStream input = null;
    private BufferedOutputStream outputFile = null;

    private OutputStreamWriter output = null;
    private PrintWriter writer = null;

    private String fileName = null;

    //Client Side Programming
    public FileClient(String ipAddress, int port) {
        //Setting Connections
        try {
            System.out.println("Connection is going to be setup");
            clientSocket = new Socket(ipAddress,port);
            System.out.println("Connection Successful");

            //Initializing OutputStreamWriter & PrintWriter
            output = new OutputStreamWriter(clientSocket.getOutputStream());
            writer = new PrintWriter(output);
        } catch(Exception e) {
            System.out.println("Error in Connection: " +e.getMessage());
        }
        //Sending File Name to Server to fetch
        try {
            System.out.print("Enter file name : ");
            fileName = new Scanner(System.in).nextLine();

            System.out.println("Sending file name to Server...\n");
            writer.println(fileName);
            writer.flush();
        } catch (Exception e) {
            System.out.println("Error in Sending: " +e.getMessage());
        }
        //Checking for File
        try {
            input = new BufferedInputStream(clientSocket.getInputStream());
            int code = input.read();
            if (code == 1) {
                String outputFileName = "C:\\Users\\acer\\IdeaProjects\\CN Lab\\downloads\\" +fileName;
                outputFile = new BufferedOutputStream(new FileOutputStream(outputFileName));
                byte[] buffer = new byte[1024];
                int bytesRead = 0;

                while ((bytesRead = input.read(buffer)) != -1) {
                    outputFile.write(buffer, 0, bytesRead);
                    outputFile.flush();
                }

                System.out.println();
                System.out.println("File: " + fileName + " was successfully downloaded!");
            } else {
                System.out.println("File is not present on the server!");
            }
        } catch (Exception e) {
            System.out.println("Error in Downloading: " +e.getMessage());
        }
        //Closing Connections
        try {
            System.out.println("Trying to Close Connections..");
            outputFile.close();
            input.close();
            writer.close();
            output.close();
            clientSocket.close();
            System.out.println("All Connections CLosed Successfully");
        } catch(Exception e) {
            System.out.println("Error in Closing : " +e.getMessage());
        }
    }

    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 9090;

        new FileClient(ipAddress,port);
    }
}
