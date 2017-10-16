package cs3500.music.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by Spencer on 3/19/2017.
 */
public class KeyboardListener implements KeyListener {
  private Map<Integer, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  //private Map<Integer, Runnable> keyReleasedMap; NOT IMPLEMENTED YET

  /**
   * Constructors a keyboardListener.
   * @param keyTypedMap The typed key, i.e. pressed once.
   * @param keyPressedMap The pressed key, i.e. held.
   */
  public KeyboardListener(Map<Integer, Runnable> keyTypedMap,
                          Map<Integer, Runnable> keyPressedMap
                          /*Map<Integer, Runnable> keyReleasedMap*/) {
    this.keyTypedMap = keyTypedMap;
    this.keyPressedMap = keyPressedMap;
    //this.keyReleasedMap = keyReleasedMap; NOT IMPLEMENTED YET
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyCode())) {
      keyTypedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO: Make me do something
  }
}
