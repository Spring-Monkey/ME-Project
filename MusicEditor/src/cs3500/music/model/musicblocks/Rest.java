package cs3500.music.model.musicblocks;

/**
 * NoteBlock representation of a Rest.
 */
public class Rest implements MusicBlock<INote> {
  private IBeat beat;

  public String toString() {
    return "     ";
  }

  @Override
  public void setBeat(IBeat beat) {
    this.beat = beat;
  }

  @Override
  public IBeat getBeat() {
    if (this.beat == null) {
      throw new IllegalStateException("This Rest has no valid Beat setup");
    }
    return this.beat;
  }

  @Override
  public boolean isNoteHead() {
    return false;
  }

  @Override
  public MusicBlockHead<INote> getNoteHead() throws IllegalArgumentException {
    throw new IllegalArgumentException("Requested note head from a rest block");
  }

  @Override
  public boolean isARest() {
    return true;
  }

  @Override
  public MusicBlock<INote> clone() {
    Rest tempRest = new Rest();
    tempRest.setBeat(this.beat);

    return tempRest;
  }
}
