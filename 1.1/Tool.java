/**
 * An interactable tool used to resolve puzzles and modify global state flags.
 *
 * This class extends the Item base definition by adding functional targeting metrics, 
 * allowing players to use components on specialized classroom objects.
 */
public class Tool extends Item {
  private String useTarget;

  /**
   * Constructs an interactable Tool instance.
   *
   * @param name the precise textual title of the tool
   * @param description the utility tracking text
   * @param useTarget the targeted object string this tool modifies
   */
  public Tool(String name, String description, String useTarget) {
    super(name, description);
    this.useTarget = useTarget;
  }

  /**
   * Retrieves the target object upon which this tool can be actively utilized.
   *
   * @return the label of the valid target object string
   */
  public String getUseTarget() {
    return this.useTarget;
  }
}