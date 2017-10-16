package cs3500.music.view;

import cs3500.music.model.musicblocks.INote;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class KeyboardPanel extends JPanel {
  private static int X_PADDING_LEFT = 100;
  private static final int WHITE_NOTE_WIDTH = 24;
  private static final int BLACK_NOTE_WIDTH = 10;
  private static final int WHITE_NOTE_HEIGHT = 300;
  private static final int BLACK_NOTE_HEIGHT = 120;
  private ArrayList<INote> paintedKeys;
  private ArrayList<Key> keys;
  private INote lowestPossibleNote;

  private class Key extends Rectangle {
    private INote note;

    public Key(INote note) {
      this.note = note;
    }
  }

  /**
   * creates a Keyboard panel, sets all the keys.
   */
  public KeyboardPanel(INote lowestPossibleNote) {
    this.paintedKeys = new ArrayList<>();
    this.keys = new ArrayList<>();
    this.lowestPossibleNote = lowestPossibleNote;

    INote currentNote = lowestPossibleNote.clone();
    for (int i = 0; i < 88; i++) {
      this.keys.add(new Key(currentNote.clone()));
      currentNote = currentNote.nextNoteUp();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Center the keyboard
    this.X_PADDING_LEFT = (this.getWidth() / 2) - (26 * WHITE_NOTE_WIDTH);
    INote currentNote = lowestPossibleNote.clone();
    int numRegular = 0;
    for (int i = 0; i < 88; i++) {
      if (!currentNote.isASharp()) {
        drawRegularNote(currentNote, numRegular, g, i);
        numRegular++;
      }
      currentNote = currentNote.nextNoteUp();
    }
    currentNote = this.lowestPossibleNote.clone();
    numRegular = 0;
    for (int i = 0; i < 88; i++) {
      if (currentNote.isASharp()) {
        drawSharpNote(currentNote, numRegular, g, i);
      }
      else {
        numRegular++;
      }
      currentNote = currentNote.nextNoteUp();
    }
  }

  /**
   * Paints the following list of keys Orange.
   * @param notes List of notes to paint the corresponding keys of
   */
  public void paintKeys(List<INote> notes) {
    this.paintedKeys = new ArrayList<INote>(notes);
    this.revalidate();
    this.repaint();
  }

  private void drawRegularNote(INote n, int noteIndex, Graphics g, int i) {
    int x = X_PADDING_LEFT + noteIndex * WHITE_NOTE_WIDTH;
    int y = 0;
    if (this.paintedKeys.contains(n)) {
      g.setColor(Color.ORANGE);
    }
    else {
      g.setColor(Color.WHITE);
    }
    g.fillRect(x, y, WHITE_NOTE_WIDTH, WHITE_NOTE_HEIGHT);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, WHITE_NOTE_WIDTH, WHITE_NOTE_HEIGHT);
    this.keys.get(i).setBounds(x, y, WHITE_NOTE_WIDTH, WHITE_NOTE_HEIGHT);
  }

  private void drawSharpNote(INote n, int noteIndex, Graphics g, int i) {
    int x = X_PADDING_LEFT + noteIndex * WHITE_NOTE_WIDTH
        - (BLACK_NOTE_WIDTH / 2);
    int y = 0;
    if (this.paintedKeys.contains(n)) {
      g.setColor(Color.ORANGE);
    }
    else {
      g.setColor(Color.BLACK);
    }
    g.fillRect(x, y, BLACK_NOTE_WIDTH, BLACK_NOTE_HEIGHT);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, BLACK_NOTE_WIDTH, BLACK_NOTE_HEIGHT);
    this.keys.get(i).setBounds(x, y, BLACK_NOTE_WIDTH, BLACK_NOTE_HEIGHT);
  }

  /**
   * Method determines the note played by the piano when clicked on.
   * @param posX X position of the mouse.
   * @param posY Y position of the mouse.
   * @return String representation of the note.
   */
  public String getNote(int posX, int posY) {
    posY -= this.getY();
    String noteString = "";

    for (Key k : this.keys) {
      if (k.contains(posX, posY)) {
        noteString = k.note.toString();
      }
    }
    return noteString;

  }
}
