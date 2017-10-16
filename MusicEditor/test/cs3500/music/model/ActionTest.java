package cs3500.music.model;

import cs3500.music.util.KeyboardListener;
import org.junit.Before;
import org.junit.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * Designed to test the mock runnables of keyboard events. Extends Component
 * to mock it.
 */
public class ActionTest extends Component {
  private KeyboardListener kb;
  String timesPressed;

  /**
   * initializes the key map for this tes.
   */
  @Before
  public void init() {
    Map<Integer, Runnable> keyTypedMap = new HashMap<>();
    Map<Integer, Runnable> keyPressedMap = new HashMap<>();
    this.timesPressed = "";

    keyPressedMap.put(KeyEvent.VK_RIGHT, () -> {
      this.timesPressed = timesPressed + " ";
    });
    keyPressedMap.put(KeyEvent.VK_LEFT, () -> {
      this.timesPressed = timesPressed + " ";
    });
    keyPressedMap.put(KeyEvent.VK_SPACE, () -> { //if its running stop, if not start playing.
      this.timesPressed = timesPressed + " ";
    });
    keyPressedMap.put(KeyEvent.VK_HOME, () -> {
      this.timesPressed = timesPressed + " ";
    });
    keyPressedMap.put(KeyEvent.VK_END, () -> {
      this.timesPressed = timesPressed + " ";
    });
    kb = new KeyboardListener(keyTypedMap, keyPressedMap);
  }

  @Test
  public void testKeyPressed() {
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_RIGHT));
    assertEquals(1,this.timesPressed.length());
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_LEFT));
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_SPACE));
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_HOME));
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_END));
    assertEquals(5, this.timesPressed.length());
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_C));
    this.kb.keyPressed(new KeyEvent(this, 3, 10, 5, KeyEvent.VK_9));
    assertEquals(5, this.timesPressed.length());
  }
}
