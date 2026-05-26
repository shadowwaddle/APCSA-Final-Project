// --- FILE: Item.java ---
/**
 * Base class representing an in-game item.
 *
 * This class holds the common fields for all items, specifically their name and
 * description. It serves as the parent class in the game's item inheritance hierarchy.
 * It is used by the Room and Player classes to store and track available or carried items.
 */
public class Item {
  private String name;
  private String description;

  /**
   * Constructs an Item with a specified name and description.
   *
   * @param name The name of the item.
   * @param description The detailed description of the item.
   */
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Retrieves the name of the item.
   *
   * @return The item's name string.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the description of the item.
   *
   * @return The item's description string.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns a string representation of the item, which is its name.
   *
   * @return The name of the item.
   */
  @Override
  public String toString() {
    return name;
  }
}