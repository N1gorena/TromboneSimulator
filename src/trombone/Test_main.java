package trombone;


import java.util.Vector;

import javax.sound.midi.MidiUnavailableException;

import midiParser.MidiGate;

public class Test_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Trombone trombone = null;
		
		try {
			trombone = new Trombone();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MidiGate parser = new MidiGate();
		Vector<Note> noteToPlay = parser.parseMidiFile("midiFiles/saria_song.mid");
		
		for(Note n : noteToPlay){
			try {
				trombone.playNote(n);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
