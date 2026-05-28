/**
 * Represents a practical utility item used to resolve active classroom challenges.
 * Inherits from Item and maps to active puzzles like code or calculations.
 * Used by the interaction dispatcher to resolve complex conditional room objectives.
 */
public class Tool extends Item {
  private String useTarget;

  /**
   * Constructs a Tool with its interactive assignment parameters.
   *
   * @param name The name of the tool
   * @param description The descriptive text explaining the tool
   * @param useTarget The specific object string this tool targets
   */
  public Tool(String name, String description, String useTarget) {
    super(name, description);
    this.useTarget = useTarget;
  }

  /**
   * Gets the interactive target identifier that this tool processes.
   *
   * @return The interactive string destination key
   */
  public String getUseTarget() {
    return this.useTarget;
  }
}