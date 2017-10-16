package cs3500.music;

import cs3500.music.model.MusicOperations;
import cs3500.music.model.ReadOnlySong;
import cs3500.music.model.Song;
import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.MusicBlockHead;
import cs3500.music.model.musicblocks.Note;
import cs3500.music.util.ButtonListener;
import cs3500.music.util.KeyboardListener;
import cs3500.music.util.MouseListener;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IPlayhead;
import cs3500.music.view.Playhead;
import cs3500.music.view.ViewFactory;
import cs3500.music.view.ViewInterface;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;


public class MusicEditor implements EditorCtrl {
  private MusicOperations<MusicBlock<INote>, INote> model;
  private ViewInterface view;

  private IPlayhead playhead;

  private Map<String, Runnable> buttonActionMap;
  private Map<Integer, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  private Map<Integer, Consumer<MouseEvent>> mouseClickedMap;
  private Map<Integer, Consumer<MouseEvent>> mousePressedMap;
  private Map<Integer, Consumer<MouseEvent>> mouseReleasedMap;

  // Variables for EC Part 1
  private String newNoteString;
  private int    newNoteStartBeat;
  private Timer  newNoteTimer;

  // Variables for EC Part 2
  private boolean practiceMode;
  private List<PracticeModeNote> toPlay;
  private List<PracticeModeNote> havePlayed;

  // Methods for EC Part 2
  // Recalculates toPlay using current beat
  private void setToPlay() {
    this.toPlay = new ArrayList<>();
    for (List<MusicBlock<INote>> bList : this.model.getBeat(this.playhead.getBeat())) {
      for (MusicBlock<INote> block : bList) {
        if (block.isNoteHead()) {
          this.toPlay.add(new PracticeModeNote(block.getNoteHead()));
        }
      }
    }
    this.havePlayed = new ArrayList<>();
  }

  private void playNotesAtBeat() {
    ArrayList<MusicBlockHead<INote>> atBeat = new ArrayList<>();
    for (List<MusicBlock<INote>> bList : this.model.getBeat(this.playhead.getBeat())) {
      for (MusicBlock<INote> block : bList) {
        if (block.isNoteHead()) {
          atBeat.add(block.getNoteHead());
        }
      }
    }
    this.view.playNotes(atBeat);
  }

  /**
   * Constructor for the music editor, keeps track of a model and view.
   * @param model The model we will be using.
   * @param view The view we will be using.
   */
  public MusicEditor(MusicOperations model, ViewInterface view) {
    this.model = model;
    this.view = view;
    this.playhead = new Playhead(new ReadOnlySong(this.model));
  }

