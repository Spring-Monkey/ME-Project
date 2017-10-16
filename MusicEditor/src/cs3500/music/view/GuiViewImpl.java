package cs3500.music.view;

import cs3500.music.model.ViewModelOperations;
import cs3500.music.model.musicblocks.INote;
import cs3500.music.model.musicblocks.MusicBlock;
import cs3500.music.model.musicblocks.MusicBlockHead;


import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewImpl extends javax.swing.JFrame implements ViewInterface {
  private ViewModelOperations<MusicBlock<INote>, INote> model;
  private ArrayList<AbstractButton> allButtons;

  private KeyboardPanel kbPanel;
  private NoteScrollPane nsPane;
  private NotePanelWithPitches nPanel;

  private IPlayhead playhead;

  private void initializeMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JMenu songMenu = new JMenu("Song");
    JMenu noteMenu = new JMenu("Note");
    JMenuItem newSongItem = fileMenu.add("New Song");
    JMenuItem loadSongItem = fileMenu.add("Load Song");
    JMenuItem saveSongItem = fileMenu.add("Save Song");
    fileMenu.addSeparator();
    JMenuItem exitItem = fileMenu.add("Exit");
    JMenuItem importSyncItem = editMenu.add("Import Song Synchronously");
    JMenuItem importConsecItem = editMenu.add("Import Song Consecutively");
    JMenuItem changeRangeItem = songMenu.add("Change Song Range");
    JMenuItem changeLengthItem = songMenu.add("Change Song Length");
    JMenuItem addNoteItem = noteMenu.add("Add Note");
    this.allButtons.addAll(Arrays.asList(newSongItem, loadSongItem,
        saveSongItem, exitItem, importSyncItem, importConsecItem,
        changeRangeItem, changeLengthItem, addNoteItem));
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(songMenu);
    menuBar.add(noteMenu);

  }

  private void initializeLayout(INote lowestPossibleNote) {
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    this.nPanel = new NotePanelWithPitches(this.model, this.playhead);
    this.nsPane = new NoteScrollPane(this.nPanel);
    this.nsPane.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
    this.nsPane.setAutoscrolls(true);
    this.kbPanel = new KeyboardPanel(lowestPossibleNote);
    this.add(this.nsPane);
    this.add(this.kbPanel);
  }

  @Override
  public void updatePlayhead() {
    if (this.model != null) {
      int newBeat = this.playhead.getBeat();
      if (newBeat == this.model.getNumBeats()) {
        this.kbPanel.paintKeys(new ArrayList<INote>());
        this.nsPane.repaint(
            new Rectangle((int)this.playhead.getRectangle().getX() - 200,
                0, 400, this.nsPane.getHeight()));
        this.nPanel.revalidate();
      }
      else {
        ArrayList<INote> toPaint = new ArrayList<>();
        for (List<MusicBlock<INote>> bList : this.model.getBeat(newBeat)) {
          for (MusicBlock<INote> b : bList) {
            if (!b.isARest()) {
              toPaint.add(b.getNoteHead().getNote().clone());
            }
          }
        }
        this.kbPanel.paintKeys(toPaint);
        this.nPanel.scrollRectToVisible(
            new Rectangle((int)this.playhead.getRectangle().getX() - 200,
                0, 400, 1));
        this.nsPane.repaint(
            new Rectangle((int)this.playhead.getRectangle().getX() - 200,
                0, 400, this.nsPane.getHeight()));
        this.nPanel.revalidate();
      }
    }
  }

  @Override
  public void startPlaying() {
    // Does nothing, all handled by composite view
  }

  @Override
  public void stopPlaying() {
    // Does nothing, all handled by composite view
  }

  @Override
  public void rewindToStart() {
    this.nsPane.getHorizontalScrollBar().setValue(0);
  }

  @Override
  public void fastForwardToEnd() {
    this.nsPane.getHorizontalScrollBar().setValue(
        this.nsPane.getHorizontalScrollBar().getMaximum());
  }

  /**
   * Creates new GuiView without a song.
   */
  public GuiViewImpl() {
    this.allButtons = new ArrayList<>();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
  }

  @Override
  public void setButtonListener(ActionListener listener) {
    for (AbstractButton b : this.allButtons) {
      b.setActionCommand(b.getText());
      b.addActionListener(listener);
    }
  }

  @Override
  public void setKeyboardListener(KeyListener listener) {
    // Dynamic Dispatch woohoo
    this.addKeyListener(listener);
  }

  @Override
  public void setMouseListener(MouseListener listener) {
    this.addMouseListener(listener);
  }

  @Override
  public void initializeView(IPlayhead playHead, INote lowestPossibleNote) {
    this.playhead = playHead;
    initializeMenuBar();
    initializeLayout(lowestPossibleNote);
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1500, 750);
  }

  @Override
  public void update() {
    this.revalidate();
    this.repaint();
  }

  @Override
  public void setModel(ViewModelOperations model) {
    this.model = model;
  }

  @Override
  public void showSong() {
    try {
      this.model.getState();
      this.nPanel.initPanel();
    } catch (IllegalStateException e) {
      // No song initialized, don't show anything
    }
  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public String getNote(int xPos, int yPos) throws IllegalArgumentException {
    return this.kbPanel.getNote(xPos, yPos);
  }

  @Override
  public void playNotes(List<MusicBlockHead<INote>> notes) {
    // Does nothing
  }
}
