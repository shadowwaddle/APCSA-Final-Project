/**
 * The base class for all items in the game.
 * Holds common characteristics such as a name and a description.
 * This class is extended by specialized subclasses to handle specific mechanics.
 */
public class Item {
  private String name;
  private String description;

  /**
   * Constructs an Item with a name and a description.
   *
   * @param name The display name of the item
   * @param description The detailed text description of the item
   */
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Retrieves the name of the item.
   *
   * @return The item's name string
   */
  public String getName() {
    return this.name;
  }

  /**
   * Retrieves the description of the item.
   *
   * @return The item's description text
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns the item's name to provide clean console outputs.
   *
   * @return The string representation of the item
   */
  @Override
  public String toString() {
    return this.name;
  }
}