package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlockHead;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * View Interface for GUI and Midi views - interacts with the ViewModelOperations
 * interface for reading from the model
 * Created by Spencer on 3/18/2017.
 */
public interface ViewInterface {
  /**
   * Sets the ViewModel for this View, for read-only access to the model.
   */
  public void setModel(ViewModelOperations model);

  /**
   * Updates the current state of the song.
   */
  public void update();

  /**
   * Shows the current state of the song.
   */
  public void showSong();

  /**
   * Initialize this view, showing the window or loading the song, with
   * the given playhead object.
   * @param playHead Playhead object to initialize view with
   * @param lowestPossibleNote Lowest note possible to display in this view
   */
  public void initializeView(IPlayhead playHead, INote lowestPossibleNote);

  /**
   * Start playing the song.
   */
  public void startPlaying();

  /**
   * Stop playing the song.
   */
  public void stopPlaying();

  /**
   * Rewind the song to the start.
   */
  public void rewindToStart();

  /**
   * Fastforward a song to the end.
   */
  public void fastForwardToEnd();

  /**
   * Gives every button in this view the given ButtonListener.
   * @param listener ButtonListener for each button in this view
   */
  public void setButtonListener(ActionListener listener);

  /**
   * Sets the KeyboardListener for this view.
   * @param listener KeyboardListener to listen for key events
   *                 in this view
   */
  public void setKeyboardListener(KeyListener listener);

  /**
   * Sets the MouseListener for this view.
   * @param listener MouseListener that determines the effects of mouse clicks.
   */
  public void setMouseListener(MouseListener listener);

  /**
   * Updates the playhead in this current view.
   */
  public void updatePlayhead();

  /**
   * determines if the music editor is currently playing or not.
   * @return boolean
   */
  public boolean isRunning();

  /**
   * Returns a Note in string format from the given position on screen.
   * @param xPos x position of mouse click.
   * @param yPos y position of mouse click.
   * @return String a string representation of the note.
   * @throws IllegalArgumentException If the mouse is not on a key.
   */
  public String getNote(int xPos, int yPos) throws IllegalArgumentException;

  /**
   * Immediately plays the list of given notes.
   * @param notes list of notes.
   */
  public void playNotes(List<MusicBlockHead<INote>> notes);

}
