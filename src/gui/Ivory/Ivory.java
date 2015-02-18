package gui.Ivory;

import gui.MainFrame;
import gui.tromboneScreen.TromboneGUI;
import gui.tromboneScreen.TromboneScreen;
import gui.tromboneScreen.TromboneScreen.CharNote;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import trombone.Note;
import trombone.Trombone;

public class Ivory extends JPanel{
	
	TromboneGUI ivGui = null;
	TromboneScreen instrumentScreen;
	private int volume = 50;
	
	private Map<Character,Boolean> notesOn = null;
	
	private static final Map<Character,Integer> relativeLocationOfKeys; 
	static{
		relativeLocationOfKeys = new HashMap<Character,Integer>();
		
		relativeLocationOfKeys.put('q', 14);
		relativeLocationOfKeys.put('w', 15);
		relativeLocationOfKeys.put('e', 16);
		relativeLocationOfKeys.put('r', 17);
		relativeLocationOfKeys.put('t', 18);
		relativeLocationOfKeys.put('y', 19);
		relativeLocationOfKeys.put('u', 20);
		relativeLocationOfKeys.put('a', 21);
		relativeLocationOfKeys.put('s', 22);
		relativeLocationOfKeys.put('d', 23);
		relativeLocationOfKeys.put('f', 24);
		relativeLocationOfKeys.put('g', 25);
		relativeLocationOfKeys.put('h', 26);
		relativeLocationOfKeys.put('j', 27);
		relativeLocationOfKeys.put('z', 28);
		relativeLocationOfKeys.put('x', 29);
		relativeLocationOfKeys.put('c', 30);
		relativeLocationOfKeys.put('v', 31);
		relativeLocationOfKeys.put('b', 32);
		relativeLocationOfKeys.put('n', 33);
		relativeLocationOfKeys.put('m', 34);
		
	}
	
	public static final Map<Character,Note> whiteBones; 
	static{
		whiteBones = new HashMap<Character,Note>();
		whiteBones.put('*', new Note(0,(long)0,0)) ;
		
		whiteBones.put('q', new Note("A3", (long) 0, 0));
		whiteBones.put('w', new Note("B3", (long) 0, 0));
		whiteBones.put('e', new Note("C3", (long) 0, 0));
		whiteBones.put('r', new Note("D3", (long) 0, 0));
		whiteBones.put('t', new Note("E3", (long) 0, 0));
		whiteBones.put('y', new Note("F3", (long) 0, 0));
		whiteBones.put('u', new Note("G3", (long) 0, 0));
		whiteBones.put('a', new Note("A4", (long) 0, 0));
		whiteBones.put('s', new Note("B4", (long) 0, 0));
		whiteBones.put('d', new Note("C4", (long) 0, 0));
		whiteBones.put('f', new Note("D4", (long) 0, 0));
		whiteBones.put('g', new Note("E4", (long) 0, 0));
		whiteBones.put('h', new Note("F4", (long) 0, 0));
		whiteBones.put('j', new Note("G4", (long) 0, 0));
		whiteBones.put('z', new Note("A5", (long) 0, 0));
		whiteBones.put('x', new Note("B5", (long) 0, 0));
		whiteBones.put('c', new Note("C5", (long) 0, 0));
		whiteBones.put('v', new Note("D5", (long) 0, 0));
		whiteBones.put('b', new Note("E5", (long) 0, 0));
		whiteBones.put('n', new Note("F5", (long) 0, 0));
		whiteBones.put('m', new Note("G5", (long) 0, 0));
		
	}
	
