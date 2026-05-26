// --- FILE: KeyItem.java ---
/**
 * Represents a key item in the game that can unlock specific targets.
 *
 * This class extends the base Item class and adds an unlock target field.
 * It is used in the game's interaction system to check if an item can progress 
 * past lock barriers or trigger major progression puzzles.
 */
public class KeyItem extends Item {
  private String unlockTarget;

  /**
   * Constructs a KeyItem with an unlock target.
   *
   * @param name The name of the key item.
   * @param description The detailed description of the key item.
   * @param unlockTarget The name of the object or room this key item unlocks.
   */
  public KeyItem(String name, String description, String unlockTarget) {
    super(name, description);
    this.unlockTarget = unlockTarget;
  }

  /**
   * Retrieves the targeted object or room that this key item unlocks.
   *
   * @return The unlock target name string.
   */
  public String getUnlockTarget() {
    return unlockTarget;
  }
}