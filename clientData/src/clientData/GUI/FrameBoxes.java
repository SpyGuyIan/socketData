package clientData.GUI;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameBoxes extends JFrame{

    private static final long serialVersionUID = 904804786799649707L;
    private ArrayList<JPanel> xContainers = new ArrayList<JPanel>();
    private JPanel yContainer = new JPanel();
    public ArrayList<JPanel> panels = new ArrayList<JPanel>();


    public FrameBoxes(JPanel... passedPanels) {
	for(JPanel p : passedPanels){
	    panels.add(p);
	}
	createGUI();
    }

    public void addPanel(JPanel panel){
	panels.add(panel);
	refreshFrame();
    }

    public void refreshFrame(){
	int[] size = findLargestFactors(panels.size());//outputs as [0] = x, [1] = y

	xContainers.clear();
	for(int i = 0; i < size[0] + 1; i++){
	    xContainers.add(new JPanel());
	}

	yContainer.setLayout(new BoxLayout(yContainer, BoxLayout.Y_AXIS));
	for(JPanel p : xContainers){
	    p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
	    yContainer.add(p);
	}
	
	int panel = 0;
	for(int y = 0; y < size[1] + 1; y++){
	    for(int x = 0; x < size[0] + x; x++){
		xContainers.get(y).add(panels.get(panel));
		panel++;
	    }
	}


    }

    private int[] findLargestFactors(int number){
	int[] factors = {0, 0};
	for(int i = 0; i < number; i++){
	    for(int ii = 0; ii < number; i++){
		if(i*ii == number && ((double)(i+ii))/2 > ((double)(factors[0] + factors[1]))/2){
		    if(i > ii){
			factors[0] = i;
			factors[1] = ii;
		    } else {
			factors[0] = ii;
			factors[1] = i;
		    }
		}
	    }
	}

	return factors;
    }


    private void createGUI() {

	for(JPanel p : panels){
	    add(p);
	}



	//TODO create # of panels for panelNum

	setSize(600, 400);
	setTitle("Frame");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
    }

    public static void main(String[] args) {
	new FrameBoxes(new ButtonPanel());
    }

}
