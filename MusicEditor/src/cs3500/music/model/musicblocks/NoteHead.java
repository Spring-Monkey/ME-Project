package cs3500.music.model.musicblocks;


import java.util.ArrayList;
import java.util.List;

/**
 * NoteBlock representation of a Note's head.
 */
public class NoteHead implements MusicBlockHead<INote> {
  private INote note;
  private int duration;
  private ArrayList<MusicBlock<INote>> tail;
  private IBeat beat;

  /**
   * Constructor for the head of a note.
   * @param note Note to play
   * @param duration Duration to play this note
   */
  public NoteHead(INote note, int duration) {
    this.note = note;
    this.duration = duration;
    this.tail = new ArrayList<MusicBlock<INote>>();
  }

  @Override
  public String toString() {
    return "  X  ";
  }

  @Override
  public INote getNote() {
    return this.note;
  }

  // Helper function for addSustains and setSustains functions
  private final void addSustainsHelper(List<MusicBlock<INote>> l) {
    if (this.tail == null) {
      throw new IllegalStateException("NoteHead tail is not instantiated yet");
    }
    this.tail.addAll(l);
  }

  @Override
  public void addSustains(List<MusicBlock<INote>> l) {
    this.addSustainsHelper(l);
  }

  @Override
  public void setSustains(List<MusicBlock<INote>> l) {
    this.tail = new ArrayList<MusicBlock<INote>>();
    this.addSustainsHelper(l);
  }

  @Override
  public MusicBlockHead<INote> getNoteHead() throws IllegalArgumentException {
    return this;
  }

  @Override
  public boolean isARest() {
    return false;
  }

  @Override
  public IBeat getBeat() {
    if (this.beat == null) {
      throw new IllegalStateException("No beat initialized for this Note Head");
    }
    return this.beat;
  }

  @Override
  public boolean isNoteHead() {
    return true;
  }

  @Override
  public void setBeat(IBeat beat) {
    this.beat = beat;
  }

  @Override
  public List<MusicBlock<INote>> getTail() {
    if (this.tail == null) {
      throw new IllegalStateException("This note head does not have an initialized tail");
    }
    return this.tail;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public void addOneDuration() {
    this.duration++;
  }

  @Override
  public MusicBlock<INote> clone() {
    NoteHead tempNoteHead = new NoteHead(this.note, this.duration);
    tempNoteHead.setBeat(this.beat);
    return tempNoteHead;
  }
}
