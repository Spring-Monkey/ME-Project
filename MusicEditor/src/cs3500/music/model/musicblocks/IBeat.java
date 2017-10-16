package cs3500.music.model.musicblocks;

import java.util.List;

public interface IBeat {

  /**
   * Generates the string representation (for the getState song function) of this beat.
   * @return String representation of this beat
   */
  public String toString();

  /**
   * Adds the given block to this beat.
   * @param b Block to add
   * @param n INote to add block to
   */
  public void addBlock(MusicBlock<INote> b, INote n);

  /**
   * Gets the ArrayList of ArrayLists of blocks in this beat.
   * @return ArrayList of blocks in this beat
   */
  public List<List<MusicBlock<INote>>> getBlocks();

  /**
   * Returns the list of blocks at the given pitch.
   * @param n Pitch of block to return
   * @return Block at the given pitch in this beat
   */
  public List<MusicBlock<INote>> getBlocksOfPitch(INote n);

  /**
   * Replaces any blocks in this beat at the given pitch with a Rest.
   * @param n Pitch to replace with rest
   */
  public void removeNoteBlock(INote n);

  /**
   * Returns the Beat Index of this beat.
   * @return Beat Index of this beat
   */
  public int getBeatIndex();
}