	private JTextField keyboard;
	private Trombone sounder;
	private int keyWidth;
	private int keyHeight;
	private int extra;
	private Vector<Integer> keysDepressed = null;;
	
	
	
	
	public Ivory(String string , TromboneScreen instrumentScreen) {
		this.setFocusable(true);
		// TODO Auto-generated constructor stub
		this.instrumentScreen = instrumentScreen;
		
		this.keysDepressed = new Vector<Integer>();
		this.notesOn = new HashMap<Character,Boolean>();
		
		try {
			this.sounder = new Trombone();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setInstrument(TromboneGUI instrument){
		this.ivGui = instrument;
		return;
	}

	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		int numberOfKeys = 52;
		int blackNumberOfKeys = 36;
		drawKeyboard(numberOfKeys, blackNumberOfKeys ,g2);
		
		
		
	}

	private void reflectPlay(Graphics2D brush) {
		
		if(!this.keysDepressed.isEmpty()){
			for(Integer i : this.keysDepressed){
				brush.setColor(Color.decode("#DBDBDB"));
				brush.draw3DRect(extra+2+(i.intValue()*this.keyWidth), 0,this.keyWidth, this.keyHeight, false);
				brush.fillRect(extra+(i.intValue()*this.keyWidth), 0, this.keyWidth, this.keyHeight);
				brush.setColor(Color.WHITE);
				brush.drawRect(extra+(i.intValue()*this.keyWidth), 0, this.keyWidth, this.keyHeight);
				
				brush.setColor(Color.RED);
				brush.draw3DRect(extra+2+(i.intValue()*this.keyWidth), 0, this.keyWidth, this.keyHeight, false);
				
			}
		}
		
	}

	private void drawKeyboard(int numberOfKeys, int blackNumberOfKeys, Graphics2D brush) {
		int xPos = 0;
		int y = 0;
		this.keyWidth = this.instrumentScreen.getWidth()/numberOfKeys;
		int width = this.keyWidth;
		this.keyHeight = this.getHeight();
		int height = this.keyHeight;
		this.extra = (this.instrumentScreen.getWidth() - (keyWidth*numberOfKeys))/2;
			
		brush.setColor(Color.DARK_GRAY);
		brush.drawRect(xPos, y, extra, height);
		brush.fillRect(xPos, y, extra, height);
		
	
		xPos = extra;
		for(int i = 0 ; i < numberOfKeys ; i++){
			
			brush.setColor(Color.WHITE);
			brush.drawRect(xPos, y, width, height);
			brush.fillRect(xPos, y, width, height);
			brush.setColor(Color.BLACK);
			brush.drawRect(xPos, y, width, height);
			xPos += width;
		}
		
		brush.setColor(Color.DARK_GRAY);
		brush.drawRect(xPos, y, extra, height);
		brush.fillRect(xPos, y, extra, height);
		
		reflectPlay(brush);
		
		int blackKeyWidth = width/4;
		xPos = (width*3)/4 + extra;
		int blackKeyHeight = (2*height)/3;
		boolean true2false3 = true;
		int currentCount = 0;
		brush.setColor(Color.BLACK);
		
		for(int i = 0 ; i < blackNumberOfKeys ; i++){
				if(true2false3){
					if(currentCount < 2){
						currentCount++;
						brush.drawRect(xPos, y, blackKeyWidth*2, blackKeyHeight);
						brush.fillRect(xPos, y, blackKeyWidth*2, blackKeyHeight);
					}
					else{
						currentCount = 0;
						i--;
						true2false3 = false;	
					}
				}
				else{
					if(currentCount < 3){
						currentCount++;
						brush.drawRect(xPos, y, blackKeyWidth*2, blackKeyHeight);
						brush.fillRect(xPos, y, blackKeyWidth*2, blackKeyHeight);

					}
					else{
						currentCount = 0;
						i--;
						true2false3 = true;
						
					}
				}
				xPos += width;	
		}
	}

	public void playKey(KeyEvent e) {
		// TODO Auto-generated method stub
		char keyPressed = e.getKeyChar();
		
		instrumentScreen.reflectNote(keyPressed);
		
		
		if(this.notesOn.containsKey(keyPressed)){
			if(!notesOn.get(keyPressed)){
				this.pushKeyDown(e.getKeyChar());
				this.notesOn.put(keyPressed, true);
				Note toPlay = whiteBones.get(keyPressed);
				toPlay.setVolume(this.volume);
				try {
					sounder.playNote(toPlay);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else{
			this.pushKeyDown(e.getKeyChar());
			this.notesOn.put(keyPressed, true);
			Note toPlay = whiteBones.get(e.getKeyChar());
			toPlay.setVolume(this.volume);
			try {
				sounder.playNote(toPlay);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	private void pushKeyDown(char keyChar) {
		// TODO Auto-generated method stub
		if(relativeLocationOfKeys.containsKey(keyChar)){
			this.keysDepressed.add(relativeLocationOfKeys.get(keyChar));
		}
		this.repaint();
	}

	public void releaseKey(KeyEvent e) {
		// TODO Auto-generated method stub
		char keyReleased = e.getKeyChar();
		
		if(this.notesOn.containsKey(keyReleased)){
			if(this.notesOn.get(keyReleased)){
				
				Note toEnd = whiteBones.get(e.getKeyChar());
				toEnd.setVolume(this.volume);
				sounder.endNote(toEnd);
				this.letKeyUp(e.getKeyChar());
				this.notesOn.put(keyReleased, false);
			}
		}
	}

	private void letKeyUp(char keyChar) {
		// TODO Auto-generated method stub
		if(relativeLocationOfKeys.containsKey(keyChar)){
			this.keysDepressed.remove(relativeLocationOfKeys.get(keyChar));
		}
		this.repaint();
		
	}
	
	public int getVolume(){
		return this.volume;
	}
	
	public void setVolume(int newVolume){
		this.volume = newVolume;
	}
	
	public void simulatePlay(Vector<CharNote> strokes){
		
		for(CharNote cn : strokes){
			
			Note N = cn.getNote();
			
			try {
				this.sounder.playNote(N,N.getLength());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
