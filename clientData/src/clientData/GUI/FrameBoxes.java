package clientData.GUI;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameBoxes extends JFrame{

	private static final long serialVersionUID = 904804786799649707L;
	public ArrayList<Panel2> panels = new ArrayList<Panel2>();
	public ArrayList<JPanel> xContainers = new ArrayList<JPanel>();
	public ArrayList<JPanel> yContainers = new ArrayList<JPanel>();

	
	public FrameBoxes(int xPanels, int yPanels) {
		createGUI(xPanels, yPanels);
	}

	private void createGUI(int xPanels, int yPanels) {

		
		
		for(int i = 0; i < panelNum + 1; i++){
			panels.add(new Panel2(this, i));
			panels.get(i).setBackground(Color.getHSBColor((float)i/10, 1, 1));
		}
		
//TODO create # of panels for panelNum

		setSize(600, 400);
		setTitle("Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new FrameBoxes(5);
	}

}
