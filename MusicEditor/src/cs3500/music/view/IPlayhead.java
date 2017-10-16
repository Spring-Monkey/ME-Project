package cs3500.music.view;

import java.awt.Rectangle;

/**
 * Interface for a generic Playhead object.
 */
public interface IPlayhead {
  /**
   * Returns the current beat of the playhead.
   * @return Current playhead beat
   */
  public int getBeat();

  /**
   * Changes the beat the playhead is located at, incrementing it by deltaBeat.
   * @param deltaBeat Change in playhead beat
   * @return New beat playhead is located at
   * @throws IllegalArgumentException If the playhead is moved to an invalid location
   */
  public int changeBeat(int deltaBeat) throws IllegalArgumentException;

  /**
   * Changes the beat the playhead is located at, to the new beat value.
   * @param newBeat New beat of this playhead
   * @throws IllegalArgumentException If given an invalid beat
   */
  public void setBeat(int newBeat) throws IllegalArgumentException;

  /**
   * Returns the Rectangle object representing the position of this playhead in a GUI view.
   * @return Rectangle object for this playhead
   */
  public Rectangle getRectangle();
}
