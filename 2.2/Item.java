/**
 * Represents the base component of all interactive objects in the game world.
 *
 * This class establishes the foundation for the game's item system, providing
 * basic properties like names and descriptions. It serves as the superclass
 * for all specialized item categories defined in the specification.
 */
public class Item {
  private String name;
  private String description;

  /**
   * Constructs an Item with a name and a description.
   *
   * @param name The unique identifier name of the item
   * @param description The descriptive text displayed to the player
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
   * @return The item's descriptive text
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Converts the item into a string representation.
   *
   * @return The name of the item as required by the specification
   */
  @Override
  public String toString() {
    return this.name;
  }
}