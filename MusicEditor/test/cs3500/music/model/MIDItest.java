package cs3500.music.model;

import cs3500.music.EditorCtrl;
import cs3500.music.MusicEditor;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.Note;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.Playhead;
import cs3500.music.view.ViewInterface;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the MIDI implementation of the dynamic view interface.
 */
public class MIDItest {
  private MusicOperations song = new Song();
  private ViewModelOperations readOnlySong = new ReadOnlySong(song);
  private MockSequencer mock = new MockSequencer();
  private MidiViewImpl midi = new MidiViewImpl(mock);

  @Before
  public void setUp() {
    song.initSong(10, new Note(Note.Pitch.A, 3), 15, 120);
    midi.setModel(readOnlySong);
  }


  @Test
  public void testStartStop() {
    assertEquals(false, mock.started);
    midi.startPlaying();
    assertEquals(true, mock.started);
    midi.stopPlaying();
    assertEquals(false, mock.started);
  }

  @Test
  public void testSetTempo() {
    midi.update();
    assertEquals(120, mock.seqTempo);
    song.setTempo(300);
    midi.update();
    assertEquals(300, mock.seqTempo);
  }

  @Test
  public void testSetSequence() {
    Note tempNote = new Note(Note.Pitch.A, 3);
    tempNote.setVelocity(120);
    song.addNote(1, tempNote, 2);
    midi.initializeView(new Playhead(new ReadOnlySong(song)), new Note(Note.Pitch.A, 1));
    assertEquals( "Message: NOTE ON\n" +
        "Beat: 1\n" +
        "Pitch: 33\n" +
        "Velocity: 120\n" +
        "Message: NOTE OFF\n" +
        "Beat: 3\n" +
        "Pitch: 33\n" +
        "Velocity: 120\n" +
        "Message: SYSTEM RESET\n" +
        "Beat: 3\n" +
        "Pitch: 47\n" +
        "Velocity: 0", mock.builder.toString());
  }

  @Test
  public void mockOutput() throws IOException, InvalidMidiDataException {
    MusicOperations<MusicBlock<INote>, INote> model =
        MusicReader.parseFile(new FileReader("resources/mary-little-lamb.txt"),
            new Song.Builder());
    ViewInterface view = new MidiViewImpl(mock);
    EditorCtrl controller = new MusicEditor(model, view);
    controller.initialize();

    assertNotEquals(mock.builder.toString().length(), 0);
  }
}
