package clientData.GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {
	
	private static final long serialVersionUID = -7803629994015778818L;

	public Game() {
		initiateUI();
	}

	private void initiateUI() {

		add(new Board(this));

		setSize(800, 800);
		setResizable(false);

		setTitle("Space Llama");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}