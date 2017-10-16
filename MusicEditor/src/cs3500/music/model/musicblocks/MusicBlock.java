package cs3500.music.model.musicblocks;

/**
 * Represents any music block in a Song.
 */
public interface MusicBlock<T> {
  /**
   * Creates the string representation of this Music Block.
   * @return String representation - "  X  ", "  |  ", or "     "
   */
  public String toString();

  /**
   * Determines if this block is a rest block.
   * @return True if this is a rest block - false otherwise
   */
  public boolean isARest();

  /**
   * Returns the Note Head for this block - throws exception if a rest.
   * @return Note Head for this note block
   * @throws IllegalArgumentException If this is a rest
   */
  public MusicBlockHead<T> getNoteHead() throws IllegalArgumentException;

  /**
   * Sets this MusicBlock's containing beat to the given List of Music Blocks.
   * @param beat This block's containing beat
   */
  public void setBeat(IBeat beat);

  /**
   * Gets the beat that contains this MusicBlock.
   * @return The beat containing this block
   */
  public IBeat getBeat();

  /**
   * determines if a given music block is a note head or not.
   * @return boolean
   */
  public boolean isNoteHead();
  //makes it easier to create a list of only noteHeads to give to MIDI.

  /**
   * returns a clone of this music block.
   * @return MusicBlock
   */
  public MusicBlock<T> clone();
}
