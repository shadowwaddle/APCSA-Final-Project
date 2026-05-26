// --- FILE: KeyItem.java ---
/**
 * Represents an item dedicated to unlocking specific progress hurdles or objects.
 *
 * This subclass extends Item by introducing an unlockTarget field, which designates 
 * the target object or room mechanism this specific item is built to unlock.
 * Used by Game to validate progress puzzles like unlocking the bookshelf or classroom door.
 */
public class KeyItem extends Item {
  private String unlockTarget;

  /**
   * Constructs a KeyItem with specialized unlocking attributes.
   *
   * @param name the name of the key item
   * @param description the description of the key item
   * @param unlockTarget the name of the object this key item unlocks
   */
  public KeyItem(String name, String description, String unlockTarget) {
    super(name, description);
    this.unlockTarget = unlockTarget;
  }

  /**
   * Retrieves the designated object target that this item unlocks.
   *
   * @return the target string name
   */
  public String getUnlockTarget() {
    return unlockTarget;
  }
}