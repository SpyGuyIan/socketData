package serverData.resources;

import java.io.*;
import java.net.*;


public class server {
	ObjectOutputStream out;
	ObjectInputStream in;
	ServerSocket server;
	Socket s;

	public server() {
		System.out.println("Server init");

	}

	public void init(int port) {
		try {
			server = new ServerSocket(port);// may not be port just an id number
			s = server.accept();

			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			in = new ObjectInputStream(s.getInputStream());
			System.out.println("Object Streams created");

			System.out.println("Connected");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOExeption error---");
		}
	}

	public void sendString(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("Server> " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public String readString() throws IOException {
		try {
			return (String) in.readObject();
		} catch (ClassNotFoundException classnot) {
			System.err.println("Data received in unknown format");
			return "Error";
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