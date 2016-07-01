package clientData.GUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5509155261502497671L;
	Frame myFrame;
	Timer timer;
	private static final int DELAY = 10;
	int screen;
	
	public void actionPerformed(ActionEvent e) {
		
		
		repaint();
	}

	public Panel(Frame frame, int screenNumber) {
		myFrame = frame;
		screen = screenNumber;
		initBoard();
	}

	public void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));

		timer = new Timer(DELAY, this);
		timer.start();        
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(screen == 0) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("I'm screen 1", 50, 50);
		} else if (screen == 1) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("I'm screen 2", 50, 50);
		} else if (screen == 2) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("I'm screen 3", 50, 50);
		} else if (screen == 3) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("I'm screen 4", 50, 50);
		} else if (screen == 4) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("I'm screen 5", 50, 50);
		} else if (screen == 5) {
			g2d.setColor(Color.BLACK);
			g2d.drawString("I'm screen 6", 50, 50);
		}
	}
	
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				
			}

			if (key == KeyEvent.VK_RIGHT) {
				
			}

			if (key == KeyEvent.VK_UP) {
				
			}

			if (key == KeyEvent.VK_DOWN) {
				
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				
			}

			if (key == KeyEvent.VK_RIGHT) {
				
			}

			if (key == KeyEvent.VK_UP) {
				
			}

			if (key == KeyEvent.VK_DOWN) {
				
			}
		}
	}
	
	public void setScreen(int screenNumber) {
		screen = screenNumber;
	}
	
	public void stopTimer() {
		timer.stop();
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
	
	public int getCenterPos(String s, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (800 - fm.stringWidth(s)) / 2;
		return x;
	}
}
