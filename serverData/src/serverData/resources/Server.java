package serverData.resources;

import java.io.*;
import java.net.*;


public class Server {
	ObjectOutputStream out;
	ObjectInputStream in;
	ServerSocket server;
	Socket s;

	public Server() {}

	public void init(int port) throws IOException {
			server = new ServerSocket(port);// may not be port just an id number
			System.out.println("Server initiated at port " + port);
			s = server.accept();

			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			in = new ObjectInputStream(s.getInputStream());
			System.out.println("Object Streams created");

			System.out.println("Connected");
	}

	public void sendString(String msg) throws IOException {
			out.writeObject(msg);
			out.flush();
	}

	public String readString() throws IOException {
		try {
			return (String) in.readObject();
		} catch (ClassNotFoundException classnot) {
			System.err.println("Data received in unknown format");
			return "Unknown";
		} 

	}

	public void close() {
		try {
			in.close();
			out.close();
			s.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}