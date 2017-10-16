package cs3500.music.model;

import cs3500.music.model.musicblocks.MusicBlockHead;
import cs3500.music.model.musicblocks.INote;

import java.util.List;

/**
 * Holds the operations for a view model. A view model is essentially a read-only model that
 * interacts with the view and gives it data it needs to operate while limiting it from the
 * features of a regular music model that it would not need.
 */
public interface ViewModelOperations<K, U> {

  /**
   * Generates and returns the string representation of the current song's state.
   * @return String representation fo this song's state
   * @throws IllegalStateException If the current song is not initialized correctly
   */
  public String getState() throws IllegalStateException;

  /**
   * Returns a list of lists of K representing the given beat index.
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
   * Generates a list of all the noteheads in the song and returns it.
   * @return A list of all noteheads int the song
   * @throws IllegalStateException If the song is not correctly initialized
   */
  public List<MusicBlockHead<INote>> getNoteHeads() throws IllegalStateException;

  /**
   * Gets the tempo that the model is currently set to.
   * @return Current tempo - in MPQ
   *
   */
  public int getTempo();

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


}
