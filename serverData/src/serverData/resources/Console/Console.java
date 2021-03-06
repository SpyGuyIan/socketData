package serverData.resources.Console;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

interface commandListener {
	void commandEntered();
}

public class Console{

	private JFrame frame;
	private JPanel panel;
	private JLabel prefix;
	private JTextField inputLine;
	private JTextPane history;
	private JScrollPane scrollV;
	private String lastCommand = "";
	private ArrayList<Message> historyList = new ArrayList<Message>(); 
	private Font font;
	private ArrayList<commandListener> listeners = new ArrayList<commandListener>();
	StyledDocument doc;
	Style style;
	final List<String> holder = new LinkedList<String>();
	PrintWriter pw;


	public Console(){
		createGui();
		addListeners();
	}
	
	public Console(boolean debug){
		createGui();
		addListeners();
		redirectSystemStreams();
	}
	
	public Console(File file){
		createGui();
		addListeners();
		redirectSystemStreams();
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addCommandListener(commandListener toAdd) {
		listeners.add(toAdd);
	}

	private void addListeners(){
		//add a Component listener to look of resize
		frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				drawHistory();          
			}

			@Override
			public void componentHidden(ComponentEvent arg0){}
			@Override
			public void componentMoved(ComponentEvent arg0){}
			@Override
			public void componentShown(ComponentEvent arg0){}
		});

		inputLine.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {		
				//addLine(inputLine.getText(), Color.WHITE, true);//text for making it echo
				lastCommand = inputLine.getText(); 
				inputLine.setText("");
				commandEntered();
				synchronized (holder) {
					holder.add(lastCommand);
	                holder.notify();
				}
			}
		});
	}

	private void commandEntered() {
		for (commandListener hl : listeners)
			hl.commandEntered();
	} 
	
	private void addHistory(Message msg) {
		historyList.add(msg);
		drawHistory();
		try {
			pw.println(msg.getText());
		} catch(NullPointerException e) {}
	}
	
	public String inString() {
		synchronized (holder) {
			while (holder.isEmpty())
				try {
					holder.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

	        String nextString = holder.remove(0);
			return nextString;
		}
	}

	private void createGui(){
		//TODO make scroll bar look nice with synth look and feel and laf.xml
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

		font = new Font("Consolas", Font.PLAIN, 16);

		prefix = new JLabel();
		prefix.setText("Console> ");
		prefix.setForeground(Color.WHITE);
		prefix.setBackground(Color.BLACK);
		prefix.setFont(font);

		inputLine = new JTextField();
		inputLine.setText("");
		inputLine.setColumns(1);
		inputLine.setBackground(Color.BLACK);
		inputLine.setForeground(Color.WHITE);
		inputLine.setBorder(BorderFactory.createEmptyBorder());
		inputLine.setFont(font);
		inputLine.setCaretColor(Color.WHITE);

		history = new JTextPane();
		history.setEditable(false);
		history.setText("");
		history.setForeground(Color.WHITE);
		history.setBackground(Color.BLACK);
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
		frame.add(panel);
		frame.setVisible(true);

	}

	public void println(String command, Color c, boolean usePrefix){
		if(usePrefix){
			addHistory(new Message(prefix.getText(), Color.WHITE, new mFormat()).append(new Message(command, c, new mFormat())));
		}else{
			addHistory(new Message(command, c, new mFormat()));
		}
	}

	public void println(Message msg, boolean usePrefix){
		if(usePrefix){
			addHistory(new Message(prefix.getText(), Color.WHITE, new mFormat()).append(msg));
		}else{
			addHistory(msg);
		}
	}

	public void println(Message msg){
		addHistory(msg);
	}

	public void println(String s){
		addHistory(new Message(s, Color.WHITE, new mFormat()));
	}

	public void println(String s, Color c){
		addHistory(new Message(s, c, new mFormat()));
	}

	public void drawHistory(){
		if(historyList.size() > 100 ){
			historyList.remove(0);
		}
		history.setText("");
		for(Message ln : historyList){
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
		try {
			pw.close();
		} catch(NullPointerException e) {}
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void minimize(){
		frame.setState(Frame.ICONIFIED);
	}

	public void maximize(){
		frame.setState(Frame.NORMAL);
	}

	private void updateTextPane(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      addHistory(new Message(text, Color.RED, new mFormat()));
		    }
		  });
		}
		 
		private void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(final int b) throws IOException {
		      updateTextPane(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextPane(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
		}

}
