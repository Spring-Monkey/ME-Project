package cs3500.music.util;

import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Nick on 4/5/17.
 */
public class MouseListener implements java.awt.event.MouseListener {
  private Map<Integer, Consumer<MouseEvent>> mouseClickedMap;
  private Map<Integer, Consumer<MouseEvent>> mousePressedMap;
  private Map<Integer, Consumer<MouseEvent>> mouseReleasedMap;

  /**
   * Creates a new instance of the object MouseListener, explicitly setting its fields.
   * @param mouseClickedMap A map of the events we want mouse presses to be able to do.
   */
  public MouseListener(Map<Integer, Consumer<MouseEvent>> mouseClickedMap,
                       Map<Integer, Consumer<MouseEvent>> mousePressedMap,
                       Map<Integer, Consumer<MouseEvent>> mouseReleasedMap) {
    this.mouseClickedMap = mouseClickedMap;
    this.mousePressedMap = mousePressedMap;
    this.mouseReleasedMap = mouseReleasedMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (mouseClickedMap.containsKey(e.getButton())) {
      mouseClickedMap.get(e.getButton()).accept(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (mousePressedMap.containsKey(e.getButton())) {
      mousePressedMap.get(e.getButton()).accept(e);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (mouseReleasedMap.containsKey(e.getButton())) {
      mouseReleasedMap.get(e.getButton()).accept(e);
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //Not using this.
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //Not using this.
  }
}
