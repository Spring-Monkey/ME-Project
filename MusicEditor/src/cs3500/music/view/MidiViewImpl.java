package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.MusicBlockHead;
import cs3500.music.model.musicblocks.NoteHead;

import javax.sound.midi.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl implements ViewInterface {
  private final Sequencer sequencer;
  private final Synthesizer synth;
  private final Receiver receiver;
  private ViewModelOperations<MusicBlock, INote> model;
  IPlayhead playhead;

  /**
   * Creates a new MidiViewImpl, getting a sequencer
   * from the MidiSystem.
   */
  public MidiViewImpl() {
    Sequencer tempSeq;
    Synthesizer tempSynth;
    Receiver tempReceiver;

    try {
      tempSeq = MidiSystem.getSequencer();
      tempSynth = MidiSystem.getSynthesizer();
      tempSeq.open();
      tempSynth.open();
      tempReceiver = tempSynth.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      tempSeq = null;
      tempSynth = null;
      tempReceiver = null;
    }
    this.sequencer = tempSeq;
    this.synth = tempSynth;
    this.receiver = tempReceiver;
  }

  /**
   * Convenience constructor for mock MIDI testing OF SEQUENCER ONLY!
   * @param mock A mock sequencer
   */
  public MidiViewImpl(Sequencer mock) {
    sequencer = mock;
    synth = null;
    receiver = null;
  }

  @Override
  public void updatePlayhead() {
    this.sequencer.setTickPosition(this.playhead.getBeat());
    sequencer.setTempoInMPQ(model.getTempo());
  }

  @Override
  public void startPlaying() {
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(model.getTempo());
  }

  @Override
  public void stopPlaying() {
    this.sequencer.stop();
    this.sequencer.setTempoInMPQ(model.getTempo());
  }

  @Override
  public void rewindToStart() {
    this.sequencer.setTickPosition(0);
    sequencer.setTempoInMPQ(model.getTempo());
  }

  @Override
  public void fastForwardToEnd() {
    this.sequencer.setTickPosition(this.sequencer.getTickLength());
    sequencer.setTempoInMPQ(model.getTempo());
  }

  @Override
  public void setModel(ViewModelOperations model) {
    this.model = model;
  }


  @Override
  public void update() {
    try {
      Sequence newSequence = new Sequence(Sequence.PPQ, 1);
      Track currentTrack = newSequence.createTrack();
      for (MusicBlockHead<INote> b : this.model.getNoteHeads()) {
        addNote(currentTrack, b, b.getBeat().getBeatIndex());
      }
      this.sequencer.setSequence(newSequence);
      this.sequencer.setTempoInMPQ(model.getTempo());
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initializeView(IPlayhead playhead, INote lowestPossibleNote) {
    // Do nothing with the lowest possible note
    this.playhead = playhead;
    try {
      this.buildSequencer();
    } catch (InvalidMidiDataException e) {
      System.out.println("Incorrect Midi data, could not build Sequencer");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Builds the sequence for the sequencer.
   * @throws InvalidMidiDataException If data does not create valid Midi Data
   */
  private void buildSequencer() throws InvalidMidiDataException {
    Sequence currentSequence = new Sequence(Sequence.PPQ, 1);
    Track currentTrack = currentSequence.createTrack();

    for (MusicBlockHead<INote> b : this.model.getNoteHeads()) {
      addNote(currentTrack, b, b.getBeat().getBeatIndex());
    }
    this.sequencer.setTempoInMPQ(model.getTempo());

    this.sequencer.setSequence(currentSequence);
  }

  /**
   * creates a MIDI representation of a note and adds it to a track.
   * @param track The track
   * @param note The current note
   * @param currentBeat The current beat of the song
   */
  private void addNote(Track track, MusicBlockHead note, int currentBeat)
        throws InvalidMidiDataException {
    int endBeat = note.getDuration() + currentBeat;
    int pitchValue = note.getNote().absolutePitch();
    int channel = note.getNote().getChannel() - 1;
    int velocity = note.getNote().getVelocity();

    ShortMessage messageOn = new ShortMessage(ShortMessage.NOTE_ON, channel,
        pitchValue, velocity);
    ShortMessage messageOff = new ShortMessage(ShortMessage.NOTE_OFF, channel,
        pitchValue, velocity);
    MidiEvent eventOn = new MidiEvent(messageOn, currentBeat);
    MidiEvent eventOff = new MidiEvent(messageOff, endBeat);

    track.add(eventOn);
    track.add(eventOff);
  }

  @Override
  public void showSong() {
    playSong();
  }

  /**
   * placeholder method to get the song to play. Needed for both to work.
   */
  private void playSong() {
    this.sequencer.start();
    this.sequencer.setTempoInMPQ(model.getTempo());
  }

  @Override
  public void setButtonListener(ActionListener listener) {
    //Midi has no buttons, not used.
  }

  @Override
  public void setKeyboardListener(KeyListener listener) {
    //Midi is never in focus, not used.
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    //Midi is never in focus, not used.
  }

  @Override
  public boolean isRunning() {
    return sequencer.isRunning();
  }

  @Override
  public String getNote(int xPos, int yPos) throws IllegalArgumentException {
    return ""; //Does nothing.
  }

  @Override
  public void playNotes(List<MusicBlockHead<INote>> notes) {
    for(MusicBlockHead<INote> i : notes) {
      try {
        playNote(i);
      } catch (InvalidMidiDataException e) {
        System.out.println("Invalid Midi data given to synthesizer");
      }
    }
  }

  public long getCurrentMidiTick() {
    return this.sequencer.getTickPosition();
  }


  /**
   * play a single note through synth. Used by playNotes.
   * @param note
   * @throws InvalidMidiDataException
   */
  private void playNote(MusicBlockHead<INote> note) throws InvalidMidiDataException {
    int pitchValue = note.getNote().absolutePitch();
    int channel = note.getNote().getChannel() - 1;
    int velocity = note.getNote().getVelocity();

    ShortMessage messageOn = new ShortMessage(ShortMessage.NOTE_ON, channel,
      pitchValue, velocity);
    ShortMessage messageOff = new ShortMessage(ShortMessage.NOTE_OFF, channel,
      pitchValue, velocity);

    receiver.send(messageOn, 0);
    receiver.send(messageOff, this.model.getTempo() * note.getDuration());
  }

}
