package dht;

public class Item {

	private String ip;
	private String port;
		
	public Item(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}
		
	String getIP() {
		return ip;
	}
	
	String getPort(){
		return port;
	}
	
}
