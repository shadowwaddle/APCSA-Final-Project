/**
 * Represents specialized puzzle items required for major structural progression.
 * Inherits from Item and adds a target field to evaluate unlocking mechanics.
 * Interacts with specific Room items to advance the story path.
 */
public class KeyItem extends Item {
  private String unlockTarget;

  /**
   * Constructs a KeyItem with specialized lock interaction targets.
   *
   * @param name The name of the key item
   * @param description The descriptive text of the key item
   * @param unlockTarget The specific object identifier this key item unlocks
   */
  public KeyItem(String name, String description, String unlockTarget) {
    super(name, description);
    this.unlockTarget = unlockTarget;
  }

  /**
   * Gets the object ID target that this key item is built to open.
   *
   * @return The string target key matching an object
   */
  public String getUnlockTarget() {
    return this.unlockTarget;
  }
}