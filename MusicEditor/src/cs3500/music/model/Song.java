package cs3500.music.model;

import cs3500.music.model.musicblocks.*;
import cs3500.music.util.CompositionBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Song.
 */
public class Song implements MusicOperations<MusicBlock<INote>, INote> {

  protected ArrayList<Beat> beats;
  protected int numBeats;
  protected INote lowestNote;
  protected INote highestNote;
  protected int numPitches;
  protected int tempo;

  private void verifySongInitArguments(int numBeats, int numPitches, int tempo)
      throws IllegalArgumentException {
    if (numBeats < 0) {
      throw new IllegalArgumentException(
          "Invalid number of beats to initialize song: " + numBeats);
    }
    if (numPitches < 0) {
      throw new IllegalArgumentException(
          "Invalid number of pitches to initialize song: " + numPitches);
    }
    if (tempo <= 0) {
      throw new IllegalArgumentException(
          "Invalid tempo to initialize song: " + tempo);
    }
  }

  private void initializeBeats() {
    this.beats = new ArrayList<>();
    for (int i = 0; i < this.numBeats; i++) {
      this.beats.add(new Beat(this.numPitches, this.lowestNote, i));
    }
  }

  @Override
  public void initSong(int numBeats, INote startingPitch, int numPitches, int tempo) {
    verifySongInitArguments(numBeats, numPitches, tempo);

    this.numBeats = numBeats;
    this.lowestNote = startingPitch;
    this.numPitches = numPitches;
    this.highestNote = startingPitch.getRelativeNote(numPitches);
    this.tempo = tempo;

    initializeBeats();
  }

  @Override
  public void initSong(int numBeats, INote startingPitch, INote endingPitch, int tempo) {
    int numPitches = endingPitch.absolutePitch() - startingPitch.absolutePitch();
    verifySongInitArguments(numBeats, numPitches, tempo);

    this.numBeats = numBeats;
    this.lowestNote = startingPitch;
    this.numPitches = numPitches;
    this.highestNote = endingPitch;
    this.tempo = tempo;

    initializeBeats();
  }

  private void checkStateIsValid() throws IllegalStateException {
    if (this.beats == null) {
      throw new IllegalStateException("Beats array is not correctly initialized");
    }
    if (this.lowestNote == null) {
      throw new IllegalStateException("Lowest note is not correctly initialized");
    }
    if (this.highestNote == null) {
      throw new IllegalStateException("Highest note is not correctly initialized");
    }
    if (this.tempo == 0) {
      throw new IllegalStateException("Tempo is not correctly initialized");
    }
  }

  private void checkBeatArgumentIsValid(int beatIndex) {
    if (beatIndex < 0 || beatIndex >= this.numBeats) {
      throw new IllegalArgumentException("Beat Index is out of range for this song: " + beatIndex);
    }
  }

  private void checkPitchArgumentIsValid(INote refNote) {
    if (refNote.compareTo(this.lowestNote) < 0 || refNote.compareTo(this.highestNote) > 0) {
      throw new IllegalArgumentException("Invalid note in this song: " + refNote.toString());
    }
  }

  private void checkDurationArgumentIsValid(int duration) {
    if (duration < 1) {
      throw new IllegalArgumentException("Invalid duration was given: " + duration);
    }
  }

  private String buildTopRowString() {
    StringBuilder res = new StringBuilder();
    INote current = this.lowestNote;
    for (int i = 0; i < this.numPitches; i++) {
      res.append(current.toPaddedString());
      current = current.nextNoteUp();
    }
    return res.toString();
  }

  @Override
  public String getState() throws IllegalStateException {
    checkStateIsValid();

    int leftColumnPad = Integer.toString(this.numBeats).length();
    StringBuilder state = new StringBuilder();
    for (int i = 0; i < leftColumnPad; i++) {
      state.append(" ");
    }

    state.append(buildTopRowString());
    state.append("\n");

    for (int i = 0; i < numBeats; i++) {
      state.append(String.format("%" + leftColumnPad + "d", i));
      state.append(this.beats.get(i).toString());
      state.append("\n");
    }

    return state.toString();
  }

