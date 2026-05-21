/**
 * Base representation of an in-game object.
 *
 * This class forms the root of the items inheritance hierarchy. It encapsulates 
 * common state variables such as name and description that all physical artifacts 
 * share within the game world.
 */
public class Item {
  private String name;
  private String description;

  /**
   * Initializes a new base item with core properties.
   *
   * @param name the precise textual title of the item
   * @param description the contextual observation text for the item
   */
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Retrieves the current name of the item.
   *
   * @return the string tracking this item's identity
   */
  public String getName() {
    return this.name;
  }

  /**
   * Retrieves the descriptive text of the item.
   *
   * @return the detailed description string
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns a clean string translation of the item.
   *
   * @return the item's display name
   */
  @Override
  public String toString() {
    return this.name;
  }
}