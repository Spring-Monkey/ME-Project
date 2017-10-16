package cs3500.music.view;

import javax.swing.JScrollPane;

/**
 * ScrollPane containing the NotePane, for designating viewports,
 * managing scrolling, etc.
 */
public class NoteScrollPane extends JScrollPane {
  NotePanelWithPitches notePanelWithPitches;

  public NoteScrollPane(NotePanelWithPitches notePanelWithPitches) {
    super(notePanelWithPitches);
    this.notePanelWithPitches = notePanelWithPitches;
  }
}
