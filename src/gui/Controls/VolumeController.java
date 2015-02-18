package gui.Controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VolumeController extends JPanel implements ActionListener {
	private Controller controlPane = null;
	private JButton increaseVol = null;
	private JButton decreaseVol = null;
	private JLabel currentVol = null;
	private JLabel volLabel =  new JLabel("Volume:");
	private int volumeLevel = 0;
	private int discreteVolChange = 5;
	
	public VolumeController(Controller controlPane , int initVol, int volStep){
		this.controlPane = controlPane;
		
		this.discreteVolChange = volStep;
		
		
		
		this.increaseVol = new JButton("+");
		this.decreaseVol = new JButton("-");
		this.volumeLevel = initVol;
		this.currentVol = new JLabel(Integer.toString(volumeLevel));
		
		this.increaseVol.addActionListener(this.controlPane.getScreenControlled());
		this.decreaseVol.addActionListener(this.controlPane.getScreenControlled());
		this.controlPane.getScreenControlled().setVolButtons(increaseVol, decreaseVol);
		
		this.add(volLabel);
		this.add(decreaseVol);
		this.add(currentVol);
		this.add(increaseVol);
		
	}
	
	public VolumeController(Controller controlPane , int initVol){
		this.controlPane = controlPane;
		
		this.increaseVol = new JButton("+");
		this.decreaseVol = new JButton("-");
		this.volumeLevel = initVol;
		this.currentVol = new JLabel(Integer.toString(volumeLevel));
		
		this.increaseVol.addActionListener(this.controlPane.getScreenControlled());
		this.decreaseVol.addActionListener(this.controlPane.getScreenControlled());
		this.controlPane.getScreenControlled().setVolButtons(increaseVol, decreaseVol);
		
		this.add(volLabel);
		this.add(decreaseVol);
		this.add(currentVol);
		this.add(increaseVol);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	public void setVolumeLabel(int newVol) {
		this.currentVol.setText(Integer.toString(newVol));
		
	}
}
