/**
 * A specialized item used for milestone security clearance or unlocking targets.
 *
 * This class inherits from Item and adds specific tracking for an unlock target,
 * modeling critical path game bottlenecks like locked doors or restricted shelves.
 */
public class KeyItem extends Item {
  private String unlockTarget;

  /**
   * Constructs a specific KeyItem instance.
   *
   * @param name the precise textual title of the key item
   * @param description the structural observation text
   * @param unlockTarget the object string this key item acts upon
   */
  public KeyItem(String name, String description, String unlockTarget) {
    super(name, description);
    this.unlockTarget = unlockTarget;
  }

  /**
   * Obtains the target object that this item is bound to unlock.
   *
   * @return the string label of the targeted room mechanism
   */
  public String getUnlockTarget() {
    return this.unlockTarget;
  }
}