package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.MusicBlockHead;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created by Spencer on 3/18/2017.
 */
public class TextViewImpl implements ViewInterface {

  @Override
  public boolean isRunning() {
    //it doesn't ever run, sorry text view.-
    return false;
  }

  @Override
  public String getNote(int xPos, int yPos) throws IllegalArgumentException {
    return "";
  }

  @Override
  public void playNotes(List<MusicBlockHead<INote>> notes) {
    // Does nothing
  }

  @Override
  public void update() {
    try {
      this.currentState = this.model.getState();
    } catch (IllegalStateException e) {
      this.currentState = null;
    }
  }

  @Override
  public void initializeView(IPlayhead playhead, INote lowestPossibleNote) {
    // Does nothing with the playhead or lowestPossibleNote
    try {
      this.currentState = this.model.getState();
    } catch (IllegalStateException e) {
      this.currentState = null;
    }
  }

  @Override
  public void startPlaying() {
    // No playing in textview
  }

  @Override
  public void stopPlaying() {
    // No stopping in textview
  }

  @Override
  public void rewindToStart() {
    // No rewinding in textview
  }

  @Override
  public void fastForwardToEnd() {
    // No fastForwarding in textview
  }

  @Override
  public void setButtonListener(ActionListener listener) {
    // No buttons in textview
  }

  @Override
  public void setKeyboardListener(KeyListener listener) {
    // No keyboardlistener in textview
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    // No MouseListener in textview.
  }

  private ViewModelOperations<MusicBlock<INote>, INote> model;
  String currentState;

  @Override
  public void setModel(ViewModelOperations model) {
    this.model = model;
  }

  @Override
  public void showSong() {
    if (this.currentState != null) {
      System.out.println(this.currentState);
    }
    else {
      System.out.println("No initialized song");
    }
  }

  @Override
  public void updatePlayhead() {
    // Does nothing in a textview
  }
}
