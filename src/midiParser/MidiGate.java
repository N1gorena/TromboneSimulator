package midiParser;

import java.awt.Point;
import java.util.Vector;

import trombone.Note;
import jm.JMC;
import jm.music.data.*;
import jm.music.tools.*;
import jm.util.*;

public class MidiGate {
	private int beatInMS = 500;
	public MidiGate(){
	}

	public Vector<Note> parseMidiFile(String file){
		Score theScore = new Score("FullScore");
		Read.midi(theScore, file);
		theScore.clean();
		
		Part firstPart = theScore.getPart(0);
		
		Phrase firstPhrase = firstPart.getPhrase(0);
		
		jm.music.data.Note[] notes = firstPhrase.getNoteArray();
		
		Vector<Note> trumpNotes = new Vector<Note>();
		
		for( jm.music.data.Note n : notes){
			
			Note newNote = new Note(n.getPitch(), (long)(n.getDuration() * beatInMS) , n.getDynamic());
			trumpNotes.add(newNote);
			
		}
		
		return trumpNotes;
		
	}
}
