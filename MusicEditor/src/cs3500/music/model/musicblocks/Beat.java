package cs3500.music.model.musicblocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a beat in a song.
 */
public class Beat implements IBeat{
  private List<List<MusicBlock<INote>>> blocks;
  private INote lowestNote;
  private int numPitches;
  private int beatIndex;

  /**
   * Main Beat constructor.
   * @param numPitches Number of pitches in the range of this beat
   * @param lowestNote Lowest INote in the range of this beat
   * @param beatIndex Index of this beat in the song
   */
  public Beat(int numPitches, INote lowestNote, int beatIndex) {
    this.blocks = new ArrayList<>();
    this.numPitches = numPitches;
    this.lowestNote = lowestNote;
    this.beatIndex = beatIndex;
    for (int i = 0; i < numPitches; i++) {
      this.blocks.add(new ArrayList<>());
      this.blocks.get(i).add(new Rest());
      this.blocks.get(i).get(0).setBeat(this);
    }
  }

  /**
   * Generates the string representation (for the getState song function) of this beat.
   * @return String representation of this beat
   */
  public String toString() {
    StringBuilder res = new StringBuilder(numPitches * 5);
    for (List<MusicBlock<INote>> blockList : this.blocks) {
      String listRep = "     ";
      for (MusicBlock<INote> b : blockList) {
        if (b.isNoteHead()) {
          listRep = b.toString();
          break;
        }
        else if (listRep.equals("     ") && !b.isARest() && !b.isNoteHead()) {
          listRep = b.toString();
        }
      }
      res.append(listRep);
    }

    return res.toString();
  }

  /**
   * Adds the given block to this beat.
   * @param b Block to add
   * @param n INote to add block to
   */
  public void addBlock(MusicBlock b, INote n) {
    int index = n.absolutePitch() - this.lowestNote.absolutePitch();
    this.blocks.get(index).add(b);
    b.setBeat(this);
  }

  /**
   * Gets the ArrayList of ArrayLists of blocks in this beat.
   * @return ArrayList of blocks in this beat
   */
  public List<List<MusicBlock<INote>>> getBlocks() {
    return this.blocks;
  }

  /**
   * Returns the list of blocks at the given pitch.
   * @param n Pitch of block to return
   * @return Block at the given pitch in this beat
   */
  public List<MusicBlock<INote>> getBlocksOfPitch(INote n) {
    return this.blocks.get(n.absolutePitch() - this.lowestNote.absolutePitch());
  }

  /**
   * Replaces any blocks in this beat at the given pitch with a Rest.
   * @param n Pitch to replace with rest
   */
  public void removeNoteBlock(INote n) {
    this.blocks.set(n.absolutePitch() - this.lowestNote.absolutePitch(), new ArrayList<>());
    this.blocks.get(n.absolutePitch() - this.lowestNote.absolutePitch()).add(new Rest());
  }

  /**
   * Returns the Beat Index of this beat.
   * @return Beat Index of this beat
   */
  public int getBeatIndex() {
    return this.beatIndex;
  }
}
