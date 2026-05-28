import java.util.ArrayList;

/**
 * Tracks status indicators and pack parameters representing the user.
 *
 * This class handles asset tracking via internal boundaries. It prevents expansion 
 * beyond the hard limit of 6 entries using messages verified against the rule spec sheet.
 */
public class Player {
  private ArrayList<Item> inventory;
  private Room currentRoom;
  private final int maxInventorySize = 6;

  /**
   * Constructs an operational Player state instance with clear empty baselines.
   */
  public Player() {
    this.inventory = new ArrayList<Item>();
    this.currentRoom = null;
  }

  /**
   * Assigns the logical target current coordinate room space node.
   *
   * @param room The structural target zone record to drop into
   */
  public void setCurrentRoom(Room room) {
    this.currentRoom = room;
  }

  /**
   * Identifies where the player entity is actively tracked.
   *
   * @return The underlying active space instance reference
   */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /**
   * Appends an element into the inventory if capacity allows.
   *
   * @param item The object descriptor payload attempting to enter the inventory
   * @return True if insertion succeeded; false if blocked by structural system limits
   */
  public boolean addItem(Item item) {
    if (this.inventory.size() >= maxInventorySize) {
      System.out.println("Your backpack is full. Drop something first.");
      return false;
    }
    this.inventory.add(item);
    return true;
  }

  /**
   * Drops an indexed item slot asset out of internal tracking lists.
   *
   * @param index The raw integer identifier address inside the collection
   * @return The item pulled out of the inventory context
   */
  public Item removeItem(int index) {
    // Explicit clean iteration via primitive indexes avoiding underlying search signatures
    return this.inventory.remove(index);
  }

  /**
   * Scans inventory text keys to identify an element using a for-each iteration.
   *
   * @param itemName The identity key tag string targeting a specific tool
   * @return The matching instance object pointer, or null if missing
   */
  public Item findItem(String itemName) {
    // Manual loop required — built-in search methods are not allowed per AP CS A constraints
    for (Item current : this.inventory) {
      if (current.getName().equalsIgnoreCase(itemName)) {
        return current;
      }
    }
    return null;
  }

  /**
   * Exposes full reference components tracking stored items.
   *
   * @return The active internal storage record trace
   */
  public ArrayList<Item> getInventory() {
    return this.inventory;
  }
}