  /* TODO: Fix this, not yet necessary
  private void addNotesFromStateLine(String stateLine, int beatIndex, int leftColumnPadding) {
    String notesString = stateLine.substring(leftColumnPadding, stateLine.length());
    for (int i = 0; i < notesString.length() / 5; i++) {
      if (notesString.substring(i * 5, (i + 1) * 5).equals("  X  ")) {
        addNoteHelper(beatIndex, this.lowestNote.getRelativeNote(i), 1);
      }
      if (notesString.substring(i * 5, (i + 1) * 5).equals("  |  ")) {
        if (beatIndex == 0) {
          throw new IllegalArgumentException(
              "Invalid state to load: Note Sustain mark on first beat");
        }
        int oldDuration = this.beats.get(beatIndex - 1)
            .getBlocks().get(i).getNoteHead().getDuration();
        changeNoteDuration(beatIndex - 1,
            this.lowestNote.getRelativeNote(i), oldDuration + 1);
      }
    }
  }*/

  /* TODO: Fix this too, not yet necessary
  @Override
  public void loadState(String state) throws IllegalArgumentException {
    String[] splitState = state.split("\n");
    int numBeats = splitState.length - 1;
    if (numBeats == 0) {
      throw new IllegalArgumentException(
          "Invalid state to load - invalid number of beats: " + numBeats);
    }
    int leftColumnPad = Integer.toString(numBeats - 1).length();
    if (splitState[0].length() < leftColumnPad + 5
        || (splitState[0].length() - leftColumnPad) % 5 != 0) {
      throw new IllegalArgumentException(
          "Invalid state to load - invalid first row");
    }
    String lowestNoteString = splitState[0].substring(leftColumnPad, leftColumnPad + 5);
    Note lowestNote = Note.getNoteFromString(lowestNoteString.trim());
    int numNotes = (splitState[0].length() - leftColumnPad) / 5;

    initSong(numBeats, lowestNote, numNotes, 120);
    for (int i = 0; i < numBeats; i++) {
      addNotesFromStateLine(splitState[i + 1], i, leftColumnPad);
    }
  }*/

  /* TODO: Fix this guy too
  private void setupAddState(String state) {
    checkStateIsValid();
    String[] splitState = state.split("\n");
    int numBeats = splitState.length - 1;
    int leftColumnPad = Integer.toString(numBeats - 1).length();
    int numNotes = (splitState[0].length() - leftColumnPad) / 5;
    if (numNotes != this.numPitches) {
      throw new IllegalArgumentException("Invalid state to load - not the same number of notes");
    }
    if ((splitState[0].length() - leftColumnPad) % 5 != 0) {
      throw new IllegalArgumentException("Invalid state to load - invalid first row");
    }
    String lowestNoteString = splitState[0].substring(leftColumnPad, leftColumnPad + 5);
    Note lowestNote = Note.getNoteFromString(lowestNoteString.trim());
    if (!lowestNote.equals(this.lowestNote)) {
      throw new IllegalArgumentException("Invalid state to load - not the same lowest note");
    }
  }*/

  /* TODO: Actually make these work I guess
  @Override
  public void addStateSimultaneous(String state)
      throws IllegalArgumentException, IllegalStateException {
    setupAddState(state);
    String[] splitState = state.split("\n");
    int newNumBeats = splitState.length - 1;
    int leftColumnPadding = Integer.toString(newNumBeats - 1).length();

    setNumBeatsHelper(Math.max(this.numBeats, newNumBeats));
    for (int i = 0; i < newNumBeats; i++) {
      addNotesFromStateLine(splitState[i + 1], i, leftColumnPadding);
    }
  }

  @Override
  public void addStateConsecutive(String state)
      throws IllegalArgumentException, IllegalStateException {
    setupAddState(state);
    String[] splitState = state.split("\n");
    int newNumBeats = splitState.length - 1;
    int leftColumnPadding = Integer.toString(newNumBeats - 1).length();

    int oldNumBeats = this.numBeats;
    setNumBeatsHelper(oldNumBeats + newNumBeats);
    System.out.println("this.numBeats: " + this.numBeats + ", newNumBeats: " + newNumBeats);
    for (int i = oldNumBeats, j = 0; i < this.numBeats; i++, j++) {
      addNotesFromStateLine(splitState[j + 1], i, leftColumnPadding);
    }
  }*/

  @Override
  public List<List<MusicBlock<INote>>> getBeat(int beatIndex)
      throws IllegalArgumentException, IllegalStateException {
    checkStateIsValid();
    checkBeatArgumentIsValid(beatIndex);

    List<List<MusicBlock<INote>>> notes =
        new ArrayList<List<MusicBlock<INote>>>();

    for (List<MusicBlock<INote>> bList : this.beats.get(beatIndex).getBlocks()) {
      ArrayList<MusicBlock<INote>> tempList = new ArrayList<MusicBlock<INote>>();
      for (MusicBlock<INote> b : bList) {
        tempList.add(b.clone());
      }
      notes.add(tempList);
    }

    return notes;
  }