  private void initMaps() {
    this.buttonActionMap = new HashMap<>();
    this.keyTypedMap = new HashMap<>();
    this.keyPressedMap = new HashMap<>();
    this.mouseClickedMap = new HashMap<>();
    this.mousePressedMap = new HashMap<>();
    this.mouseReleasedMap = new HashMap<>();
    this.buttonActionMap.put("New Song", () -> {
      System.out.println("New song");
    });
    this.buttonActionMap.put("Load Song", () -> {
      // TODO: Implement file selection/reading and loading into song
    });
    this.buttonActionMap.put("Save Song", () -> {
      // TODO: Implement getting the state and saving it to a file
    });
    this.buttonActionMap.put("Exit", () -> {
      System.exit(0);
    });
    this.buttonActionMap.put("Add Note", () -> {
      System.out.println("Add Note");
    });
    this.keyPressedMap.put(KeyEvent.VK_P, () -> {
      this.practiceMode = !this.practiceMode;
      if (this.practiceMode) {
        System.out.println("Entering Practice Mode!");
        setToPlay();
      }
      else {
        System.out.println("Exiting Practice Mode!");
      }
    });
    this.keyPressedMap.put(KeyEvent.VK_RIGHT, () -> {
      if (!this.view.isRunning()) {
        try {
          this.playhead.changeBeat(1);
          this.view.update();
          if (this.practiceMode) {
            setToPlay();
          }
        } catch (IllegalArgumentException e) {
          // Do nothing if invalid playhead move
        }
      }
    });
    this.keyPressedMap.put(KeyEvent.VK_LEFT, () -> {
      if (!this.view.isRunning()) {
        try {
          this.playhead.changeBeat(-1);
          this.view.update();
          if (this.practiceMode) {
            setToPlay();
          }
        } catch (IllegalArgumentException e) {
          // Do nothing if invalid playhead move
        }
      }
    });
    this.keyPressedMap.put(KeyEvent.VK_UP, () -> {
      if (!this.view.isRunning()) {
        try {
          this.model.setTempo((int)(this.model.getTempo() * 0.9));
          this.view.update();
        } catch (IllegalArgumentException e) {
          // Do nothing if the attempted change is invalid - should never happen
        }
      }
    });
    this.keyPressedMap.put(KeyEvent.VK_DOWN, () -> {
      if (!this.view.isRunning()) {
        try {
          this.model.setTempo((int)(this.model.getTempo() * 1.1));
          this.view.update();
        } catch (IllegalArgumentException e) {
          // Do nothing if the attempted change is invalid - should never happen
        }
      }
    });
    this.keyPressedMap.put(KeyEvent.VK_SPACE, () -> { //if its running stop, if not start playing.
      if (this.view.isRunning()) {
        this.view.stopPlaying();
        if (this.practiceMode) {
          setToPlay();
        }
      } else {
        this.view.startPlaying();
      }
      this.view.update();
    });
    this.keyPressedMap.put(KeyEvent.VK_HOME, () -> {
      this.view.rewindToStart();
      this.playhead.setBeat(0);
      this.view.update();
      if (this.practiceMode) {
        setToPlay();
      }
    });
    this.keyPressedMap.put(KeyEvent.VK_END, () -> {
      this.view.fastForwardToEnd();
      this.playhead.setBeat(this.model.getNumBeats());
      this.view.update();
      if (this.practiceMode) {
        setToPlay();
      }
    });
    // Changed mouseEvent for holding and releasing the mouse
    this.mousePressedMap.put(MouseEvent.BUTTON1, (e) -> {
      this.newNoteString = this.view.getNote(e.getX(), e.getY());
      this.newNoteStartBeat = this.playhead.getBeat();
      if (!this.newNoteString.equals("") && this.playhead.getBeat() != this.model.getNumBeats()) {
        this.newNoteTimer = new Timer(this.model.getTempo() / 1000, (evt) -> {
          this.playhead.changeBeat(1);
          this.view.updatePlayhead();
        });
        this.newNoteTimer.start();
      }
    });
    this.mouseReleasedMap.put(MouseEvent.BUTTON1, (e) -> {
      this.newNoteTimer.stop();
      int duration = this.playhead.getBeat() - this.newNoteStartBeat;
      if (this.practiceMode) {
        if (this.toPlay.size() == 0) {
          this.playhead.changeBeat(1);
          this.view.updatePlayhead();
          setToPlay();
        }
        else {
          PracticeModeNote tempPMNote = new PracticeModeNote(new Note(this.newNoteString), duration);
          if (this.toPlay.contains(tempPMNote) && !this.havePlayed.contains(tempPMNote)) {
            System.out.println("Correct Note!");
            this.havePlayed.add(tempPMNote);
          }
          this.playhead.setBeat(this.newNoteStartBeat);
          this.view.update();
          if (this.toPlay.size() == this.havePlayed.size()) {
            System.out.println("Finished beat!");
            playNotesAtBeat();
            this.playhead.changeBeat(1);
            setToPlay();
            while (this.toPlay.size() == 0) {
              this.playhead.changeBeat(1);
              setToPlay();
            }
            this.view.updatePlayhead();
            this.view.update();
          }
        }
      }
      else {
        this.model.addNoteFromString(this.newNoteString, this.newNoteStartBeat, duration);
        this.playhead.setBeat(this.newNoteStartBeat);
        this.view.update();
      }
    });/* Removed because of new mouse click events
    this.mouseClickedMap.put(MouseEvent.BUTTON1, (e) -> {
      String noteString = this.view.getNote(e.getX(), e.getY());
      if (!noteString.equals("") && this.playhead.getBeat() != this.model.getNumBeats()) {
        try {
          this.model.addNoteFromString(noteString,
              this.playhead.getBeat(), 1);
          this.playhead.changeBeat(1);
          this.view.update();
        } catch (IllegalArgumentException exc) {
          // You tried to add an illegal note for this song. Don't do that
        }
      }
    });*/
  }


  @Override
  public void initialize() {
    ViewModelOperations<MusicBlock<INote>, INote> roSong = new ReadOnlySong(this.model);
    this.view.setModel(roSong);

    this.view.initializeView(this.playhead, new Note(Note.Pitch.A, 1));

    initMaps();
    this.view.setButtonListener(new ButtonListener(this.buttonActionMap));
    this.view.setKeyboardListener(new KeyboardListener(this.keyTypedMap, this.keyPressedMap));
    this.view.setMouseListener(new MouseListener(this.mouseClickedMap, this.mousePressedMap, this.mouseReleasedMap));
    this.view.showSong();
    this.view.updatePlayhead();
  }

  private class PracticeModeNote {
    private INote note;
    private int  duration;

    public PracticeModeNote(INote note, int duration) {
      this.note = note;
      this.duration = duration;
    }

    public PracticeModeNote(MusicBlockHead<INote> head) {
      this.note = head.getNote().clone();
      this.duration = head.getDuration();
    }

    public boolean equals(Object o) {
      if (!(o instanceof PracticeModeNote)) {
        return false;
      }
      return this.note.equals(((PracticeModeNote) o).note) && this.duration == ((PracticeModeNote) o).duration;
    }
  }

  /**
   * Main method for the music player.
   * @param args arguments.
   * @throws IOException incorrect input/output info.
   * @throws InvalidMidiDataException incorrectly created midi information.
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Not enough arguments");
    }
    MusicOperations<MusicBlock<INote>, INote> model =
        MusicReader.parseFile(new FileReader(args[0]), new Song.Builder());
    ViewInterface view = ViewFactory.build(args[1]);
    EditorCtrl controller = new MusicEditor(model, view);
    controller.initialize();
  }
}
