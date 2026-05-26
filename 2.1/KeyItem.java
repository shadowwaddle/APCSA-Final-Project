/**
 * KeyItem represents a specific subclass of Item used for progression.
 *
 * It extends the base Item class by introducing an unlock target field, mapping 
 * the item explicitly to major structural modifications or room progression puzzles.
 */
public class KeyItem extends Item {
  private String unlockTarget;

  /**
   * Constructs a KeyItem with the specified properties.
   *
   * @param name the distinct identifying name of the key item
   * @param description a detailed description of the key item
   * @param unlockTarget the object name or element this key item unlocks
   */
  public KeyItem(String name, String description, String unlockTarget) {
    super(name, description);
    this.unlockTarget = unlockTarget;
  }

  /**
   * Retrieves the target object this key item is designed to unlock.
   *
   * @return the unlock target identifier string
   */
  public String getUnlockTarget() {
    return this.unlockTarget;
  }
}
