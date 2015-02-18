package gui.Controls;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MixController extends JPanel {
	private Controller controlPane = null;
	private JButton recordButton = null;
	private JButton pauseButton = null;
	private JButton playbackButton = null; 
	
	public MixController(Controller myController){
		this.recordButton = new JButton("REC");
		this.pauseButton = new JButton("Pause");
		this.playbackButton = new JButton("Play");
		
		this.controlPane = myController;
		
		this.recordButton.addActionListener(this.controlPane.getScreenControlled());
		this.pauseButton.addActionListener(this.controlPane.getScreenControlled());
		this.playbackButton.addActionListener(this.controlPane.getScreenControlled());
		this.controlPane.getScreenControlled().setMixButtons(this.recordButton, this.pauseButton, this.playbackButton);
		
		
		this.add(recordButton);
		this.add(pauseButton);
		this.add(playbackButton);
	}

}
