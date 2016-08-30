package clientData.GUI;

import java.util.ArrayList;
import javax.swing.JFrame;

public class Frame extends JFrame{

	private static final long serialVersionUID = -7803629994015778818L;
	
	private ArrayList<Screen> screenList = new ArrayList<Screen>();
	private int currentScreen = 0;
	private int listSize = 0;
	

	public Frame(Screen... screens) {
		for(Screen s : screens)
			screenList.add(s);
		listSize = screenList.size();
	}
	
	public void setScreen(int screenNumber) {
		remove(getCurrentScreen());
		currentScreen = screenNumber;
		add(getCurrentScreen());
	}
	
	public void nextScreen() {
		if(currentScreen+1 < listSize)
			setScreen(currentScreen + 1);
		else
			setScreen(0);
	}
	
	public int getScreenNumber() {
		return currentScreen;
	}
	
	public Screen getCurrentScreen() {
		return screenList.get(getScreenNumber());
	}
	
	public void addScreen(Screen s) {
		screenList.add(s);
	}
	
	public void removeScreen(int screenNumber) {
		screenList.remove(screenNumber);
	}
	
	public void removeScreen(Screen s) {
		screenList.remove(s);
	}
	
	public static void main(String[] args) {
		new Frame();
	}

}
