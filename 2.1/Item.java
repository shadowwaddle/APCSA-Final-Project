/**
 * Item represents the base class for all physical entities in the game.
 *
 * This class serves as the core component of the items hierarchy, defining
 * fundamental attributes like name and description that all items share. It 
 * overrides toString() to return the item's name for seamless inventory printing.
 */
public class Item {
  private String name;
  private String description;

  /**
   * Constructs an Item with a specific name and description.
   *
   * @param name the distinct identifying name of the item
   * @param description a detailed description of the item
   */
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Retrieves the name of the item.
   *
   * @return the item name string
   */
  public String getName() {
    return this.name;
  }

  /**
   * Retrieves the description of the item.
   *
   * @return the item description string
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns a string representation of the item, which is its name.
   *
   * @return the item name
   */
  @Override
  public String toString() {
    return this.name;
  }
}
