package serverData.main;


import java.io.IOException;
import java.util.Scanner;


import serverData.resources.server;

public class mainClass {
    int port = 3000;

    public mainClass() {// Runs when class called
	// Scanner input = new Scanner(System.in);
	// System.out.println("Input port: ");
	// port = input.nextInt();
	// input.nextLine();
	// input.close();

	System.out.println("Server opening...");
	server myServer = new server();
	myServer.init(port);

	Scanner input = new Scanner(System.in);
	try {
	    if (myServer.readString() == "Ping!") {
		System.out.println("Server: Ping recieved.");
		System.out.println("Server: Ponging client...");
		myServer.sendString("Pong!");
	    }
	} catch (IOException e) {
	}
	myServer.close();
	input.close();

    }

}