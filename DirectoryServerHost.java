package dht;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class DirectoryServerHost
{
    public static void main(String[] args) throws Exception
    {
	//Hash map responsible for holding File name -> IP, Port mapping
        Map<String, Item> hashTable = new HashMap<String, Item>();

	//Variables to set hash map
	String fileName;
	String ip;
	String port;
	String returnMessage = "";

	//Initilize sent and receiving data buffers
	byte[] sendData = new byte[1024];
    	byte[] receiveData = new byte[1024];
	
	//Create 2 sockets to receive and to send 
	DatagramSocket welcomeSocket = new DatagramSocket(25000);
	DatagramSocket clientSocket = new DatagramSocket();
	
	//Constantly loop to wait for requests
	while(true) {	
	
	//counter for the number of files being uploaded by the client
	int counter = 0;
	
	//Receive packets
	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	welcomeSocket.receive(receivePacket);
	
	//Scanner to get different elements in the request
	Scanner inFromClient = new Scanner(new String(receivePacket.getData()));
	Scanner line = new Scanner(inFromClient.nextLine());

	//loop through the elements(strings) in the request
	while(line.hasNext()) {
		fileName = line.next();
		
		//Determine if request is for a file or to upload its files
		//If there is a string after the file name, we determine that its registering
		//its file with the server, other wise its a request for a file location.
		if(line.hasNext()) {
			ip = line.next();
			port = line.next();

			hashTable.put(fileName, new Item(ip, port));
			counter++;
			returnMessage = counter + " Files read successfully!\n";

		} else if (hashTable.containsKey(fileName)){
			Item fileLocation = hashTable.get(fileName);
			returnMessage = fileLocation.getIP() + " " + fileLocation.getPort() + "\n";
		}
	}
	
	//Return either the number of files read successfully
 	//Or the ip port of the request file.
	sendData = returnMessage.getBytes();	
	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
	clientSocket.send(sendPacket);
	
	//Close Scanners
	line.close();
	inFromClient.close();

		}  
	}
}
