package cs3500.music.model.musicblocks;

import java.util.Objects;

/**
 * Represents a single note, with a pitch and octave.
 */
public class Note implements INote {
  /**
   * Enum representation of a pitch - from A to G-Sharp.
   */
  public enum Pitch {
    C("C"),
    C_SHARP("C#"),
    D("D"),
    D_SHARP("D#"),
    E("E"),
    F("F"),
    F_SHARP("F#"),
    G("G"),
    G_SHARP("G#"),
    A("A"),
    A_SHARP("A#"),
    B("B");

    private String asString;
    private static Pitch[] pitches = values();

    // Stores the string representation of the enum as well
    private Pitch(String asString) {
      this.asString = asString;
    }

    /**
     * Returns the next pitch enumeration.
     * @return Next pitch enumeration after this one
     */
    public Pitch next() {
      return pitches[(this.ordinal() + 1) % pitches.length];
    }

    /**
     * Returns the previous pitch enumeration.
     * @return Previous pitch enumeration before this one
     */
    public Pitch prev() {
      return pitches[(this.ordinal() + 11) % pitches.length];
    }

    /**
     * Creates a pitch from the given pitch's string.
     * @param s String to create Pitch from
     * @return Pitch representing the given string
     */
    public static Pitch fromString(String s) {
      for (Pitch p : pitches) {
        if (p.asString.equals(s)) {
          return p;
        }
      }
      throw new IllegalArgumentException("No pitch for the given pitch string found");
    }
  }

  private int octave;
  private Pitch pitch;
  private int channel;
  private int velocity;

  /**
   * Public constructor, given a pitch and octave to construct with.
   * @param pitch Pitch (A through G-Sharp) of the note to construct
   * @param octave Octave of this note
   */
  public Note(Pitch pitch, int octave) {
    if (octave < 1) {
      throw new IllegalArgumentException(
          "Invalid octave value (must be greater than 1): " + octave);
    }
    if (octave > 999) {
      throw new IllegalArgumentException(
          "Invalid octave value (must be less than 1000): " + octave);
    }
    this.pitch = pitch;
    this.octave = octave;
    this.channel = 1;
    this.velocity = 127;
  }

  @Override
  public INote clone() {
    Note newNote = new Note(this.pitch, this.octave);
    newNote.setVelocity(this.velocity);
    newNote.setChannel(this.channel);
    return newNote;
  }

  /**
   * Constructor that uses a string representation
   * of a note to create a Note object.
   * @param noteString String representation of a note
   */
  public Note(String noteString) {
    if (noteString.length() > 0) {
      int index = noteString.indexOf("#");
      Pitch p;
      if (index == -1) {
        p = Pitch.fromString(noteString.substring(0,1));
        index = 0;
      }
      else {
        p = Pitch.fromString(noteString.substring(0, 2));
      }
      int octave;
      try {
        octave = Integer.parseInt(noteString.substring(index + 1, noteString.length()));
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid note octave given: "
            + noteString.substring(index + 1, noteString.length()));
      }

      this.octave = octave;
      this.pitch = p;
      this.channel = 1;
      this.velocity = 127;
    }
  }

  @Override
  public int compareTo(INote that) {
    return this.absolutePitch() - that.absolutePitch();
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Note)) {
      return false;
    }
    return this.pitch == ((Note) that).pitch
        && this.octave == ((Note) that).octave;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.octave);
  }

  /**
   * Returns the absolute pitch of this note - where c1 = 1.
   * @return Absolute pitch of this note
   */
  public int absolutePitch() {
    return Pitch.values().length * (this.octave - 1) + this.pitch.ordinal();
  }

  @Override
  public String toString() {
    return this.pitch.asString + this.octave;
  }

  /**
   * Generates the string representation of this Note, padded to 5 characters.
   * @return Padded string representation of this Note
   */
  public String toPaddedString() {
    String res = this.toString();
    switch (res.length()) {
      case 2:
        return "  " + res + " ";
      case 3:
        return " " + res + " ";
      case 4:
        return " " + res;
      case 5:
        return res;
      default:
        throw new IllegalArgumentException("Unable to make padded string for note: " + res);
    }
  }

  /**
   * Returns an instance of the next note up from this one, chromatically.
   * @return Next note up from this one - following the chromatic scale
   */
  public INote nextNoteUp() {
    int nextOctave = this.octave;
    if (this.pitch == Pitch.B) {
      nextOctave++;
    }
    return new Note(this.pitch.next(), nextOctave);
  }

  /**
   * Returns an instance of the next note down from this one, chromatically.
   * @return Next note down from this one - following the chromatic scale
   */
  public INote nextNoteDown() {
    int prevOctave = this.octave;
    if (this.pitch == Pitch.C) {
      prevOctave--;
    }
    return new Note(this.pitch.prev(), prevOctave);
  }

  /**
   * Returns the note that is delta pitches relative to this note, positive
   * or negative.
   * @param delta Change in note pitch to the desired note from this one
   * @return Note that is delta notes in pitch away from this one
   */
  public INote getRelativeNote(int delta) {
    INote res = this.clone();
    if (delta >= 0) {
      for (int i = 0; i < delta; i++) {
        res = res.nextNoteUp();
      }
    }
    else {
      for (int i = 0; i > delta; i--) {
        res = res.nextNoteDown();
      }
    }

    return res;
  }

  /**
   * Determines if this note is a sharp.
   * @return If this note is a sharp
   */
  public boolean isASharp() {
    return this.pitch == Pitch.A_SHARP || this.pitch == Pitch.C_SHARP
        || this.pitch == Pitch.D_SHARP || this.pitch == Pitch.F_SHARP
        || this.pitch == Pitch.G_SHARP;
  }

  /**
   * Sets the channel of this note to the given channel.
   * @param channel Channel of this note
   */
  public void setChannel(int channel) {
    this.channel = channel;
  }

  /**
   * Sets the velocity of this note to the given value.
   * @param velocity Velocity of this note.
   */
  public void setVelocity(int velocity) {
    if (velocity < 0) {
      throw new IllegalArgumentException("Invalid note velocity given: " + velocity);
    }
    this.velocity = velocity;
  }

  /**
   * Returns the channel of this note.
   * @return Channel of this note
   */
  public int getChannel() {
    return this.channel;
  }

  /**
   * Returns the velocity of this note.
   * @return Velocity of this note
   */
  public int getVelocity() {
    return this.velocity;
  }
}
