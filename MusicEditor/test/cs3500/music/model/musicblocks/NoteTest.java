package cs3500.music.model.musicblocks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for Note class.
 */
public class NoteTest {
  private Note c1;
  private Note a1;
  private Note aSharp1;
  private Note aSharp3;
  private Note b3;
  private Note b4;
  private Note c4;
  private Note cSharp5;
  private Note c5;

  /**
   * Sets up testing environment.
   */
  @Before
  public void setup() {
    this.c1 = new Note(Note.Pitch.C, 1);
    this.a1 = new Note(Note.Pitch.A, 1);
    this.aSharp1 = new Note(Note.Pitch.A_SHARP, 1);
    this.aSharp3 = new Note(Note.Pitch.A_SHARP, 3);
    this.b3 = new Note(Note.Pitch.B, 3);
    this.b4 = new Note(Note.Pitch.B, 4);
    this.c4 = new Note(Note.Pitch.C, 4);
    this.cSharp5 = new Note(Note.Pitch.C_SHARP, 5);
    this.c5 = new Note(Note.Pitch.C, 5);
  }

  @Test
  public void testNextNoteUp() {
    assertEquals(this.a1.nextNoteUp(), this.aSharp1);
    assertNotEquals(this.a1.nextNoteUp(), this.a1);
    assertEquals(this.b4.nextNoteUp(), this.c5);
    assertEquals(this.b4.nextNoteUp().nextNoteUp(), this.cSharp5);
  }

  @Test
  public void testNextNoteDown() {
    assertEquals(this.aSharp1.nextNoteDown(), this.a1);
    assertNotEquals(this.aSharp1.nextNoteDown(), this.aSharp1);
    assertEquals(this.c4.nextNoteDown(), this.b3);
    assertEquals(this.c4.nextNoteDown().nextNoteDown(), this.aSharp3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNextNoteDownIllegal() {
    this.c1.nextNoteDown();
  }

  @Test
  public void testGetRelativeNoteUp() {
    assertEquals(this.a1.getRelativeNote(1), this.aSharp1);
    assertEquals(this.b4.getRelativeNote(2), this.cSharp5);
    assertEquals(this.c4.getRelativeNote(12), this.c5);
  }

  @Test
  public void testGetRelativeNoteDown() {
    assertEquals(this.aSharp1.getRelativeNote(-1), this.a1);
    assertEquals(this.cSharp5.getRelativeNote(-2), this.b4);
    assertEquals(this.c5.getRelativeNote(-12), this.c4);
  }
}