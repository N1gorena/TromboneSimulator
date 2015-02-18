package trombone;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Note {
	private int octave;
	private int note;
	private int mod;
	private int midiNumber;
	
	private int volume;
	private Long length;
	
	private static final Map<Character, Integer> noteMod = Collections.unmodifiableMap(
				new HashMap<Character, Integer>() {/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

				{
					put('A', 9);
					put('B', 11);
					put('C', 0);
					put('D', 2);
					put('E', 4);
					put('F', 5);
					put('G', 7);
				}});
	
	//VARS ^
	//FUNCTS\/
	public Note(String Note , Long length, int volume){
		this.octave = Integer.parseInt(Note.substring(1)) + 1;
		
		this.volume = volume;
		this.length = length;
		
		this.note = noteMod.get(Note.charAt(0));
		
		if(Note.length() > 2){
			this.mod = (Note.charAt(2) == '#')?1:-1; 
		}
		else{
			this.mod = 0;
		}
		
		this.midiNumber = (this.octave * 12) + this.note + this.mod;

	}
	
	public Note(Note n){
		this.length = n.getLength();
		this.midiNumber = n.MIDINoteNumber();
		this.volume = n.volume;
	}
	
	public Note(int midiNoteNumber, Long length, int volume){
		this.midiNumber = midiNoteNumber;
		this.volume = volume;
		this.length = length;
		
	}
	
	public int MIDINoteNumber(){
		return this.midiNumber;
	}
	public int getVolume(){
		return this.volume;
	}
	public long getLength(){
		return this.length;
	}
	public void setLength(Long newLength){
		this.length = newLength;
	}

	public void setVolume(int volume2) {
		// TODO Auto-generated method stub
		this.volume = volume2;
		return;
		
	}
}
