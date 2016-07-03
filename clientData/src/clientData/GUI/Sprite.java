package clientData.GUI;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;

public class Sprite {
	private Image image;
	private Coordinates coords;
	int dx=0, dy=0, radius;
	private static final int SPEED = 5;
	private String name = null;
	private boolean isCollected = false;

	public Sprite(String imageName, int myRadius, String myName) {
		do {
			coords = new Coordinates((int) (Math.random() * 2000 - 800), (int) (Math.random() * 2000 - 800));
		} while((coords.getX() < 600 && coords.getX() > 200) && (coords.getY() < 600 && coords.getY() > 200));
		setImage(imageName);
		radius = myRadius;
		name = myName;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(String imageName) {
		ImageIcon ii = createImageIcon(imageName);
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

	public void move() {
		changeX();
		changeY();
	}
	
	public int getRadius() {
		return radius;
	}
	
	public String getName() {
		return name;
	}
	
	public void collect() {
		isCollected = true;
	}
	
	public boolean isCollected() {
		return isCollected;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = -1;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 1;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = -1;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}
	
	public void changeX() {
		coords.setX(coords.getX() + dx * SPEED);
	}
	
	public void changeY() {
		coords.setY(coords.getY() + dy * SPEED);
	}
	
	public Coordinates getCoords() {
		return coords;
	}
}
