package clientData.main;

import java.io.EOFException;
import java.io.IOException;
import java.util.Scanner;

import serverData.resources.client;
import serverData.resources.server;

public class mainClass {
	String ip = "127.0.0.1", choice = "";
	int port = 3000;

	public mainClass() {// Runs when class called
		Scanner input = new Scanner(System.in);
		//System.out.println("Input ip: ");
		//ip = input.nextLine();

		//System.out.println("Input port: ");
		//port = input.nextInt();
		//input.nextLine();

		System.out.println("Client or server (c/s)?");
		choice = input.nextLine();

		input.close();

		if (choice.equals("s")) {
			serverSet();
		} else if (choice.equals("c")) {
			clientSet();
		} else {
			// TODO make it go back an ask again
		}

	}

	private void serverSet() {
		System.out.println("Server opening...");
		server myServer = new server();
		myServer.init(port);
		serverMain(myServer);
	}

	private void clientSet() {
		System.out.println("Client opening...");
		client myClient = new client();
		System.out.println("client being initialized");
		myClient.init(ip, port);
		clientMain(myClient);
	}

	private void clientMain(client myClient) {
		Scanner input = new Scanner(System.in);
			System.out.println("Client: Pinging server...");
			myClient.sendString("Ping!");
			try {
				if(myClient.readString() == "Pong!"){
					System.out.println("Client> Pong recieved!");
				}
			} catch (IOException e) {}
			
			myClient.close();
			input.close();
	}

	private void serverMain(server myServer) {
		Scanner input = new Scanner(System.in);
		try {
			if(myServer.readString() == "Ping!") {
				System.out.println("Server: Ping recieved.");
				System.out.println("Server: Ponging client...");
				myServer.sendString("Pong!");
			}
		} catch (IOException e) {}
		myServer.close();
		input.close();
	}

}