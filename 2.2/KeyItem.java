/**
 * Represents a specialized Item used to unlock major structural components.
 *
 * This class extends the base Item class by introducing an unlock target field.
 * It is managed by the Game logic to verify if a milestone hurdle or path block
 * can be resolved by matching targets.
 */
public class KeyItem extends Item {
  private String unlockTarget;

  /**
   * Constructs a KeyItem with specialized unlocking properties.
   *
   * @param name The unique identifier name of the key item
   * @param description The descriptive text displayed to the player
   * @param unlockTarget The name of the room object this item unlocks
   */
  public KeyItem(String name, String description, String unlockTarget) {
    super(name, description);
    this.unlockTarget = unlockTarget;
  }

  /**
   * Retrieves the specific object target that this item can unlock.
   *
   * @return The target object identifier string
   */
  public String getUnlockTarget() {
    return this.unlockTarget;
  }
}