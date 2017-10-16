package cs3500.music.model;

import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.Note;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for Song Class.
 */
public class SongTest {

  private MusicOperations<MusicBlock<INote>, INote> song1;

  /**
   * Sets up the testing environment.
   */
  @Before
  public void setup() {
    this.song1 = new Song();
    this.song1.initSong(5, new Note(Note.Pitch.C, 4), 5, 120);
  }

  @Test
  public void testGetState() {
    this.song1.addNote(0, new Note(Note.Pitch.D, 4), 4);
    String original = this.song1.getState();
    assertEquals(original, "   C4  C#4   D4  D#4   E4 \n" +
        "0            X            \n" +
        "1            |            \n" +
        "2            |            \n" +
        "3            |            \n" +
        "4                         \n");
  }

  @Test (expected = IllegalStateException.class)
  public void testIllegalState() {
    MusicOperations song1 = new Song();
    song1.getState();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSongInitIllegalNumBeats() {
    MusicOperations song1 = new Song();
    song1.initSong(-1, new Note(Note.Pitch.C, 4), 4, 120);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSongInitIllegalNumPitces() {
    MusicOperations song1 = new Song();
    song1.initSong(4, new Note(Note.Pitch.C, 4), -1, 120);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSongInitIllegalTempo() {
    MusicOperations song1 = new Song();
    song1.initSong(4, new Note(Note.Pitch.C, 4), -1, 0);
  }

  @Test
  public void testSongInitSuccessful1() {
    MusicOperations song2 = new Song();
    song2.initSong(5, new Note(Note.Pitch.C, 4), 5,120 );
    assertEquals(song2.getState(), "   C4  C#4   D4  D#4   E4 \n" +
        "0                         \n" +
        "1                         \n" +
        "2                         \n" +
        "3                         \n" +
        "4                         \n");
  }

  @Test
  public void testSongInitSuccessful2() {
    MusicOperations song2 = new Song();
    song2.initSong(5, new Note(Note.Pitch.C, 4),
        new Note(Note.Pitch.F, 4), 120);
    assertEquals(song2.getState(), "   C4  C#4   D4  D#4   E4 \n" +
        "0                         \n" +
        "1                         \n" +
        "2                         \n" +
        "3                         \n" +
        "4                         \n");
  }

  @Test
  public void testAddNoteWorks() {
    MusicOperations song2 = new Song();
    song2.initSong(5, new Note(Note.Pitch.C, 4), 5, 120);
    this.song1.addNote(1, new Note(Note.Pitch.C_SHARP, 4), 3);
    assertNotEquals(song2.getState(), this.song1.getState());
    assertEquals(this.song1.getState(), "   C4  C#4   D4  D#4   E4 \n" +
        "0                         \n" +
        "1       X                 \n" +
        "2       |                 \n" +
        "3       |                 \n" +
        "4                         \n");
  }

  /*@Test (expected = IllegalArgumentException.class) //TODO: FIX TEST
  public void testAddNoteAlreadyAnotherNote() {
    this.song1.addNote(1, new Note(Note.Pitch.C_SHARP, 4), 3);
    this.song1.addNote(2, new Note(Note.Pitch.C_SHARP, 4), 2);
  } */

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalAddNoteBeat() {
    this.song1.addNote(8, new Note(Note.Pitch.C_SHARP, 4), 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalAddNoteDuration() {
    this.song1.addNote(4, new Note(Note.Pitch.C_SHARP, 4), 6);
  }

  /* TODO: Fix this test
  @Test
  public void testLoadState() {
    MusicOperations song2 = new Song();
    song1.addNote(0, new Note(Note.Pitch.D, 4), 4);
    String state1 = this.song1.getState();
    song2.loadState(state1);
    assertEquals(this.song1.getState(), song2.getState());
  }*/

  /* TODO: Fix these tests
  @Test
  public void testAddStateSimWorks() {
    MusicOperations song2 = new Song();
    song2.initSong(8, new Note(Note.Pitch.C, 4), 5, 120);
    this.song1.addNote(0, new Note(Note.Pitch.C, 4), 3);
    song2.addNote(4, new Note(Note.Pitch.D, 4), 3);
    this.song1.addStateSimultaneous(song2.getState());
    assertEquals(this.song1.getState(), "   C4  C#4   D4  D#4   E4 \n" +
        "0  X                      \n" +
        "1  |                      \n" +
        "2  |                      \n" +
        "3                         \n" +
        "4            X            \n" +
        "5            |            \n" +
        "6            |            \n" +
        "7                         \n");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddStateSimIllegalStartingNote() {
    MusicOperations song2 = new Song();
    song2.initSong(8, new Note(Note.Pitch.C_SHARP,4), 5, 120);
    this.song1.addStateSimultaneous(song2.getState());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddStateSimIllegalNumPitches() {
    MusicOperations song2 = new Song();
    song2.initSong(8, new Note(Note.Pitch.C, 4), 4, 120);
    this.song1.addStateSimultaneous(song2.getState());
  }

  @Test
  public void testAddStateConsWorks() {
    MusicOperations song2 = new Song();
    song2.initSong(8, new Note(Note.Pitch.C, 4), 5, 120);
    this.song1.addNote(0, new Note(Note.Pitch.C, 4), 3);
    song2.addNote(4, new Note(Note.Pitch.D, 4), 3);
    this.song1.addStateConsecutive(song2.getState());
    assertEquals(this.song1.getState(), "    C4  C#4   D4  D#4   E4 \n" +
        " 0  X                      \n" +
        " 1  |                      \n" +
        " 2  |                      \n" +
        " 3                         \n" +
        " 4                         \n" +
        " 5                         \n" +
        " 6                         \n" +
        " 7                         \n" +
        " 8                         \n" +
        " 9            X            \n" +
        "10            |            \n" +
        "11            |            \n" +
        "12                         \n");
  }*/

  /*@Test //TODO: FIX 2 TESTS
  public void testGetBeatWorks() {
    this.song1.addNote(2, new Note(Note.Pitch.D, 4), 2);
    for (int i = 0; i < this.song1.getBeat(2).size(); i++) {
      String expected = "     ";
      if (i == 2) {
        expected = "  X  ";
      }
      assertEquals(expected, this.song1.getBeat(2).get(i).toString());
    }
  }

  @Test
  public void testGetAllBlocksOfNoteWorks() {
    this.song1.addNote(0, new Note(Note.Pitch.D, 4), 1);
    this.song1.addNote(2, new Note(Note.Pitch.D, 4), 2);
    List<MusicBlock<Note>> blocks = this.song1.getAllBlocksOfNote(
        new Note(Note.Pitch.D, 4));
    for (int i = 0; i < blocks.size(); i++) {
      String expected = "     ";
      if (i == 0 || i == 2) {
        expected = "  X  ";
      }
      if (i == 3) {
        expected = "  |  ";
      }
      assertEquals(expected, blocks.get(i).toString());
    }
  }*/

  @Test (expected = IllegalArgumentException.class)
  public void testGetAllBlocksOutsideRange() {
    this.song1.getAllBlocksOfNote(new Note(Note.Pitch.C, 6));
  }

  @Test
  public void testGetNoteHeads() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    List<MusicBlock<INote>> heads = this.song1.getNoteHeads(2,
        new Note(Note.Pitch.C, 4));
    assertEquals(heads.get(0).toString(), "  X  ");
    assertEquals(heads.get(0).getBeat().getBeatIndex(), 1);
  }

  /*@Test (expected = IllegalArgumentException.class)
  public void testGetNoteHeadFails() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    this.song1.getNoteHeads(2, new Note(Note.Pitch.C_SHARP, 4));
  }*/ //TODO: FIX TEST

  @Test
  public void testChangeNoteDuration() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    String preChangeState = this.song1.getState();
    this.song1.changeNoteDuration(2,
        new Note(Note.Pitch.C, 4), 1);
    assertNotEquals(preChangeState, this.song1.getState());
  }

  @Test
  public void testChangeNotePitch() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    String preChangeState = this.song1.getState();
    this.song1.changeNotePitch(2,
        new Note(Note.Pitch.C, 4),
        new Note(Note.Pitch.D, 4));
    assertNotEquals(preChangeState, this.song1.getState());
  }

  @Test
  public void testRemoveNote() {
    this.song1.addNote(1, new Note(Note.Pitch.C, 4), 3);
    String preRemoveState = this.song1.getState();
    this.song1.removeNotes(2, new Note(Note.Pitch.C, 4));
    assertNotEquals(preRemoveState, this.song1.getState());
  }

  /*@Test (expected = IllegalArgumentException.class) //TODO: FIX TEST
  public void testRemoveNoteNoNote() {
    this.song1.removeNotes(3, new Note(Note.Pitch.C, 4));
  }*/

  @Test
  public void testSetNumBeats() {
    String preChange = this.song1.getState();
    assertEquals(preChange.split("\n").length, 6);
    this.song1.setNumBeats(3);
    assertEquals(this.song1.getState().split("\n").length, 4);
  }

  @Test
  public void testChangeNumBeats() {
    String preChange = this.song1.getState();
    assertEquals(preChange.split("\n").length, 6);
    this.song1.changeNumBeats(-2);
    assertEquals(this.song1.getState().split("\n").length, 4);
  }

  @Test
  public void testSetTempo() {
    int preChange = song1.getTempo();
    song1.setTempo(300);
    assertEquals(300, song1.getTempo());
    assertEquals(120, preChange);
  }

  @Test
  public void testGetNumBeats() {
    assertEquals(song1.getNumBeats(), 5);
  }

  @Test
  public void testAddNoteFromString() {
    song1.addNoteFromString("D4", 2, 1);
    assertEquals(song1.getLowestPitch().toString(), "C4");
    song1.addNoteFromString("E4", 2, 1);
    List<MusicBlock<INote>> blocks = song1.getNoteHeads(2, new Note(Note.Pitch.E, 4));
    assertEquals(blocks.size(), 1);
  }

}