package trombone;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import jm.constants.Instruments;



public class Trombone {
	private final int channel = 1;
	private int duration = 200;
	private Synthesizer synth;
	private MidiChannel T_bone;
	private int volume;
	private final int trombone = Instruments.TROMBONE;
	
	public Trombone() throws MidiUnavailableException{
		this.synth = MidiSystem.getSynthesizer();
		this.synth.open();
		
		Soundbank soundbank = synth.getDefaultSoundbank();
		Instrument[] instr = soundbank.getInstruments();
		synth.loadInstrument(instr[Instruments.TROMBONE]);
		
		MidiChannel[] channels = synth.getChannels();
		this.T_bone = channels[channel];
		this.T_bone.programChange(instr[trombone].getPatch().getBank(),instr[trombone].getPatch().getProgram());
	}

	public void playNote(Note n) throws InterruptedException{
		//TODO play Note
		T_bone.noteOn(n.MIDINoteNumber(), n.getVolume());
		Thread.sleep(n.getLength());
		T_bone.noteOff(n.MIDINoteNumber(), n.getVolume());
		return;
	}
	public void endNote(Note n){
		T_bone.noteOff(n.MIDINoteNumber(), n.getVolume());
		return;
	}
	
	public void playNote(int midiNoteNumber, int volume, double length) throws InterruptedException{
		T_bone.noteOn(midiNoteNumber, volume);
		Thread.sleep((long) length);
		T_bone.noteOff(midiNoteNumber);

	}
	
	public void playNote(Note n, long length) throws InterruptedException{
		this.playNote(n);
		Thread.sleep( length);
		this.endNote(n);

	}
	
}
