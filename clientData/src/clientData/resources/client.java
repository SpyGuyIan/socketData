package clientData.resources;

import java.io.*;
import java.net.*;

import clientData.GUI.*;

public class client {
	Socket s;
	ObjectOutputStream out;
	ObjectInputStream in;

	public client() {
		System.out.println("Client init");
	}

	public void init(String ip, int port) {
		try {
			s = new Socket(ip, port);
			System.out.println("Connected");

			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			in = new ObjectInputStream(s.getInputStream());
			System.out.println("Object Streams created");

			// Game ex = new Game();
			// ex.setVisible(true);

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Unknown Host");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOExeption error");
		}

	}

	public void sendString(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("Client> " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public String readString() throws IOException {
		try {
			return (String)in.readObject();
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
