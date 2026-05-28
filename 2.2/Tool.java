/**
 * Represents an interactive item used to execute classroom tasks or puzzles.
 *
 * This class extends the base Item class by tracking a specific use target.
 * It is evaluated within the processCommand infrastructure to trigger state modifications
 * when used on valid targets.
 */
public class Tool extends Item {
  private String useTarget;

  /**
   * Constructs a Tool with interaction properties.
   *
   * @param name The unique identifier name of the tool item
   * @param description The descriptive text displayed to the player
   * @param useTarget The room object target this tool is meant to interact with
   */
  public Tool(String name, String description, String useTarget) {
    super(name, description);
    this.useTarget = useTarget;
  }

  /**
   * Retrieves the room object target this tool functions on.
   *
   * @return The target object identifier string
   */
  public String getUseTarget() {
    return this.useTarget;
  }
}