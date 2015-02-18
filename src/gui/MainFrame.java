package gui;

import gui.Ivory.Ivory;
import gui.roseLock.RoseLock;
import gui.tromboneScreen.TromboneGUI;
import gui.tromboneScreen.TromboneScreen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	public static final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private RoseLock lockScreen;
	private TromboneScreen instrumentScreen; 
	
	/* Mainframe constructor
	 * Create the main frame, the top level frame.
	 */
	public MainFrame(){
		
		//Setting up environment variables
		this.setTitle("8-Bit Trombone");
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		//this.setPreferredSize(screenDim);
		this.setResizable(false);
		this.setLayout(new CardLayout());		
		//
		/*Setting up a lock screen for the program so that it must be unlocked to
		 *Get to the content of the mainframe being made.
		 *
		 */
		this.lockScreen = new RoseLock("roseBackground.png", this);
		this.add(lockScreen, "ROSELOCK");
		//
		//Hack to properly size screen on background.
		Dimension hack = lockScreen.getSize();
		hack.setSize(hack.width + 6, hack.height + 26);
		
		this.setSize(hack);
		this.setLocation( ((screenDim.width/2) - this.getWidth()/2) , ((screenDim.height/2) - this.getHeight()/2) );
		//
		/* Creating the graphical representation of the instrument
		 * the content frame, and in this case a trombone
		 */
		this.instrumentScreen = new TromboneScreen(this.getContentPane());
		
		this.add(instrumentScreen,"TEST");
		
		
	}

	public void cardSwap(String string) {
		CardLayout myCL = (CardLayout) this.getContentPane().getLayout();
		myCL.show(this.getContentPane(), "TEST");
		this.instrumentScreen.setFocusable(true);
		this.instrumentScreen.focus();
	}
	
	
	public Ivory getIvoryInput(){
		return this.instrumentScreen.getIvory();
	};
	
	public void moveSlide(double dist){
		instrumentScreen.getTromboneGUI().setOffset(dist);
	}

}
