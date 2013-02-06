// Joshua Yancy HW 1 CS 471G
//Client side sending message count


import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TCPEchoClient {

  public static void main(String[] args) throws IOException {

    if ((args.length < 3) || (args.length > 4))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> [<Port>] <Count> <Word> ");

    String server = args[0];       // Server name or IP address
    // Convert argument String to bytes using the default character encoding
    String message = args[2] + " " + args[3];
    byte[] data = message.getBytes();
    
    int servPort = (args.length == 4) ? Integer.parseInt(args[1]) : 7;

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    System.out.println("Connected to server...sending echo string");

    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();

    out.write(data);  // Send the encoded string to the server
    int bytelength = message.length() * 3; 
    // Receive the same string back from the server
    int totalBytesRcvd = 0;  // Total bytes received so far
    int bytesRcvd;           // Bytes received in last read
    byte[] newdata = new byte[bytelength];
    while (totalBytesRcvd < newdata.length) {
      if ((bytesRcvd = in.read(newdata, totalBytesRcvd,  newdata.length - totalBytesRcvd)) == -1)
        throw new SocketException("Connection closed prematurely");
      totalBytesRcvd += bytesRcvd;
    }  // data array is full
   
    System.out.println("Received: " + new String(newdata));

    socket.close();  // Close the socket and its streams
  }
}
