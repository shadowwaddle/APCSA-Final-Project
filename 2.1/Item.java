// --- FILE: Item.java ---
/**
 * Base class representing a collectible or intractable object within the game world.
 *
 * This class serves as the root of the item hierarchy, encapsulating shared fields
 * like name and description that apply to all items in the game. It is designed to 
 * be extended by specialized subclasses to handle specific mechanics.
 * Relates to Room (which contains collections of Items) and Player (which holds 
 * an inventory collection of Items).
 */
public class Item {
  private String name;
  private String description;

  /**
   * Constructs an Item with a specified name and description.
   *
   * @param name the unique or display name of the item
   * @param description the descriptive text shown when examining the item
   */
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Retrieves the name of the item.
   *
   * @return the item name String
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the description of the item.
   *
   * @return the item description String
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns a string representation of the item, which is its name.
   *
   * @return the name of the item
   */
  @Override
  public String toString() {
    return name;
  }
}