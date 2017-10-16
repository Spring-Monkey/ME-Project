package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.MusicBlockHead;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;


/**
 * Component inside NoteScrollPanes, contains notes.
 */
public class NotePanelWithPitches extends JPanel {
  public static final int BLOCK_DIMENSION = 20;
  public static final int Y_PADDING = 30;
  public static final int X_PADDING = 40;

  ViewModelOperations<MusicBlock<INote>, INote> model;
  private INote lowestNote;
  private int numPitches;
  private int numBeats;
  IPlayhead playhead;
  private boolean isInitialized;

  /**
   * Empty constructor for pre-song-initialization use.
   */
  public NotePanelWithPitches(ViewModelOperations<MusicBlock<INote>, INote> model,
                              IPlayhead playhead) {
    // Used to initialize empty starting panel
    this.model = model;
    this.isInitialized = false;
    this.playhead = playhead;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isInitialized) {
      this.lowestNote = this.model.getLowestPitch();
      this.numPitches = this.model.getNumPitches();
      this.numBeats = this.model.getNumBeats();
      drawPitchColumn(g);
      drawLines(g);
      drawNotes(g);
      drawPlayhead(g);
    }
  }

  /**
   * initializes the panel.
   */
  public void initPanel() {
    int numBeats;

    this.lowestNote = this.model.getLowestPitch();
    this.numPitches = this.model.getNumPitches();
    numBeats = this.model.getNumBeats();
    this.isInitialized = true;
    this.setPreferredSize(
        new Dimension(X_PADDING + numBeats * BLOCK_DIMENSION + 1,
        Y_PADDING + this.numPitches * BLOCK_DIMENSION + 1));
    this.setBackground(Color.WHITE);
    this.isInitialized = true;
    this.revalidate();
    this.repaint();
  }

  private void drawPitchColumn(Graphics g) {
    INote currentNote = this.lowestNote.clone();
    for (int i = 0; i < numPitches; i++) {
      g.drawString(currentNote.toPaddedString(), 0,
          Y_PADDING + BLOCK_DIMENSION * (i + 1) - BLOCK_DIMENSION / 2);
      currentNote = currentNote.nextNoteUp();
    }
  }

  private void drawLines(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawLine(X_PADDING, Y_PADDING, X_PADDING + this.numBeats * BLOCK_DIMENSION, Y_PADDING);
    for (int i = 1; i <= this.numPitches; i++) {
      g.drawLine(X_PADDING, Y_PADDING + i * BLOCK_DIMENSION,
          X_PADDING + this.numBeats * BLOCK_DIMENSION, Y_PADDING + i * BLOCK_DIMENSION);
    }
    g.drawLine(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING + this.numPitches * BLOCK_DIMENSION);
    for (int i = 0; i <= this.numBeats; i++) {
      if (i % 4 == 0) {
        g.drawLine(X_PADDING + i * BLOCK_DIMENSION, Y_PADDING,
            X_PADDING + i * BLOCK_DIMENSION, Y_PADDING + this.numPitches * BLOCK_DIMENSION);
        g.drawString(Integer.toString(i),X_PADDING + i * BLOCK_DIMENSION, 20);
      }
    }
  }

  private void drawNotes(Graphics g) {
    for (MusicBlockHead<INote> head : this.model.getNoteHeads()) {
      g.setColor(Color.BLACK);
      g.fillRect(X_PADDING + head.getBeat().getBeatIndex() * BLOCK_DIMENSION,
          Y_PADDING + (head.getNote().absolutePitch() -
              this.model.getLowestPitch().absolutePitch()) * BLOCK_DIMENSION + 1,
          BLOCK_DIMENSION, BLOCK_DIMENSION - 1);
      if (head.getDuration() > 1) {
        g.setColor(Color.green);
        g.fillRect(X_PADDING + (head.getBeat().getBeatIndex() + 1) * BLOCK_DIMENSION,
            Y_PADDING + (head.getNote().absolutePitch() -
                this.model.getLowestPitch().absolutePitch()) * BLOCK_DIMENSION + 1,
            BLOCK_DIMENSION * (head.getDuration() - 1), BLOCK_DIMENSION - 1);
      }
    }
  }

  private void drawPlayhead(Graphics g) {
    g.setColor(Color.RED);
    Rectangle playheadRect = this.playhead.getRectangle();
    g.fillRect(X_PADDING + playheadRect.x, Y_PADDING + playheadRect.y,
        playheadRect.width, playheadRect.height);
  }
}
