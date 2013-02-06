// Joshua Yancy HW 1 CS 471G
//Server side receiving message count
// returns message count number of times



import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.nio.charset.CharsetEncoder;
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServer {

  private static final int BUFSIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);
    System.out.println(InetAddress.getLocalHost().getHostAddress());
    int recvMsgSize;   // Size of received message
    byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer
    
   
    while (true) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();     // Get client connection

      SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
      System.out.println("Handling client at " + clientAddress);
      String[] splitdecoded;
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();
      // need to figure out how to get this to send back multiple times
      // are we allowed to encode and decode
      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(receiveBuf)) != -1) {
    	  String decoded = new String(receiveBuf, "UTF-8");
    	  String holder = "";
    	  splitdecoded = decoded.split(" ");
    	  int count = Integer.parseInt(splitdecoded[0]);
    	  holder = splitdecoded[1];
    	   for(int number = 1; number < count; number++)
    	  {
    		  holder = holder.trim() + " " + splitdecoded[1];
    		  
    	  }
    	  byte[] convertme = holder.getBytes();
    	  
    	  byte[] result = new byte[convertme.length];
    	  for(int i=0; i<convertme.length; i++)
    	  {
    		  result[i] = convertme[i];
    	  }
    	
    	 out.write(result);
      }

      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
