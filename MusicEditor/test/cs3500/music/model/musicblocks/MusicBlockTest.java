package cs3500.music.model.musicblocks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for MusicBlock classes.
 */
public class MusicBlockTest {

  private MusicBlock<INote> rest;
  private MusicBlock<INote> head;
  private MusicBlock<INote> sustain;

  /**
   * Sets up the testing environment.
   */
  @Before
  public void setup() {
    this.rest = new Rest();
    this.head = new NoteHead(new Note(Note.Pitch.C, 4), 2);
    this.sustain = new NoteSustain(this.head.getNoteHead());
  }

  @Test
  public void testToString() {
    assertEquals(this.rest.toString(), "     ");
    assertEquals(this.head.toString(), "  X  ");
    assertEquals(this.sustain.toString(), "  |  ");
  }

  @Test
  public void testIsARest() {
    assertTrue(this.rest.isARest());
    assertFalse(this.head.isARest());
    assertFalse(this.sustain.isARest());
  }

  @Test
  public void testGetNoteHeadWorks() {
    assertTrue(this.head.getNoteHead() == this.head);
    assertTrue(this.sustain.getNoteHead() == this.head);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNoteHeadFails() {
    this.rest.getNoteHead();
  }

  @Test
  public void testIsNoteHead() {
    assertFalse(this.rest.isNoteHead());
    assertFalse(this.sustain.isNoteHead());
    assertTrue(this.head.isNoteHead());
  }
}