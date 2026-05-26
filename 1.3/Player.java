// --- FILE: Player.java ---
import java.util.ArrayList;

/**
 * Tracks the player's personal state, backpack inventory, and active location.
 *
 * This class coordinates moving characters between Room nodes and tracks item collection limits.
 * It safely prevents players from carrying items exceeding structural design boundaries.
 */
public class Player {
  private ArrayList<Item> inventory;
  private Room currentRoom;
  private final int maxInventorySize = 6;

  /**
   * Constructs a Player instance initialized with an empty backpack.
   */
  public Player() {
    this.inventory = new ArrayList<Item>();
    this.currentRoom = null;
  }

  /**
   * Retrieves the room object the player is currently occupying.
   *
   * @return The active Room object.
   */
  public Room getCurrentRoom() {
    return currentRoom;
  }

  /**
   * Updates the player's active room location.
   *
   * @param currentRoom The destination Room object to move into.
   */
  public void setCurrentRoom(Room currentRoom) {
    this.currentRoom = currentRoom;
  }

  /**
   * Adds an item to the player's inventory if limits are respected.
   *
   * @param item The Item object intended for pickup.
   * @return true if added successfully; false if rejected due to inventory limits.
   */
  public boolean addItem(Item item) {
    if (inventory.size() >= maxInventorySize) {
      System.out.println("Your backpack is full. Drop something first.");
      return false;
    }
    inventory.add(item);
    return true;
  }

  /**
   * Removes an item from the player's inventory using manual indexing.
   *
   * @param item The Item object to find and discard.
   */
  public void removeItem(Item item) {
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) == item) {
        inventory.remove(i);
        return;
      }
    }
  }

  /**
   * Searches the player's inventory by item name using a for-each loop.
   *
   * @param itemName The string name of the target item.
   * @return The item if discovered; null if missing from the backpack.
   */
  public Item findItem(String itemName) {
    for (Item item : inventory) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Obtains the list of items inside the player's backpack.
   *
   * @return An ArrayList containing the player's current items.
   */
  public ArrayList<Item> getInventory() {
    return inventory;
  }
}