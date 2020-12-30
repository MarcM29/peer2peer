package p2pServer;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

class UDPServer
{
	public final static int SOCKET_PORT = 20630;  // you may change this
	public static String FILE_TO_SEND;
   public static void main(String args[]) throws Exception
      {
	   FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    ServerSocket servsock = null;
	    Socket sock = null;
	    try {
	      servsock = new ServerSocket(SOCKET_PORT);
	      while (true) {
	        System.out.println("Waiting...");
	        try {
                sock = servsock.accept();
                InputStream is = sock.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String fileName = br.readLine();
                System.out.println("Message received from client is "+fileName);
                FILE_TO_SEND = "C:\\Users\\marc1\\Desktop\\temp\\"+fileName;

	          System.out.println("Accepted connection : " + sock);
	          // send file
	          File myFile = new File (FILE_TO_SEND);
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);
	          bis.read(mybytearray,0,mybytearray.length);
	          os = sock.getOutputStream();
	          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	          System.out.println("Done.");
	        }
	        finally {
	          if (bis != null) bis.close();
	          if (os != null) os.close();
	          if (sock!=null) sock.close();
	        }
	      }
	    }
	    finally {
	      if (servsock != null) servsock.close();
	    }
	  }
	}