package cs3500.music.view;

/**
 * Factory for creating views.
 */
public class ViewFactory {
  /**
   * Creates a view of the given type as a String.
   * @param type Type of view to create
   * @return View of type given by String input
   */
  public static ViewInterface build(String type) {
    switch (type) {
      case "console":
        return new TextViewImpl();
      case "visual":
        return new GuiViewImpl();
      case "midi":
        return new MidiViewImpl();
      case "composite":
        return new CompositeView();
      default:
        throw new IllegalArgumentException("Invalid string to build");
    }
  }
}
