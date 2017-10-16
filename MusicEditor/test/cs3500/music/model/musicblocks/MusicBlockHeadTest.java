package cs3500.music.model.musicblocks;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for MusicBlockHead.
 */
public class MusicBlockHeadTest {

  private MusicBlockHead head1;
  private MusicBlockHead head2;

  /**
   * Sets up the testing environment.
   */
  @Before
  public void setup() {
    this.head1 = new NoteHead(new Note(Note.Pitch.C, 4), 2);
    this.head2 = new NoteHead(new Note(Note.Pitch.D, 4), 4);
  }

  @Test
  public void testAddSustains() {
    ArrayList<NoteSustain> tail = new ArrayList<>();
    tail.add(new NoteSustain(this.head1));
    this.head1.addSustains(tail);
    this.head2.setSustains(tail);
    assertEquals(this.head1.getTail(), this.head2.getTail());
  }

  @Test
  public void testGetDuration() {
    assertEquals(this.head1.getDuration(), 2);
    assertEquals(this.head2.getDuration(), 4);
  }

  @Test
  public void testAddOneDuration() {
    assertEquals(this.head1.getDuration(), 2);
    this.head1.addOneDuration();
    assertEquals(this.head1.getDuration(), 3);
  }

}