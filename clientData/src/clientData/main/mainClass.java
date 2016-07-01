package clientData.main;

import java.io.IOException;
import java.util.Scanner;
import clientData.resources.client;

public class mainClass {
    String ip = "127.0.0.1";
    int port = 3000;

    public mainClass() {// Runs when class called
	// Scanner input = new Scanner(System.in);
	// System.out.println("Input ip: ");
	// ip = input.nextLine();

	// System.out.println("Input port: ");
	// port = input.nextInt();
	// input.nextLine();
	// input.close();

	System.out.println("Client opening...");
	client myClient = new client();
	System.out.println("client being initialized");
	myClient.init(ip, port);

	Scanner input = new Scanner(System.in);
	System.out.println("Client: Pinging server...");
	myClient.sendString("Ping!");
	try {
	    if (myClient.readString() == "Pong!") {
		System.out.println("Client> Pong recieved!");
	    }
	} catch (IOException e) {
	}

	myClient.close();
	input.close();
    }

}