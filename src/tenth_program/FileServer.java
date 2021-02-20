package tenth_program;

import java.net.*;
import java.io.*;

public class FileServer {
    //Declaring Variables
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    private BufferedReader input = null;
    private BufferedInputStream fileReader = null;
    private BufferedOutputStream output = null;

    //Server Side Programming
    public FileServer(int port) {
        //Setting Connections
        try {
            System.out.println("Setting Server");
            serverSocket = new ServerSocket(port);
            System.out.println("Server is waiting for Client Request");

            clientSocket = serverSocket.accept();
            System.out.println("Server sets the connection with Client");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new BufferedOutputStream(clientSocket.getOutputStream());
        } catch(Exception e) {
            System.out.println("Error in Connection : " +e.getMessage());
        }
        //Checking for File
        try {
            String fileName = input.readLine();
            System.out.println("file name: " + fileName + " has been requested by " + clientSocket.getInetAddress().getCanonicalHostName());

            File file = new File(fileName);
            if(!file.exists()) {
                //Send code 0 as File doesnot exist
                output.write((byte) 0);
                output.flush();
            }
            else
            {
                //Send code 1 as File exists
                output.write((byte)1);
                output.flush();

                System.out.println("Sending file to Client...\n");
                fileReader = new BufferedInputStream(new FileInputStream(file));

                //Set the Buffer Size
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = fileReader.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    output.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Error in Sending : "+e.getMessage());
        }
        //closing connections
        try {
            System.out.println("Trying to Close..");
            fileReader.close();
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
        int port = 9090;

        new FileServer(port);
    }
}
