package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlockHead;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;

/**
 * Represents a GUI and MIDI view implementation that will work together.
 */
public class CompositeView implements ViewInterface {
  ViewModelOperations model;
  GuiViewImpl gui;
  MidiViewImpl midi;
  IPlayhead playhead;
  Timer timer;

  public CompositeView() {
    gui = new GuiViewImpl();
    midi = new MidiViewImpl();
  }

  @Override
  public void setModel(ViewModelOperations model) {
    this.model = model;
    gui.setModel(model);
    midi.setModel(model);
  }

  @Override
  public void update() {
    gui.update();
    gui.updatePlayhead();
    midi.update();
    midi.updatePlayhead();
  }

  @Override
  public void showSong() {
    gui.showSong();
  }

  @Override
  public void initializeView(IPlayhead playhead, INote lowestPossibleNote) {
    this.playhead = playhead;
    midi.initializeView(playhead, lowestPossibleNote);
    gui.initializeView(playhead, lowestPossibleNote);
  }

  @Override
  public void startPlaying() {
    this.timer = new Timer(1000 / 60, (evt) -> {
      if (this.playhead.getBeat() < this.model.getNumBeats()) {
        this.playhead.setBeat((int)this.midi.getCurrentMidiTick());
      }
      if (this.playhead.getBeat() == this.model.getNumBeats()) {
        this.timer.stop();
      }
      this.gui.updatePlayhead();
    });
    midi.startPlaying();
    gui.startPlaying();
    this.timer.start();
  }

  @Override
  public void stopPlaying() {
    gui.stopPlaying();
    midi.stopPlaying();
    this.timer.stop();
  }

  @Override
  public void rewindToStart() {
    gui.rewindToStart();
    midi.rewindToStart();
  }

  @Override
  public void fastForwardToEnd() {
    gui.fastForwardToEnd();
    midi.fastForwardToEnd();
  }

  @Override
  public void setButtonListener(ActionListener listener) {
    gui.setButtonListener(listener);
    midi.setButtonListener(listener);
  }

  @Override
  public void setKeyboardListener(KeyListener listener) {
    this.gui.addKeyListener(listener);
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    this.gui.addMouseListener(listener);
  }

  @Override
  public void updatePlayhead() {
    gui.updatePlayhead();
    midi.updatePlayhead();
  }

  @Override
  public boolean isRunning() {
    return midi.isRunning();
  }

  @Override
  public String getNote(int xPos, int yPos) throws IllegalArgumentException {
    return this.gui.getNote(xPos, yPos);
  }

  @Override
  public void playNotes(List<MusicBlockHead<INote>> notes) {
    this.midi.playNotes(notes);
  }

}
