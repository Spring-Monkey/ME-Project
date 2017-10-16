package cs3500.music.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * General button-listening class. Given to the GUI view to give buttons actions.
 */
public class ButtonListener implements ActionListener {
  Map<String, Runnable> buttonClickedActions;

  /**
   * Constructor with buttonClickedActions map as input.
   * @param map Map between button click strings and Runnables that
   *            defines the actions of this ButtonListener
   */
  public ButtonListener(Map<String, Runnable> map) {
    this.buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Button pressed");
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}