  @Override
  public List<MusicBlock<INote>> getAllBlocksOfNote(INote referenceNote)
      throws IllegalArgumentException, IllegalStateException {
    checkStateIsValid();
    checkPitchArgumentIsValid(referenceNote);

    ArrayList<MusicBlock<INote>> res = new ArrayList<>();

    for (int i = 0; i < this.beats.size(); i++) {
      for (MusicBlock<INote> b : this.beats.get(i).getBlocksOfPitch(referenceNote)) {
        res.add(b.clone());
      }
    }

    return res;
  }

  private final void addNoteHelper(int beatIndex, INote note, int duration) {
    checkStateIsValid();
    checkBeatArgumentIsValid(beatIndex);
    checkDurationArgumentIsValid(duration);
    checkBeatArgumentIsValid(beatIndex + duration - 1);
    checkPitchArgumentIsValid(note);

    NoteHead newHead = new NoteHead(note, duration);
    ArrayList<MusicBlock<INote>> newTail = new ArrayList<>();
    for (int i = 0; i < duration - 1; i++) {
      newTail.add(new NoteSustain(newHead));
    }
    newHead.setSustains(newTail);

    this.beats.get(beatIndex).addBlock(newHead, note);
    for (int i = 0; i < newTail.size(); i++) {
      this.beats.get(beatIndex + 1 + i).addBlock(newTail.get(i), note);
    }
  }

  @Override
  public void addNote(int beatIndex, INote note, int duration)
      throws IllegalArgumentException, IllegalStateException {
    addNoteHelper(beatIndex, note, duration);
  }

  @Override
  public List<MusicBlock<INote>> getNoteHeads(int beatIndex, INote note)
      throws IllegalArgumentException, IllegalStateException {
    checkStateIsValid();
    checkBeatArgumentIsValid(beatIndex);
    checkPitchArgumentIsValid(note);

    ArrayList<MusicBlock<INote>> res = new ArrayList<>();
    for (MusicBlock<INote> b : this.beats.get(beatIndex).getBlocksOfPitch(note)) {
      if (b.isARest()) {
        // Skip rests
        continue;
      }
      res.add(b.getNoteHead().clone());
    }

    return res;
  }

  @Override
  public void changeNoteDuration(int beatIndex, INote note, int deltaDuration)
      throws IllegalArgumentException, IllegalStateException {
    checkStateIsValid();
    checkBeatArgumentIsValid(beatIndex);
    checkPitchArgumentIsValid(note);

    for (MusicBlock<INote> b : this.beats.get(beatIndex).getBlocksOfPitch(note)) {
      if (b.isARest()) {
        // Skip rests
        continue;
      }
      MusicBlockHead<INote> head = b.getNoteHead();
      beatIndex = head.getBeat().getBeatIndex();
      int newDuration = head.getDuration() + deltaDuration;

      removeNoteHelper(head);
      addNoteHelper(beatIndex, note, newDuration);
    }
  }

  @Override
  public void changeNotePitch(int beatIndex, INote note, INote newNote)
      throws IllegalArgumentException, IllegalStateException {
    checkStateIsValid();
    checkBeatArgumentIsValid(beatIndex);
    checkPitchArgumentIsValid(note);

    for (MusicBlock<INote> b : this.beats.get(beatIndex).getBlocksOfPitch(note)) {
      if (b.isARest()) {
        // Skip rests
        continue;
      }
      MusicBlockHead<INote> head = b.getNoteHead();
      beatIndex = head.getBeat().getBeatIndex();
      int duration = head.getDuration();

      removeNoteHelper(head);
      addNoteHelper(beatIndex, newNote, duration);
    }
  }

  final private void removeNoteHelper(MusicBlockHead<INote> head) {
    checkStateIsValid();
    INote note = head.getNote();

    ArrayList<MusicBlock<INote>> tail = new ArrayList<>();
    tail.addAll(head.getTail());

    for (MusicBlock<INote> n : tail) {
      n.getBeat().removeNoteBlock(note);
    }

    head.getBeat().removeNoteBlock(note);
  }

  @Override
  public void removeNotes(int beatIndex, INote note)
      throws IllegalArgumentException, IllegalStateException {
    for (MusicBlock<INote> b : this.beats.get(beatIndex).getBlocksOfPitch(note)) {
      if (b.isARest()) {
        // Skip rests
        continue;
      }
      removeNoteHelper(b.getNoteHead());
    }
  }

