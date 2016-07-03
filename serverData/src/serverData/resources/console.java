
package serverData.resources;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

public class console {

	private JFrame frame;
	private JPanel panel;
	private JLabel prefix;
	private JTextField inputLine;
	private JTextPane history;//TODO make this do stuff
	private JScrollPane scrollV;
	private String lastCommand = "";
	private ArrayList<message> historyList = new ArrayList<message>(); 
	private Font font;
	StyledDocument doc;
	Style style;
	//private JScrollBar scrollVBar;

	public console(){
		createGui();
		addListeners();
	}

	public console(File logFile){//TODO specify URL aswell
		createGui();
		addListeners();
		//TODO make log file
	}

	private void addListeners(){
		//add a Component listener to look of resize
		frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				refreshHistory();          
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		inputLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {		
				addLine(inputLine.getText(), Color.RED, true);//TODO make this up to the user class for more variable situations(setAutoAddLine function??)
				lastCommand = inputLine.getText(); 
				inputLine.setText("");
			}

		});

	}

	private void createGui(){

		//SynthLookAndFeel laf = new SynthLookAndFeel();
		//try { laf.load(console.class.getResourceAsStream("laf.xml"), console.class);}
		//catch(ParseException e1) {e1.printStackTrace();}

		//try { UIManager.setLookAndFeel(laf);} 
		//catch( UnsupportedLookAndFeelException e) {e.printStackTrace();}


		frame = new JFrame("Console");
		frame.setSize(600,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO figure out how to make this also tell clients that tits shutting down

		SpringLayout layout = new SpringLayout();
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(layout);

		//font = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		font = new Font("Consolas", Font.PLAIN, 16);

		prefix = new JLabel();
		prefix.setText("Console> ");
		prefix.setForeground(Color.WHITE);
		prefix.setBackground(Color.BLACK);
		prefix.setFont(font);

		inputLine = new JTextField();
		inputLine.setText("");//.getText();
		inputLine.setColumns(1);
		inputLine.setBackground(Color.BLACK);
		inputLine.setForeground(Color.WHITE);
		inputLine.setBorder(BorderFactory.createEmptyBorder());
		inputLine.setFont(font);

		history = new JTextPane();
		history.setEditable(false);
		//history.setText("TEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\nTEXTS\n");
		history.setText("");
		history.setForeground(Color.WHITE);
		history.setBackground(Color.BLACK);
		//history.setColumns(50);
		//history.setLineWrap(true);
		history.setFont(font);

		doc = history.getStyledDocument();
		style = history.addStyle("I'm a Style", null);
		StyleConstants.setForeground(style, Color.WHITE);



		scrollV = new JScrollPane(history);
		scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollV.setWheelScrollingEnabled(true);
		scrollV.setBorder(BorderFactory.createEmptyBorder());
		scrollV.setForeground(Color.WHITE);
		scrollV.setBackground(Color.BLACK);
		//scrollV.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		//TODO make

		//scrollVBar = scrollV.getVerticalScrollBar();
		//scrollVBar.setValue(scrollVBar.getMaximum());

		//Positioning of the scroll bar
		layout.putConstraint(SpringLayout.EAST, scrollV, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.NORTH, scrollV, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, scrollV, -5, SpringLayout.NORTH, inputLine);
		layout.putConstraint(SpringLayout.WEST, scrollV, 10, SpringLayout.WEST, panel);

		//Positioning of the history area
		layout.putConstraint(SpringLayout.WEST, history, 0, SpringLayout.WEST, prefix);
		layout.putConstraint(SpringLayout.EAST, history, -5, SpringLayout.WEST, scrollV);
		layout.putConstraint(SpringLayout.NORTH, history, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, history, -10, SpringLayout.NORTH, prefix);

		//Positioning of the prefix and input box
		layout.putConstraint(SpringLayout.WEST, inputLine, 2, SpringLayout.EAST, prefix);
		layout.putConstraint(SpringLayout.EAST, inputLine, -5, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, inputLine, -1, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, prefix, -1, SpringLayout.SOUTH, panel);

		panel.add(scrollV);
		panel.add(prefix);
		panel.add(inputLine);
		//panel.add(history);
		frame.add(panel);
		frame.setVisible(true);

	}

	public void addLine(String command, Color c, boolean usePrefix){
		if(usePrefix){
			historyList.add(new message(prefix.getText(), Color.WHITE, new mFormat()).append(new message(command, c, new mFormat())));
		}else{
			historyList.add(new message(command, c, new mFormat()));
		}
		if(historyList.size() > 50 ){
			historyList.remove(0);
		}
		refreshHistory();
	}

	public void refreshHistory(){
		history.setText("");
		for(message ln : historyList){
			for(mChar ch : ln.mCharList){
				
				StyleConstants.setBold(style, ch.format.bold);
				StyleConstants.setItalic(style, ch.format.italic);
				StyleConstants.setUnderline(style, ch.format.underline);
				StyleConstants.setStrikeThrough(style, ch.format.strikeThrough);

				StyleConstants.setForeground(style, ch.color);
				try { doc.insertString(doc.getLength(), String.valueOf(ch.charValue), style); }
				catch (BadLocationException e){}
			}
			try { doc.insertString(doc.getLength(), "\n", style); }
			catch (BadLocationException e){}

		}
	}

	public String getLastCommand() {
		return lastCommand;
	}

	public void setPrefix(String p){
		prefix.setText(p + "> ");
	}

	public void clear(){
		history.setText("");
	}

	public void setScrollVisibility(boolean visibility){
		scrollV.setVisible(visibility);
	}

	public void close(){
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void minimize(){
		frame.setState(Frame.ICONIFIED);
	}

	public void maximize(){
		frame.setState(Frame.NORMAL);
	}

	public static void main(String[] args){
		new console();
	}

}
