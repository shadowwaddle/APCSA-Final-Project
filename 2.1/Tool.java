/**
 * Tool represents an interactive utility subclass of Item.
 *
 * It models classroom implements that the player can manipulate or apply to specific
 * classroom fixtures in order to solve logical or state-based puzzles.
 */
public class Tool extends Item {
  private String useTarget;

  /**
   * Constructs a Tool with the specified properties.
   *
   * @param name the distinct identifying name of the tool
   * @param description a detailed description of the tool
   * @param useTarget the name of the target object this tool interacts with
   */
  public Tool(String name, String description, String useTarget) {
    super(name, description);
    this.useTarget = useTarget;
  }

  /**
   * Retrieves the targeted room object that this tool can operate on.
   *
   * @return the use target string identifier
   */
  public String getUseTarget() {
    return this.useTarget;
  }
}
