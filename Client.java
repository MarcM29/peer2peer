package p2pClient;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client
{
   public static void main(String args[]) throws Exception
   {
      //BufferedReader inFromUser =
      //	new BufferedReader(new InputStreamReader(System.in));
	   /*String contentName="mountain.jpg";
	   File folder = new File("C:\\Users\\mtmccomb\\Desktop\\pictures");
	   File[] listOfFiles = folder.listFiles();
	   
	   for (int i = 0; i < listOfFiles.length; i++) {
	     if (listOfFiles[i].isFile()) {
	       contentName = listOfFiles[i].getName();
	       uploadFile(contentName);
	     } 
	   }
	   System.out.println("Init done\n");
	   //Replace following line for user input
	   String desiredFileName;
	   Scanner myScanner = new Scanner(System.in);
	   System.out.println("Enter desired file name");
	   desiredFileName = myScanner.nextLine();
	   
	   String desiredFileAddress;
	   desiredFileAddress = sendQuery(desiredFileName);
	   System.out.println("Desired file is located at address: " + desiredFileAddress);
	   */
	   
	   //Delete this
	   String desiredFileName;
	   Scanner myScanner = new Scanner(System.in);
	   System.out.println("Enter desired file name");
	   desiredFileName = myScanner.nextLine();
	   //
	   
	   requestFile(desiredFileName, "localhost 20630");
   }
   
   //This function just hashes content name into server ID (decides which server it will send to)
   public static int hashToID(String fileName) {
	   int sum=0;
	   int serverID;
	   //Takes sum of ASCII values of fileName and takes result mod 4 to get destination server ID
	   char[] ascii = fileName.toCharArray();
	   for(char ch:ascii) {
		   sum+=(int)ch;
	   }
	   serverID = (sum%4)+1;
	   return serverID;
   }
   
   public static void requestFile(String desiredFileName, String desiredFileAddress) throws Exception
   {
	   String[] splitBySpace = desiredFileAddress.split(" ");
	   int  SOCKET_PORT = Integer.parseInt(splitBySpace[1]);
	   String SERVER = splitBySpace[0];
	   String FILE_TO_RECEIVED = "C:\\Users\\marc1\\Desktop\\temp\\download-"+desiredFileName;
	   int FILE_SIZE = 3000000;
	   //InetAddress address = InetAddress.getByName(SERVER);

	   int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket sock = null;
	    try {
	    sock = new Socket(SERVER, SOCKET_PORT);
	    OutputStream os = sock.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        String sendMessage = desiredFileName + "\n";
        bw.write(sendMessage);
        bw.flush();
        System.out.println("File requested from server: "+sendMessage);
	      
	      
	      // receive file
	      byte [] mybytearray  = new byte [FILE_SIZE];
	      InputStream is = sock.getInputStream();
	      fos = new FileOutputStream(FILE_TO_RECEIVED);
	      bos = new BufferedOutputStream(fos);
	      bytesRead = is.read(mybytearray,0,mybytearray.length);
	      current = bytesRead;

	      do {
	         bytesRead =
	            is.read(mybytearray, current, (mybytearray.length-current));
	         if(bytesRead >= 0) current += bytesRead;
	      } while(bytesRead > -1);

	      bos.write(mybytearray, 0 , current);
	      bos.flush();
	      System.out.println("File " + FILE_TO_RECEIVED
	          + " downloaded (" + current + " bytes read)");
	    }
	    finally {
	      if (fos != null) fos.close();
	      if (bos != null) bos.close();
	      if (sock != null) sock.close();
	    }
	  }

   
   public static String sendQuery(String fileName) throws Exception{
	      //Gets DHT server ID it will send too (not used atm)
	      int chosenID = hashToID(fileName);
		  //int chosenID = 2;
		  //DHT server info (known) (ID's 1-4)
	      ///////////////////////////////////////////////////////
		  String serverIP1 = "ENG201-06";
		  String serverPort1 = "20630";
		  
		  String serverIP2 = "ENG201-06";
		  String serverPort2 = "20631";
		  
		  String serverIP3 = "ENG201-04";
		  String serverPort3 = "20630";
		  
		  String serverIP4 = "ENG201-04";
		  String serverPort4 = "20631";
		  ///////////////////////////////////////////////////////
	   
	   	  DatagramSocket clientSocket = new DatagramSocket(20630);
	   	  //Following variables are assigned values for testing purposes
	      InetAddress IPAddress = InetAddress.getByName("localhost");
	   	  //InetAddress IPAddress = InetAddress.getByName(clientIPv6);
	      //InetAddress IPAddress;
	      int port=20630;
	      
	      //Based on result from hashToID value it will alter IP address and Port to the respective servers values
		  if(chosenID==1) {
			  System.out.println("\nFile will be sent to server ID: " + 1 + "\n");
			  IPAddress = InetAddress.getByName(serverIP1);
			  port = Integer.parseInt(serverPort1);
		  }
		  if(chosenID==2) {
			  System.out.println("\nFile will be sent to server ID: " + 2 + "\n");
			  IPAddress = InetAddress.getByName(serverIP2);
			  port = Integer.parseInt(serverPort2);
		  }
		  if(chosenID==3) {
			  System.out.println("\nFile will be sent to server ID: " + 3 + "\n");
			  IPAddress = InetAddress.getByName(serverIP3);
			  port = Integer.parseInt(serverPort3);
		  }
		  if(chosenID==4) {
			  System.out.println("\nFile will be sent to server ID: " + 4 + "\n");
			  IPAddress = InetAddress.getByName(serverIP4);
			  port = Integer.parseInt(serverPort4);
		  }
		  
		  
		  //At this point in the code it will know which server it will be sending to
		  
		  
	      byte[] receiveData = new byte[256];
	      byte[] sendQuery = new byte[256];
	      
	      //Creates the msg
	      String query = fileName + "\n";
	      
	      sendQuery = query.getBytes();
	      
	      //Sends packet
	      //DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	      //clientSocket.send(sendPacket);
	      
	      DatagramPacket sendQueryPacket = new DatagramPacket(sendQuery, sendQuery.length, IPAddress, port);
	      clientSocket.send(sendQueryPacket);

	      
	      
	      //Receives response from server
	      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
       clientSocket.receive(receivePacket);
       
       Scanner inFromClient = new Scanner(new String(receivePacket.getData()));
       Scanner line = new Scanner(inFromClient.nextLine());
       String returnedAddress = "";
       String tempIP;
       String tempPort;
       while(line.hasNext()){
     	  tempIP=line.next();
     	  tempPort=line.next();
          returnedAddress=(tempIP + " " + tempPort);
       }
       line.close();
       inFromClient.close();
	      clientSocket.close();
	      return returnedAddress;
   }
   
   //This function handles the sending packets
   public static void uploadFile(String fileName) throws Exception {
	      //Gets DHT server ID it will send too (not used atm)
	      int chosenID = hashToID(fileName);
	   	  //int chosenID =2;
	      String clientServerIP = "192.0.82.5";
	      String clientServerPort = "20631";
		  
		  //DHT server info (known) (ID's 1-4)
	      ///////////////////////////////////////////////////////
		  String serverIP1 = "ENG201-06";
		  String serverPort1 = "20630";
		  
		  String serverIP2 = "ENG201-06";
		  String serverPort2 = "20631";
		  
		  String serverIP3 = "ENG201-04";
		  String serverPort3 = "20630";
		  
		  String serverIP4 = "ENG201-04";
		  String serverPort4 = "20631";
		  ///////////////////////////////////////////////////////
	   
	   	  DatagramSocket clientSocket = new DatagramSocket(20630);
	   	  //Following variables are assigned values for testing purposes
	      InetAddress IPAddress = InetAddress.getByName("localhost");
	   	  //InetAddress IPAddress = InetAddress.getByName(clientIPv6);
	      //InetAddress IPAddress = InetAddress.getByName(jurgenIPv6);
	      int port=20630;
	      
	      //Based on result from hashToID value it will alter IP address and Port to the respective servers values
		  if(chosenID==1) {
			  System.out.println("\nFile will be sent to server ID: " + 1 + "\n");
			  IPAddress = InetAddress.getByName(serverIP1);
			  port = Integer.parseInt(serverPort1);
		  }
		  if(chosenID==2) {
			  System.out.println("\nFile will be sent to server ID: " + 2 + "\n");
			  IPAddress = InetAddress.getByName(serverIP2);
			  port = Integer.parseInt(serverPort2);
		  }
		  if(chosenID==3) {
			  System.out.println("\nFile will be sent to server ID: " + 3 + "\n");
			  IPAddress = InetAddress.getByName(serverIP3);
			  port = Integer.parseInt(serverPort3);
		  }
		  if(chosenID==4) {
			  System.out.println("\nFile will be sent to server ID: " + 4 + "\n");
			  IPAddress = InetAddress.getByName(serverIP4);
			  port = Integer.parseInt(serverPort4);
		  }
		  
		  
		  //At this point in the code it will know which server it will be sending to
		  
		  
	      byte[] sendData = new byte[256];
	      byte[] receiveData = new byte[256];
	      
	      byte[] sendQuery = new byte[256];
	      
	      //Creates the msg
	      String sentence = fileName + " " + clientServerIP + " " + clientServerPort + "\n";
	      String query = fileName + "\n";
	      
	      sendData = sentence.getBytes();
	      sendQuery = query.getBytes();
	      
	      //Sends packet
	      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	      clientSocket.send(sendPacket);
	      
	      //DatagramPacket sendQueryPacket = new DatagramPacket(sendQuery, sendQuery.length, IPAddress, port);
	      //clientSocket.send(sendQueryPacket);

	      
	      
	      //Receives response from server
	      //DatagramSocket serverSocket = new DatagramSocket(20631);
	      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          
          Scanner inFromClient = new Scanner(new String(receivePacket.getData()));
          Scanner line = new Scanner(inFromClient.nextLine());
          String temp1;
          String temp2;
          while(line.hasNext()){
        	  temp1=line.next();
        	  temp2=line.next();
              System.out.println(temp1 + " " + temp2);
          }
          line.close();
          inFromClient.close();
	      clientSocket.close();
   }
}
