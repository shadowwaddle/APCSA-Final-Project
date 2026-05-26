// --- FILE: Player.java ---
import java.util.ArrayList;

/**
 * Tracks the player's runtime inventory collection and current physical location.
 *
 * This class handles the logistical movement of objects into the student's backpack,
 * enforcing maximum volume thresholds defined by the educational environment rules.
 * Relates directly to Room (location tracking) and Item (inventory contents tracking).
 */
public class Player {
  private ArrayList<Item> inventory;
  private Room currentRoom;
  private int maxInventorySize;

  /**
   * Constructs the Player character initializing their carrying storage limits.
   */
  public Player() {
    this.inventory = new ArrayList<Item>();
    this.maxInventorySize = 6;
  }

  /**
   * Retreives the current room location of the player.
   *
   * @return the Room object where the player is positioned
   */
  public Room getCurrentRoom() {
    return currentRoom;
  }

  /**
   * Relocates the player to a new target classroom location.
   *
   * @param currentRoom the destination Room object to put the player in
   */
  public void setCurrentRoom(Room currentRoom) {
    this.currentRoom = currentRoom;
  }

  /**
   * Retrieves the entire item collection carried inside the inventory.
   *
   * @return the collection of Items
   */
  public ArrayList<Item> getInventory() {
    return inventory;
  }

  /**
   * Adds an item into the player's inventory bag if capacity permits.
   *
   * @param item the Item object requesting storage inside the inventory
   * @return true if successfully stored, false if blocked due to inventory limitations
   */
  public boolean addItem(Item item) {
    // Validate storage cap bounds before committing allocation
    if (inventory.size() >= maxInventorySize) {
      System.out.println("Your backpack is full. Drop something first.");
      return false;
    }
    inventory.add(item);
    return true;
  }

  /**
   * Drops an item out of inventory by clearing it at a specified collection index.
   *
   * @param index the position mapping index inside the inventory
   */
  public void removeItemAt(int index) {
    // Index manipulation protects against underlying automated pointer searches
    inventory.remove(index);
  }

  /**
   * Manually searches the inventory collection for an item matching a specified name.
   *
   * @param itemName the target name string to search for
   * @return the matching Item object, or null if it cannot be found
   */
  public Item findItem(String itemName) {
    // Standard enhanced loop required for safe array searches per spec constraints
    for (Item item : inventory) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }
}