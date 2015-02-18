package gui.Controls;

import gui.Ivory.Ivory;
import gui.tromboneScreen.TromboneScreen;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Controller extends JPanel {
	private TromboneScreen tromboneScreen = null;
	private VolumeController volumeControls = null;
	private MixController mixControls = null;
	private FileController fileControl = null;
	
	public Controller(TromboneScreen tromboneScreen, Ivory keys) {
		// TODO Auto-generated constructor stub
		this.setBackground(Color.BLUE);
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS) );
		
		this.tromboneScreen = tromboneScreen;
		this.volumeControls = new VolumeController( this, keys.getVolume());
		
		this.add(volumeControls);
		
		this.mixControls = new MixController(this);
		
		this.add(this.mixControls);
		
		this.fileControl = new FileController(this);
		
		this.add(fileControl);
		
	}

	public void volumeChange(int newVol){
		this.volumeControls.setVolumeLabel(newVol);
	}
	
	public TromboneScreen getScreenControlled(){
		return this.tromboneScreen;
	}
}
