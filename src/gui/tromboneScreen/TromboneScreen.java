package gui.tromboneScreen;

import gui.MainFrame;
import gui.Controls.Controller;
import gui.Ivory.Ivory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import javax.management.timer.Timer;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JPanel;

import trombone.Note;
import trombone.Trombone;

public class TromboneScreen extends JPanel implements KeyListener,ActionListener{
	
	private TromboneGUI myTromboneGUI = null;
	private Ivory Keyano = null;
	private JButton incVolButton = null;
	private JButton decVolButton = null;
	private int discreteVolChange = 5;
	private Controller controlsScreen = null;
	private JButton recordButton = null;
	private JButton playButton = null;
	private JButton pauseButton = null;
	private boolean record = false;
	private boolean playbackInProgress = false;
	private Vector<CharNote> strokes = new Vector<CharNote>();
	private Vector<Integer> timing = new Vector<Integer>();
	private final Timer metronome = new Timer();
	private Map<Character,Long> stopWatch = new HashMap<Character,Long>();
	
	
	public TromboneScreen(Container mf){
		this.metronome.start();
		
		this.setBackground(Color.BLUE);
		this.setLayout(new GridBagLayout());
		this.setSize(mf.getSize());
		//Create a Trombone instrument(object) that we will use to make sound
		Trombone RTrombone = null; 
		try {
			RTrombone = new Trombone();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		//Setting up a JOGL GUI panel TODO convert to canvas in TromboneGUI
		GLProfile glprofile = GLProfile.getDefault();
		GLCapabilities glcapabilities = new GLCapabilities(glprofile);
	
		this.myTromboneGUI = new TromboneGUI(RTrombone,glcapabilities);
		//Place The Trombone GUI in its proper space
		GridBagConstraints trombGUIConstraints = new GridBagConstraints();
		trombGUIConstraints.gridx = 0;
		trombGUIConstraints.gridy = 0;
		trombGUIConstraints.weightx = 0.4;
		trombGUIConstraints.weighty = 0.0;
		trombGUIConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		trombGUIConstraints.fill = GridBagConstraints.BOTH;
		
		this.myTromboneGUI.setSize(500 ,700);
		this.myTromboneGUI.setPreferredSize(new Dimension(500,700));
		this.myTromboneGUI.setMaximumSize(new Dimension(500,700));
		this.add(this.myTromboneGUI, trombGUIConstraints);
		
		//Setup and place keys for the trombone screen
		GridBagConstraints Constraints = new GridBagConstraints();
		Constraints.gridx = 0;
		Constraints.gridy = 1;
		Constraints.weightx = 1.0;
		Constraints.weighty = 0.25;
		Constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		Constraints.fill = GridBagConstraints.BOTH;
		Constraints.gridwidth = 2;
		
		this.Keyano = new Ivory("" , this);
		this.Keyano.addKeyListener(this);
		this.Keyano.setInstrument(this.myTromboneGUI);
		Keyano.setSize(MainFrame.screenDim);
		this.add( Keyano , Constraints );
		//
		
		//End TODO
		//ControlsGUI controls = new ControlsGUI();
		GridBagConstraints controlsConstraints = new GridBagConstraints();
		controlsConstraints.gridx = 1;
		controlsConstraints.gridy = 0;
		controlsConstraints.weightx = 0.25;
		controlsConstraints.weighty = 0.0;
		controlsConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		controlsConstraints.fill = GridBagConstraints.BOTH;
		this.controlsScreen = new Controller(this,this.Keyano);
		controlsScreen.setSize(MainFrame.screenDim);
		this.add(controlsScreen, controlsConstraints);
		//ENDTODO 
	}
	
	public void reflectNote(char c){
		double offSet = 0;
		
		if(TromboneGUI.slideNoteOffset.containsKey(c)){
			offSet = TromboneGUI.slideNoteOffset.get(c);
		}
		this.myTromboneGUI.setOffset(offSet);
		return;
	}
	
	public TromboneGUI getTromboneGUI(){
		return this.myTromboneGUI;
	}
	
	public Ivory getIvory(){
		return this.Keyano;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.incVolButton){
			int newVolume = this.Keyano.getVolume() + this.discreteVolChange;
			this.Keyano.setVolume(newVolume);
			this.controlsScreen.volumeChange(newVolume);
			this.Keyano.requestFocus();
		}
		else if(arg0.getSource() == this.decVolButton){
			int newVolume = this.Keyano.getVolume() - this.discreteVolChange;
			this.Keyano.setVolume(newVolume);
			this.controlsScreen.volumeChange(newVolume);
			this.Keyano.requestFocus();
		}
		else if(arg0.getSource() == this.recordButton){
			this.keepStroke();
			this.Keyano.requestFocus();
		}
		else if(arg0.getSource() == this.playButton){
			//TODO create seperate thread to sim play.
			this.reflectNote('a');
			this.Keyano.requestFocus();
			this.playLastStroke();
			
			
		}
		else if(arg0.getSource() == this.pauseButton){
			//TODO Set up pause when the play is finished.
			
		}
	}

