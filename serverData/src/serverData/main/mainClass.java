package serverData.main;


import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;




import serverData.resources.Console;
import serverData.resources.Server;

public class mainClass {
	int port = 3000;
	Console console = new Console();

	public mainClass() {
		// Scanner input = new Scanner(System.in);
		// System.out.println("Input port: ");
		// port = input.nextInt();
		// input.nextLine();
		// input.close();

		console.println("Server opening...", Color.BLUE, false);
		Server myServer = new Server();
		myServer.init(port);

		try {
			if (myServer.readString() == "Ping!") {
				console.println("Server: Ping recieved.", Color.BLUE, false);
				console.println("Server: Ponging client..." , Color.BLUE, false);
				myServer.sendString("Pong!");
			}
		} catch (IOException e) {
		}
		myServer.close();
	}

}