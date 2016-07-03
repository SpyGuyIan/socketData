package clientData.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1046335505815064243L;
	private static final int DELAY = 10;
	private Sprite planets[];
	private Sprite foods[];
	Timer timer;
	Llama llama = new Llama();
	Game myGame;
	Coordinates llamaCoords;
	private List<Coordinates> stars = new ArrayList<Coordinates>();
	int dx = 0, dy = 0;
	int screen = 3;
	Action a = new PlayAgain(this), r = new Rules(this), m = new Menu(this);
	JButton playAgain = new JButton(a), rules = new JButton(r), joinGame = new JButton(a), mainMenu = new JButton(m);

	@Override
	public void actionPerformed(ActionEvent e) {
		for(Sprite s : planets)
			s.move();
		for(Sprite s : foods)
			s.move();
		llamaCoords.setX(llamaCoords.getX() + dx);
		llamaCoords.setY(llamaCoords.getY() + dy);
		
		repaint();
	}

	public Board(Game game) {
		myGame = game;
		initBoard();
	}

	public void createSprites() {
		planets = new Sprite[]{new Sprite("images/Planet 3.png", 100, null), new Sprite("images/Sun.png", 200, null), new Sprite("images/Planet 1.png", 100, null), new Sprite("images/Planet 2.png", 200, null), new Sprite("images/Planet 4.png", 150, null), new Sprite("images/Planet 5.png", 150, null), new Sprite("images/Planet 6.png", 100, null)};
		foods = new Sprite[]{new Sprite("images/Food 1.png", 50, "birthday cake"), new Sprite("images/Food 2.png", 50, "cupcake"), new Sprite("images/Food 3.png", 50, "candy"), new Sprite("images/Food 1.png", 50, "birthday cake"), new Sprite("images/Food 2.png", 50, "cupcake"), new Sprite("images/Food 3.png", 50, "candy")};
	}

	public void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);

		createSprites();
		llamaCoords = new Coordinates(0, 0);
		
		playAgain.setText("Play Again");
		playAgain.setVisible(false);
		this.add(playAgain);
		
		rules.setText("Rules");
		rules.setVisible(false);
		this.add(rules);
		
		joinGame.setText("Join Game");
		joinGame.setVisible(false);
		this.add(joinGame);
		
		mainMenu.setText("Main Menu");
		mainMenu.setVisible(false);
		this.add(mainMenu);

		//fill stars array
		for(int myX = -5000; myX < 5000; myX+=5)
			for(int myY = -5000; myY < 5000; myY+=5)
				if(Math.random() * 1000 < 0.5)
				{
					stars.add(new Coordinates(myX, myY));
				}

		timer = new Timer(DELAY, this);
		timer.start();        
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
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

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(screen == 0) {
			
			playAgain.setVisible(false);
			rules.setVisible(false);
			joinGame.setVisible(false);
			mainMenu.setVisible(false);
			
			//Stars
			g2d.setColor(Color.WHITE);
			for(Coordinates temp : stars)
				g2d.fillOval(temp.getX() - llamaCoords.getX(), temp.getY() - llamaCoords.getY(), 5, 5);

			//Planets
			for(Sprite s : planets)
				g2d.drawImage(s.getImage(), s.getCoords().getX() - s.getImage().getWidth(this)/2, s.getCoords().getY() - s.getImage().getHeight(this)/2, this);

			//Foods
			for(Sprite s : foods) {
				if(!s.isCollected())
					g2d.drawImage(s.getImage(), s.getCoords().getX() - s.getImage().getWidth(this)/2, s.getCoords().getY() - s.getImage().getHeight(this)/2, this);
			}

			//Llama
			int temp = 400 - llama.getImage().getWidth(this)/2;
			if(llama.getOrientation()==-1)
				temp +=llama.getImage().getWidth(this);
			g2d.drawImage(llama.getImage(), temp, 400 - llama.getImage().getHeight(this)/2, llama.getImage().getWidth(this) * llama.getOrientation(), llama.getImage().getHeight(this), this);

			//Collecting foods
			for(Sprite s : foods)
				if(isTouching(s)) {
					s.collect();
				}

			//Recipe
			g2d.fillRect(650, 0, 150, 200);
			g2d.setColor(Color.BLACK);
			g2d.drawString("SWEETS", 705, 20);
			int y = 50;
			for(Sprite s : foods) {
				if(s.isCollected())
					g2d.setColor(Color.GREEN);
				else
					g2d.setColor(Color.RED);
				g2d.drawString(s.getName(), 675, y);
				y+=20;
			}

			//Check for winning
			boolean win = true;
			for(Sprite s : foods)
				if(!s.isCollected())
					win = false;
			if(win) {
				screen = 1;
			}

			//Check for losing
			for(Sprite s : planets)
				if(isTouching(s)) {
					screen = 2;
				}
		} else if (screen == 1) {		//winning screen
			
			rules.setVisible(false);
			joinGame.setVisible(false);
			
			setBackground(Color.GREEN);
			g2d.setFont(new Font("Dialog", Font.PLAIN, 50));
			g2d.drawString("YOU WIN", getCenterPos("YOU WIN", g), 300);
			
			playAgain.setBounds(350, 400, 100, 50);
			playAgain.setVisible(true);
			mainMenu.setBounds(350, 500, 100, 50);
			mainMenu.setVisible(true);
		} else if (screen == 2) {		//losing screen
			
			joinGame.setVisible(false);
			rules.setVisible(false);
			
			setBackground(Color.RED);
			g2d.setFont(new Font("Dialog", Font.PLAIN, 50));
			g2d.drawString("YOU LOSE", getCenterPos("YOU LOSE", g), 300);
			
			playAgain.setBounds(350, 400, 100, 50);
			playAgain.setVisible(true);
			mainMenu.setBounds(350, 500, 100, 50);
			mainMenu.setVisible(true);
		} else if (screen == 3) {		//main menu
			
			playAgain.setVisible(false);
			mainMenu.setVisible(false);
			
			setBackground(Color.MAGENTA);
			
			g2d.setFont(new Font("Dialog", Font.PLAIN, 50));
			g2d.drawString("This is a Client", getCenterPos("This is a Client", g), 300);
			
			//rules.setBounds(280, 400, 100, 100);
			//rules.setVisible(true);
			joinGame.setBounds(350, 400, 100, 100);
			joinGame.setVisible(true);
		} else if (screen == 4) {		//rules
			
			joinGame.setVisible(false);
			rules.setVisible(false);
			playAgain.setVisible(false);
			
			setBackground(Color.LIGHT_GRAY);
			ImageIcon ii = createImageIcon("images/rules.png");
			Image text = ii.getImage();
			g2d.drawImage(text, 60, 50, this);
			
			mainMenu.setBounds(350, 500, 100, 50);
			mainMenu.setVisible(true);
		}
	}

	public boolean isTouching(Sprite s) { //planet1 doesn't work well?
		int r = s.getRadius();
		int xDiff = 400 - s.getCoords().getX();
		int yDiff = 400 - s.getCoords().getY();
		double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		if(distance < r)
			return true;
		return false;
	}
	
	public void setScreen(int screenNumber) {
		screen = screenNumber;
	}
	
	public void stopTimer() {
		timer.stop();
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			for(Sprite s : planets)
				s.keyReleased(e);
			for(Sprite s : foods)
				s.keyReleased(e);

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

		@Override
		public void keyPressed(KeyEvent e) {
			for(Sprite s : planets)
				s.keyPressed(e);
			for(Sprite s : foods)
				s.keyPressed(e);
			llama.keyPressed(e);

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				dx = -1;
			}

			if (key == KeyEvent.VK_RIGHT) {
				dx = 1;
			}

			if (key == KeyEvent.VK_UP) {
				dy = -1;
			}

			if (key == KeyEvent.VK_DOWN) {
				dy = 1;
			}
		}
	}

	public int getCenterPos(String s, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (800 - fm.stringWidth(s)) / 2;
		return x;
	}
}

class PlayAgain extends AbstractAction {
	
	private static final long serialVersionUID = -9030308296260884843L;
	Board board;
	
	PlayAgain(Board myBoard) {
		board = myBoard;
	}
	
	public void actionPerformed(ActionEvent e) {
		board.stopTimer();
		board.initBoard();
		board.setScreen(0);
	}
}

class Rules extends AbstractAction {

	private static final long serialVersionUID = -3944425884964824938L;
	Board board;
	
	Rules(Board myBoard) {
		board = myBoard;
	}
	
	public void actionPerformed(ActionEvent e) {
		board.setScreen(4);
	}
}

class Menu extends AbstractAction {

	private static final long serialVersionUID = 6448973038478641944L;
	Board board;
	
	Menu(Board myBoard) {
		board = myBoard;
	}
	
	public void actionPerformed(ActionEvent e) {
		board.setScreen(3);
	}
}
