// --- FILE: Tool.java ---
/**
 * Represents a tool item used to interact with room objects.
 *
 * This class extends the base Item class and adds a use target field.
 * It is utilized by the interaction system to map specific actions between
 * the player's tools and interactive objects found within rooms.
 */
public class Tool extends Item {
  private String useTarget;

  /**
   * Constructs a Tool with a targeted use object.
   *
   * @param name The name of the tool.
   * @param description The detailed description of the tool.
   * @param useTarget The name of the object this tool is meant to be used on.
   */
  public Tool(String name, String description, String useTarget) {
    super(name, description);
    this.useTarget = useTarget;
  }

  /**
   * Retrieves the target object name that this tool interacts with.
   *
   * @return The use target name string.
   */
  public String getUseTarget() {
    return useTarget;
  }
}