package cs3500.music.model;

import java.util.List;

/**
 * Interface representing a model for a song represented in a music editor.
 * @param <K> Main music blocks used in the song - represent notes, rests, note-heads, etc.
 * @param <U> Note pitch and octave representation used.
 */
public interface MusicOperations<K, U> {
  /**
   * Initializes this song with the given number of beats, and consisting of notes in a range
   * starting at the given startingPitch, and of size numPitches.
   * @param numBeats Number of beats to initialize the song with
   * @param startingPitch Lowest pitch to initialize the song with
   * @param numPitches Number of pitches to have in the song's pitch range
   * @param tempo The tempo of the song
   */
  public void initSong(int numBeats, U startingPitch, int numPitches, int tempo);

  /**
   * Initializes this song with the given number of beats, and consisting of notes in a range
   * starting at the given startingPitch and ending before the given endingPitch (exclusive).
   * @param numBeats Number of beats to initialize the song with
   * @param startingPitch Lowest pitch to initialize the song with
   * @param endingPitch Highest pitch to initialize the song with
   * @param tempo The tempo of the song
   */
  public void initSong(int numBeats, U startingPitch, U endingPitch, int tempo);

  /**
   * Generates and returns the string representation of the current song's state.
   * @return String representation fo this song's state
   * @throws IllegalStateException If the current song is not initialized correctly
   */
  public String getState() throws IllegalStateException;

  /**
   * Sets this song to have the same state as the given state string.
   * @param state State to load into this song
   * @throws IllegalArgumentException If the given state string is invalid.
   */
  /* TODO: Actually implement
  public void loadState(String state) throws IllegalArgumentException; */

  /**
   * Adds every note from the given state to this song, in place with the current song.
   * @param state State to load into this song
   * @throws IllegalArgumentException If the given state is invalid
   * @throws IllegalStateException If this song's state is not initialized
   */
  /* TODO: Actually implement
  public void addStateSimultaneous(String state)
      throws IllegalArgumentException, IllegalStateException; */

  /**
   * Adds every note from the given state to this song, after this current song.
   * @param state State to load into this song
   * @throws IllegalArgumentException If the given state is invalid
   * @throws IllegalStateException If this song's state is not initialized
   */
  /* TODO: Actually implement
  public void addStateConsecutive(String state)
      throws IllegalArgumentException, IllegalStateException; */

  /**
   * Returns a list of list of K representing the given beat index.
   * @param beatIndex Index of the beat to get - 0 indexed
   * @return List of K representing the requested beat
   * @throws IllegalArgumentException If the given beatIndex is not valid (e.g. out of range)
   * @throws IllegalStateException If the song has not been initialized
   */
  public List<List<K>> getBeat(int beatIndex)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the list of K that represents every rest/note/sustain
   * for the given referenceNote of type U, across every beat in the song.
   * @param referenceNote Note to get every beat's instance of
   * @return List of blocks of the given note
   * @throws IllegalArgumentException If the given referenceNote is not in this song
   * @throws IllegalStateException If the song has not been initialized
   */
  public List<K> getAllBlocksOfNote(U referenceNote)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds a noteBlock (K) of the given note and duration at the given beat index.
   * @param beatIndex Starting beat index of the note to add
   * @param note Type of note to add (pitch and octave)
   * @param duration Duration of note to add
   * @throws IllegalArgumentException If the beatIndex, note or duration are invalid
   *         for this song, or if a block already exists at the given location
   * @throws IllegalStateException If the song is not properly initialized
   */
  public void addNote(int beatIndex, U note, int duration)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the noteBlocks representing the heads of the note(s) occurring at the given beatIndex
   * and of the given notes' pitch/octave.
   * @param beatIndex Beat index to get note block from
   * @param note Note to get note block from
   * @return noteBlock representing the head of the requested note
   * @throws IllegalArgumentException If the given beat index or note are invalid for this song
   * @throws IllegalStateException If the song is not initialized correctly
   */
  public List<K> getNoteHeads(int beatIndex, U note)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Changes the duration of the notes at the given beatIndex and of the given
   * notes' pitch/octave by the given delta duration.
   * @param beatIndex Beat index to get note from
   * @param note Pitch/octave to get note from
   * @param deltaDuration Change in duration for the note
   * @throws IllegalArgumentException If the given beatIndex, note, or duration
   *         are invalid, or the requested note does not exists. Will also
   *         be thrown if this change would conflict with another existing note.
   * @throws IllegalStateException If this song is not initialized correctly
   */
  public void changeNoteDuration(int beatIndex, U note, int deltaDuration)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Changes the pitch/octave of the note at the given beatIndex and of the
   * given note's pitch/octave to the new pitch/octave given.
   * @param beatIndex Beat to get note from
   * @param note Starting note
   * @param newNote Ending note
   * @throws IllegalArgumentException If the given beatIndex, note, or newNote
   *         values are invalid, or the requested note does not exits. Will
   *         also be thrown if this change would conflict with another existing note.
   * @throws IllegalStateException If this song is not initialized correctly
   */
  public void changeNotePitch(int beatIndex, U note, U newNote)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Removes the notes at the given beat index and of the given note's pitch/octave.
   * @param beatIndex Beat index to get remove note from.
   * @param note Note type to remove
   * @throws IllegalArgumentException If the given beat index or note are invalid,
   *         or the requested note does not exist
   * @throws IllegalStateException If the song is not initialized properly
   */
  public void removeNotes(int beatIndex, U note)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Sets the number of beats in the song to the given number of beats.
   * NOTE: Any notes that would now end after the end of the song are removed.
   * @param newNumBeats New number of beats to be in the song
   * @throws IllegalStateException If the song is not initialized properly
   */
  public void setNumBeats(int newNumBeats) throws IllegalStateException;

  /**
   * Changes the number of beats in the song by the given delta-beats amount
   * (positive numebers increase the number of beats, negative numbers decrease it).
   * NOTE: Any beats that would now end after the end of the song are removed.
   * @param deltaBeats Change in the number of beats in the song
   * @throws IllegalStateException If the song is not initialized properly
   */
  public void changeNumBeats(int deltaBeats) throws IllegalStateException;

  /**
   * Gets the tempo that the model is currently set to.
   * @return int
   */
  public int getTempo();

  /**
   * Sets the tempo of the song.
   * @throws IllegalArgumentException For tempos that are below or equal to 0.
   */
  public void setTempo(int given) throws IllegalArgumentException;

  /**
   * Returns the lowest pitch in this song.
   * @return Lowest pitch in this song, of type U
   * @throws IllegalStateException If this song is not initialized correctly
   */
  public U getLowestPitch() throws IllegalStateException;

  /**
   * Returns the number of pitches in this song.
   * @return Number of pitches in this song, as an int
   * @throws IllegalStateException If this song is not initialized correctly
   */
  public int getNumPitches() throws IllegalStateException;

  /**
   * Returns the number of beats in this song.
   * @return Number of beats in this song, as an int
   * @throws IllegalStateException If this song is not initialized correctly
   */
  public int getNumBeats() throws IllegalStateException;

  /**
   * Adds a note to the model from a given String.
   * String must be formatted as such: "C#" or "D". No flats.
   * @param noteString The well-formatted string representation of a note.
   * @param beatIndex The beat where the note shall be placed.
   * @param duration The duration of the note.
   * @throws IllegalArgumentException If the note parameters are invalid.
   * @throws IllegalStateException If the song is not initialized properly.
   */
  void addNoteFromString(String noteString, int beatIndex, int duration)
      throws IllegalArgumentException, IllegalStateException;
}
