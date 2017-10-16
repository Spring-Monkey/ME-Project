package cs3500.music.model;

import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.Note;
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.assertEquals;

/**
 * Test class for the Read Only Song, an implementation of the view model operations.
 */
public class ReadOnlyTest {

  private MusicOperations<MusicBlock<INote>, INote> song1;
  private ViewModelOperations<MusicBlock<INote>, INote> readOnlySong;

  /**
   * Sets up the testing environment.
   */
  @Before
  public void setup() {
    this.song1 = new Song();
    this.readOnlySong = new ReadOnlySong(song1);
    this.song1.initSong(5, new Note(Note.Pitch.C, 4), 5, 120);
  }

  @Test
  public void testGetState() {
    this.song1.addNote(0, new Note(Note.Pitch.D, 4), 4);
    String original = this.readOnlySong.getState();
    assertEquals(original, "   C4  C#4   D4  D#4   E4 \n" +
        "0            X            \n" +
        "1            |            \n" +
        "2            |            \n" +
        "3            |            \n" +
        "4                         \n");
  }

  @Test (expected = IllegalStateException.class)
  public void testIllegalState() {
    MusicOperations song2 = new Song();
    ViewModelOperations readOnly2 = new ReadOnlySong(song2);
    readOnly2.getState();
  }

  /*@Test //TODO: FIX  TEST
  public void testGetBeatWorks() {
    this.song1.addNote(2, new Note(Note.Pitch.D, 4), 2);
    for (int i = 0; i < this.song1.getBeat(2).size(); i++) {
      String expected = "     ";
      if (i == 2) {
        expected = "  X  ";
      }
      assertEquals(expected, this.readOnlySong.getBeat(2).get(i).toString());
    }
  } */

  /*@Test //TODO: FIX TEST
  public void testGetAllBlocksOfNoteWorks() {
    this.song1.addNote(0, new Note(Note.Pitch.D, 4), 1);
    this.song1.addNote(2, new Note(Note.Pitch.D, 4), 2);
    List<MusicBlock<Note>> blocks =
        this.readOnlySong.getAllBlocksOfNote(new Note(Note.Pitch.D, 4));
    for (int i = 0; i < blocks.size(); i++) {
      String expected = "  X  ";
      if (i == 0 || i == 2) {
        expected = "     ";
      }
      if (i == 3) {
        expected = "  |  ";
      }
      assertEquals(expected, blocks.get(i).toString());
    }
  } */

  @Test (expected = IllegalArgumentException.class)
  public void testGetAllBlocksOutsideRange() {
    this.readOnlySong.getAllBlocksOfNote(new Note(Note.Pitch.C, 6));
  }

  /* TODO: Fix this test
  @Test
  public void testGetNoteHead() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    MusicBlock<Note> head =
        this.readOnlySong.getNoteHead(2, new Note(Note.Pitch.C, 4));
    assertEquals(head.toString(), "  X  ");
    assertEquals(head.getBeat().getBeatIndex(), 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNoteHeadFails() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    this.readOnlySong.getNoteHead(2, new Note(Note.Pitch.C_SHARP, 4));
  }*/

}
