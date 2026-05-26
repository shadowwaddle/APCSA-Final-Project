// --- FILE: Tool.java ---
/**
 * Represents an item utilized to execute interactive tasks or resolve classroom challenges.
 *
 * This subclass introduces a useTarget field, marking the specific object with which 
 * this tool must interact to alter the global game state.
 * Used by the interaction puzzle system within Game to process specific player actions.
 */
public class Tool extends Item {
  private String useTarget;

  /**
   * Constructs a Tool item with functional application configurations.
   *
   * @param name the name of the tool
   * @param description the description of the tool
   * @param useTarget the name of the object this tool interacts with
   */
  public Tool(String name, String description, String useTarget) {
    super(name, description);
    this.useTarget = useTarget;
  }

  /**
   * Retrieves the target object name on which this tool should be applied.
   *
   * @return the target string name
   */
  public String getUseTarget() {
    return useTarget;
  }
}