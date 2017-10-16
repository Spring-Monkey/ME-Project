package cs3500.music.model;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MockSequencer implements Sequencer {
  boolean started;
  int seqTempo;
  StringBuilder builder;

  /**
   * constructs a mock sequencer.
   */
  public MockSequencer() {
    started = false;
    seqTempo = 0;
    builder = new StringBuilder();
  }

  @Override
  public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    for (int i = 0; i < sequence.getTracks()[0].size(); i++) {
      builder.append("Message: " +
          setSequenceHelper(sequence.getTracks()[0].get(i)
              .getMessage().getStatus()) + "\n");
      builder.append("Beat: " + sequence.getTracks()[0].get(i)
          .getTick() + "\n");
      builder.append("Pitch: " + sequence.getTracks()[0].get(i)
          .getMessage().getMessage()[1] + "\n");
      builder.append("Velocity: " + sequence.getTracks()[0].get(i)
          .getMessage().getMessage()[2] + "\n");
    }

    builder.deleteCharAt(builder.toString().length() - 1);
  }

  @Override
  public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
    //DOES NOTHING
  }

  @Override
  public Sequence getSequence() {
    return null;//DOES NOTHING.
  }

  /**
   * starts the song and sets started to true.
   */
  @Override
  public void start() {
    this.started = true;
  }

  /**
   * stops the song and sets started to false.
   */
  @Override
  public void stop() {
    this.started = false;
  }

  @Override
  public boolean isRunning() {
    return false;//DOES NOTHING.
  }

  @Override
  public void startRecording() {
    //DOES NOTHING.
  }

  @Override
  public void stopRecording() {
    //DOES NOTHING.
  }

  @Override
  public boolean isRecording() {
    return false;//DOES NOTHING.
  }

  @Override
  public void recordEnable(Track track, int channel) {
    //DOES NOTHING.
  }

  @Override
  public void recordDisable(Track track) {
    //DOES NOTHING.
  }

  @Override
  public float getTempoInBPM() {
    return 0;//DOES NOTHING.
  }

  /**
   * Sets the tempo of the sequencer.
   * @param bpm the beats per minutes of the sequence.
   */
  @Override
  public void setTempoInBPM(float bpm) {
    this.seqTempo = (int) bpm;
  }

  @Override
  public float getTempoInMPQ() {
    //does nothing
    return 0;
  }

  @Override
  public void setTempoInMPQ(float mpq) {
    this.seqTempo = (int) mpq;
  }

  @Override
  public void setTempoFactor(float factor) {
    //DOES NOTHING.
  }

  @Override
  public float getTempoFactor() {
    return 0;//DOES NOTHING.
  }

  @Override
  public long getTickLength() {
    return 0;//DOES NOTHING.
  }

  @Override
  public long getTickPosition() {
    return 0;//DOES NOTHING.
  }

  @Override
  public void setTickPosition(long tick) {
    //DOES NOTHING.
  }

  @Override
  public long getMicrosecondLength() {
    return 0;//DOES NOTHING.
  }

  @Override
  public Info getDeviceInfo() {
    return null;//DOES NOTHING.
  }

  @Override
  public void open() throws MidiUnavailableException {
    //DOES NOTHING.
  }

  @Override
  public void close() {
    //DOES NOTHING.
  }

  @Override
  public boolean isOpen() {
    return false;//DOES NOTHING.
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;//DOES NOTHING.
  }

  @Override
  public int getMaxReceivers() {
    return 0;//DOES NOTHING.
  }

  @Override
  public int getMaxTransmitters() {
    return 0;//DOES NOTHING.
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return null;//DOES NOTHING.
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;//DOES NOTHING.
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;//DOES NOTHING.
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;//DOES NOTHING.
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {
    //DOES NOTHING.
  }

  @Override
  public void setMasterSyncMode(SyncMode sync) {
    //DOES NOTHING.
  }

  @Override
  public SyncMode getMasterSyncMode() {
    return null;//DOES NOTHING.
  }

  @Override
  public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];//DOES NOTHING.
  }

  @Override
  public void setSlaveSyncMode(SyncMode sync) {
    //DOES NOTHING.
  }

  @Override
  public SyncMode getSlaveSyncMode() {
    return null;//DOES NOTHING.
  }

  @Override
  public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];//DOES NOTHING.
  }

  @Override
  public void setTrackMute(int track, boolean mute) {
    //DOES NOTHING.
  }

  @Override
  public boolean getTrackMute(int track) {
    return false;//DOES NOTHING.
  }

  @Override
  public void setTrackSolo(int track, boolean solo) {
    //DOES NOTHING.
  }

  @Override
  public boolean getTrackSolo(int track) {
    return false;//DOES NOTHING.
  }

  @Override
  public boolean addMetaEventListener(MetaEventListener listener) {
    return false;//DOES NOTHING.
  }

  @Override
  public void removeMetaEventListener(MetaEventListener listener) {
    //DOES NOTHING.
  }

  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];//DOES NOTHING.
  }

  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];//DOES NOTHING.
  }

  @Override
  public void setLoopStartPoint(long tick) {
    //DOES NOTHING.
  }

  @Override
  public long getLoopStartPoint() {
    return 0;//DOES NOTHING.
  }

  @Override
  public void setLoopEndPoint(long tick) {
    //DOES NOTHING.
  }

  @Override
  public long getLoopEndPoint() {
    return 0;//DOES NOTHING.
  }

  @Override
  public void setLoopCount(int count) {
    //DOES NOTHING.
  }

  @Override
  public int getLoopCount() {
    return 0;//DOES NOTHING.
  }

  /**
   * Returns a string representation the status message for a Midi Event.
   * @param status Status for midi event
   * @return String String representation of status
   */
  private String setSequenceHelper(int status) {
    switch (status) {
      case (144):
        return "NOTE ON";
      case (128):
        return "NOTE OFF";
      case (255):
        return "SYSTEM RESET";
      default:
        return "UNKNOWN MESSAGE";
    }
  }
}