	private void playLastStroke() {
		this.playbackInProgress = true;
		this.Keyano.simulatePlay(this.strokes);
		this.playbackInProgress = false;
		
		
	}

	private void keepStroke() {
		this.recordButton.setBackground(null);
		if(!this.record){
			stopWatch.clear();
			strokes.clear();
			this.recordButton.setBackground(Color.RED);
		}
		this.record = this.record?false:true;
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(!playbackInProgress){
			char keyPressed = arg0.getKeyChar();
			if(Ivory.whiteBones.containsKey(keyPressed)){
				if(this.record){
					if(this.stopWatch.containsKey('*')){
						Long currTime = System.currentTimeMillis();
						Long length = (currTime - this.stopWatch.get('*'));
						this.stopWatch.remove('*');
						Note nPlayed = new Note(0, (long) 0 , 0);
						nPlayed.setLength(length);
						CharNote newPair = new CharNote('*', nPlayed );
						this.strokes.add(newPair);
					}
					if(!stopWatch.containsKey(keyPressed))
						this.stopWatch.put( arg0.getKeyChar() , System.currentTimeMillis() );
				}
				this.Keyano.playKey(arg0);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(!playbackInProgress){
			char keyReleased = arg0.getKeyChar();
			if(Ivory.whiteBones.containsKey(keyReleased)){
				if(this.record){
					Long currTime = System.currentTimeMillis();
					Long length = (currTime - this.stopWatch.get(keyReleased));
					this.stopWatch.remove(keyReleased);
					Note nPlayed = new Note(this.Keyano.whiteBones.get(keyReleased));
					nPlayed.setLength(length);
					CharNote newPair =  new CharNote(keyReleased, nPlayed);
					this.strokes.add(newPair);
					if(this.stopWatch.isEmpty()){
						this.stopWatch.put('*', System.currentTimeMillis());
					}
				}
				this.Keyano.releaseKey(arg0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void focus(){
		this.Keyano.grabFocus();
		return;
	}

	public void changeVolume(int i) {
		// TODO Auto-generated method stub
		this.Keyano.setVolume(this.Keyano.getVolume() + i);
	}
	
	public void setVolButtons(JButton inc, JButton dec){
		this.incVolButton = inc;
		this.decVolButton = dec;
	}
	
	public void setMixButtons(JButton rec,JButton pause,JButton play){
		this.recordButton = rec;
		this.pauseButton = pause;
		this.playButton = play;
	}
	
	public class CharNote{
		private char character = 0;
		private Note note = null;
		
		public CharNote(char c, Note n){
			this.note = n;
			this.character = c;
		}
		
		public char getCharacter(){
			return this.character;
		}
		
		public Note getNote(){
			return this.note;
		}
	}
}
