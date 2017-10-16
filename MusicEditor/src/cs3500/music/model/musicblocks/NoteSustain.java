package cs3500.music.model.musicblocks;

/**
 * NoteBlock representation of a sustained Note.
 */
public class NoteSustain implements MusicBlock<INote> {
  private MusicBlockHead head;
  private IBeat beat;

  /**
   * Default Constructor for a NoteSustain.
   * @param head MusicBlockHead to connect this sustain to.
   */
  public NoteSustain(MusicBlockHead head) {
    this.head = head;
  }

  public String toString() {
    return "  |  ";
  }

  @Override
  public MusicBlockHead<INote> getNoteHead() throws IllegalArgumentException {
    return this.head;
  }

  public boolean isARest() {
    return false;
  }

  @Override
  public void setBeat(IBeat beat) {
    this.beat = beat;
  }

  @Override
  public IBeat getBeat() {
    if (this.beat == null) {
      throw new IllegalStateException("This NoteSustain has no valid Beat setup");
    }
    return this.beat;
  }

  @Override
  public boolean isNoteHead() {
    return false;
  }

  @Override
  public MusicBlock<INote> clone() {
    NoteSustain tempSustain = new NoteSustain(this.head);
    tempSustain.setBeat(this.beat);

    return tempSustain;
  }
}
