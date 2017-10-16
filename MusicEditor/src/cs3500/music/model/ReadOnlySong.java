package cs3500.music.model;

import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.MusicBlockHead;
import cs3500.music.model.musicblocks.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for a viewModel.
 */
public class ReadOnlySong implements ViewModelOperations<MusicBlock<INote>, INote> {
  private MusicOperations<MusicBlock<INote>, INote> song;

  public ReadOnlySong(MusicOperations<MusicBlock<INote>, INote> s) {
    song = s;
  }

  @Override
  public String getState() throws IllegalStateException {
    return song.getState();
  }

  @Override
  public List<List<MusicBlock<INote>>> getBeat(int beatIndex)
      throws IllegalArgumentException, IllegalStateException {
    return song.getBeat(beatIndex);
  }

  @Override
  public List<MusicBlock<INote>> getAllBlocksOfNote(INote referenceNote)
      throws IllegalArgumentException, IllegalStateException {
    return song.getAllBlocksOfNote(referenceNote);
  }

  @Override
  public int getTempo() {
    return song.getTempo();
  }

  @Override
  public INote getLowestPitch() throws IllegalStateException {
    return song.getLowestPitch();
  }

  @Override
  public int getNumPitches() throws IllegalStateException {
    return song.getNumPitches();
  }

  @Override
  public int getNumBeats() throws IllegalStateException {
    return song.getNumBeats();
  }

  @Override
  public List<MusicBlockHead<INote>> getNoteHeads() throws IllegalStateException {
    ArrayList<MusicBlockHead<INote>> res = new ArrayList<>();
    for (int i = 0; i < this.song.getNumBeats(); i++) {
      for (List<MusicBlock<INote>> bList : this.song.getBeat(i)) {
        for (MusicBlock<INote> b : bList) {
          if (b.isNoteHead()) {
            res.add(b.clone().getNoteHead());
          }
        }
      }
    }
    return res;
  }
}