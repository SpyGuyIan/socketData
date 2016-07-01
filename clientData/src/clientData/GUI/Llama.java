package clientData.GUI;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

public class Llama {

	private int orientation = 1;
	private Image image;

	public Llama() {
		initiateSprite();
	}

	private void initiateSprite() {
		ImageIcon ii = createImageIcon("images/carl.png");
		image = ii.getImage();       
	}

	ImageIcon createImageIcon(String path) {
		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public int getOrientation() {
		return orientation;
	}

	public Image getImage() {
		return image;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			orientation = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			orientation = 1;
		}
	}
}