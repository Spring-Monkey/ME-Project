package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;

import java.awt.Rectangle;

/**
 * Implementation of a Playhead, for giving to views.
 */
public class Playhead extends Rectangle implements IPlayhead {
  private int location;
  private ViewModelOperations model;
  private Rectangle rectangle;

  public Playhead(ViewModelOperations model) {
    this.location = 0;
    this.model = model;
  }

  @Override
  public int getBeat() {
    return this.location;
  }

  @Override
  public int changeBeat(int deltaBeat) throws IllegalArgumentException {
    if (this.location + deltaBeat < 0 || this.location + deltaBeat > this.model.getNumBeats()) {
      throw new IllegalArgumentException("Invalid beat change for playhead");
    }
    this.location += deltaBeat;
    return this.location;
  }

  @Override
  public void setBeat(int newBeat) throws IllegalArgumentException {
    if (newBeat < 0 || newBeat > this.model.getNumBeats()) {
      throw new IllegalArgumentException("Invalid beat set for playhead");
    }
    this.location = newBeat;
  }

  @Override
  public Rectangle getRectangle() {
    return new Rectangle(this.location * NotePanelWithPitches.BLOCK_DIMENSION, 0,
        1, this.model.getNumPitches() * NotePanelWithPitches.BLOCK_DIMENSION);
  }
}
