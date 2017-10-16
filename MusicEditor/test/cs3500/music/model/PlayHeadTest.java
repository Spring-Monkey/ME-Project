package cs3500.music.model;


import cs3500.music.model.musicblocks.Note;
import cs3500.music.view.Playhead;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the PlayHead class.
 */
public class PlayHeadTest {
  private Playhead playhead;

  /**
   * initializes the classes used to test the PlayHead.
   */
  @Before
  public void init() {
    MusicOperations song;
    ViewModelOperations readOnlySong;
    song = new Song();
    song.initSong(30, new Note(Note.Pitch.C, 4), 15, 120);
    readOnlySong = new ReadOnlySong(song);
    playhead = new Playhead(readOnlySong);
  }

  @Test
  public void testGetBeat() {
    assertEquals(0, playhead.getBeat());
  }

  @Test
  public void testSetBeat() {
    playhead.setBeat(5);
    assertEquals(5, playhead.getBeat());
    playhead.setBeat(11);
    assertEquals(11, playhead.getBeat());
    playhead.setBeat(30);
    assertEquals(30, playhead.getBeat());
    playhead.setBeat(0);
    assertEquals(0, playhead.getBeat());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetBeatNegative() {
    playhead.setBeat(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetBeatTooBig() {
    playhead.setBeat(35);
  }






}
