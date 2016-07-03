package clientData.GUI;

public class Coordinates {
	private int xPos;
	private int yPos;
	
	public Coordinates(int myX, int myY) {
		xPos = myX;
		yPos = myY;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setX(int myX) {
		xPos = myX;
	}
	
	public void setY(int myY) {
		yPos = myY;
	}
}
