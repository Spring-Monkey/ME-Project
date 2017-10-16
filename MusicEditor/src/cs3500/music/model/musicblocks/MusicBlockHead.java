package cs3500.music.model.musicblocks;

import java.util.List;

/**
 * Represents a Head MusicBlock in a song.
 */
public interface MusicBlockHead<T> extends MusicBlock<T> {
  /**
   * Adds the given list of NoteSustains to this NoteHead's tail ArrayList.
   * @param l List of NoteSustains to add
   */
  public void addSustains(List<MusicBlock<T>> l);

  /**
   * Sets this NoteHead's tail to the given List of NoteSustains.
   * @param l List of NoteSustains to set tail as
   */
  public void setSustains(List<MusicBlock<T>> l);

  /**
   * Gets the trailing note sustains for this NoteHead.
   * @return This NoteHead's trailing sustains
   */
  public List<MusicBlock<T>> getTail();

  /**
   * Returns the duration of the note starting at this NoteHead.
   * @return Duration of this note
   */
  public int getDuration();

  /**
   * Adds one to this note's duration.
   */
  public void addOneDuration();

  /**
   * Gets the note of this Note Head.
   * @return This NoteHead's Note
   */
  public INote getNote();
}