  private final void setNumBeatsHelper(int newNumBeats) {
    checkStateIsValid();
    if (newNumBeats < 0) {
      throw new IllegalArgumentException(
          "Invalid number of beats to set song to contain: " + newNumBeats);
    }

    if (newNumBeats == this.numBeats) {
      // Already equal, nothing to change
      return;
    }

    if (newNumBeats > this.numBeats) {
      for (int i = 0; i < newNumBeats - this.numBeats; i++) {
        this.beats.add(new Beat(this.numPitches, this.lowestNote, this.numBeats + i));
      }
    }
    else {
      for (int i = 0; i < this.numPitches; i++) {
        if (this.beats.get(newNumBeats).getBlocks().get(i).size() > 1) {
          for (MusicBlock<INote> b : this.beats.get(newNumBeats).getBlocks().get(i)) {
            if (b.isARest()) {
              // Skip rests
              continue;
            }
            removeNoteHelper(b.getNoteHead());
          }
        }
      }
      for (int i = this.numBeats - 1; i >= newNumBeats; i--) {
        this.beats.remove(i);
      }
    }
    this.numBeats = newNumBeats;
  }

  @Override
  public void setNumBeats(int newNumBeats) throws IllegalStateException {
    setNumBeatsHelper(newNumBeats);
  }

  @Override
  public void changeNumBeats(int deltaBeats) throws IllegalStateException {
    setNumBeatsHelper(this.numBeats + deltaBeats);
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public void setTempo(int given) throws IllegalArgumentException {
    if (given <= 0) {
      throw new IllegalArgumentException("Cannot set Tempo, negative value: " + given);
    }

    this.tempo = given;
  }

  @Override
  public INote getLowestPitch() throws IllegalStateException {
    return this.lowestNote.clone();
  }

  @Override
  public int getNumPitches() throws IllegalStateException {
    return this.numPitches;
  }

  @Override
  public int getNumBeats() throws IllegalStateException {
    return this.numBeats;
  }

  @Override
  public void addNoteFromString(String noteString, int beatIndex, int duration)
      throws IllegalArgumentException, IllegalStateException {
    checkStateIsValid();
    checkBeatArgumentIsValid(beatIndex);
    checkDurationArgumentIsValid(duration);

    INote tempNote = new Note(noteString);
    tempNote.setChannel(1);
    tempNote.setVelocity(127);
    addNoteHelper(beatIndex, tempNote, duration);
  }

  public static final class Builder implements
      CompositionBuilder<MusicOperations<MusicBlock<INote>, INote>> {
    private ArrayList<BuilderNote> notes;
    private ArrayList<INote> pitches;
    private int tempo;
    private int numBeats;
    private MusicOperations<MusicBlock<INote>, INote> song;

    @Override
    public MusicOperations<MusicBlock<INote>, INote> build() {
      this.song = new Song();
      INote startingPitch = Collections.min(this.pitches);
      INote endingPitch = Collections.max(this.pitches).getRelativeNote(1);
      this.song.initSong(this.numBeats, startingPitch, endingPitch, this.tempo);
      for (BuilderNote n : this.notes) {
        try {
          this.song.addNote(n.start, n.note, n.duration);
        } catch (IllegalArgumentException e) {
          // Attempted to add an illegal note (out of note ranges probably)
        }
      }
      return this.song;
    }

    @Override
    public CompositionBuilder<MusicOperations<MusicBlock<INote>, INote>> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<MusicOperations<MusicBlock<INote>, INote>>
        addNote(int start, int end, int instrument, int pitch, int volume) {
      if (this.notes == null) {
        this.notes = new ArrayList<>();
      }
      if (this.pitches == null) {
        this.pitches = new ArrayList<>();
      }
      pitch += 24;
      INote tempNote = new Note(Note.Pitch.C, 4);
      tempNote = tempNote.getRelativeNote(pitch - 60);
      tempNote.setChannel(instrument);
      tempNote.setVelocity(volume);
      this.notes.add(new BuilderNote(tempNote.clone(), start, end - start,
          instrument, volume));
      this.pitches.add(tempNote.clone());
      if (this.numBeats == 0 || end > this.numBeats) {
        this.numBeats = end;
      }
      return this;
    }

    private class BuilderNote {
      INote note;
      int start;
      int duration;
      int instrument;
      int volume;

      public BuilderNote(INote note, int start, int duration,
                         int instrument, int volume) {
        this.note = note;
        this.start = start;
        this.duration = duration;
        this.instrument = instrument;
        this.volume = volume;
      }
    }
  }
}
