package clientData.GUI;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame{

	private static final long serialVersionUID = -7803629994015778818L;

	public Frame() {
		createGUI();
	}

	private void createGUI() {

		JPanel container = new JPanel();
		JPanel yContainer1 = new JPanel();
		JPanel yContainer2 = new JPanel();
		JPanel yContainer3 = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		yContainer1.setLayout(new BoxLayout(yContainer1, BoxLayout.Y_AXIS));
		yContainer2.setLayout(new BoxLayout(yContainer2, BoxLayout.Y_AXIS));
		yContainer3.setLayout(new BoxLayout(yContainer3, BoxLayout.Y_AXIS));
		this.add(container);

		Panel panel1 = new Panel(this, 0);
		Panel panel2 = new Panel(this, 1);
		Panel panel3 = new Panel(this, 2);
		Panel panel4 = new Panel(this, 3);
		Panel panel5 = new Panel(this, 4);
		Panel panel6 = new Panel(this, 5);

		container.add(yContainer1);
		container.add(yContainer2);
		container.add(yContainer3);
		yContainer1.add(panel1);
		yContainer1.add(panel2);
		yContainer2.add(panel3);
		yContainer2.add(panel4);
		yContainer3.add(panel5);
		yContainer3.add(panel6);

		setSize(600, 400);
		setResizable(true);

		setTitle("Client");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Frame();
	}

}
