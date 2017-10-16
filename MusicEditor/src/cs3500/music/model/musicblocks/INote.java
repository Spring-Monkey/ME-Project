package cs3500.music.model.musicblocks;

public interface INote extends Comparable<INote> {

  public int hashCode();

  public boolean equals(Object that);

  /**
   * Returns the absolute pitch of this note - where c1 = 1.
   * @return Absolute pitch of this note
   */
  public int absolutePitch();

  public String toString();

  /**
   * Generates the string representation of this Note, padded to 5 characters.
   * @return Padded string representation of this Note
   */
  public String toPaddedString();

  /**
   * Returns an instance of the next note up from this one, chromatically.
   * @return Next note up from this one - following the chromatic scale
   */
  public INote nextNoteUp();

  /**
   * Returns an instance of the next note down from this one, chromatically.
   * @return Next note down from this one - following the chromatic scale
   */
  public INote nextNoteDown();

  /**
   * Returns the note that is delta pitches relative to this note, positive
   * or negative.
   * @param delta Change in note pitch to the desired note from this one
   * @return Note that is delta notes in pitch away from this one
   */
  public INote getRelativeNote(int delta);

  /**
   * Determines if this note is a sharp.
   * @return If this note is a sharp
   */
  public boolean isASharp();

  /**
   * Sets the channel of this note to the given channel.
   * @param channel Channel of this note
   */
  public void setChannel(int channel);

  /**
   * Sets the velocity of this note to the given value.
   * @param velocity Velocity of this note.
   */
  public void setVelocity(int velocity);

  /**
   * Returns the channel of this note.
   * @return Channel of this note
   */
  public int getChannel();

  /**
   * Returns the velocity of this note.
   * @return Velocity of this note
   */
  public int getVelocity();

  public INote clone();
}
