import java.util.ArrayList;

/**
 * Tracks current positional references, storage limits, and held items.
 * Enforces concrete maximum constraints against inventory actions.
 * Leverages explicit item matching routines to confirm ownership.
 */
public class Player {
  private ArrayList<Item> inventory;
  private Room currentRoom;
  private final int maxInventorySize = 6;

  /**
   * Initializes the container vectors for the tracking model.
   */
  public Player() {
    this.inventory = new ArrayList<Item>();
  }

  /**
   * Checks the item volume thresholds before appending objects to the backpack.
   * Emits precise system failures if capacity is exhausted.
   *
   * @param item The target item object to absorb
   * @return true if successful, false if capacity reached
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
   * Extracts items using indexing loops to prevent hidden automatic search errors.
   *
   * @param item The explicit item reference to delete
   */
  public void removeItem(Item item) {
    // Manual index search loop to bypass prohibited collection routines
    for (int i = 0; i < this.inventory.size(); i++) {
      if (this.inventory.get(i) == item) {
        this.inventory.remove(i);
        return;
      }
    }
  }

  /**
   * Scans the complete structural inventory via custom for-each iterations.
   * Validates target entries without risking data mutations.
   *
   * @param itemName The query label to evaluate against
   * @return The matched item instance pointer, or null if unverified
   */
  public Item findItem(String itemName) {
    // For-each verification iteration for clean conditional checking
    for (Item item : this.inventory) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Fetches the internal inventory tracking array directly.
   *
   * @return The underlying data collection layer
   */
  public ArrayList<Item> getInventory() {
    return this.inventory;
  }

  /**
   * Points the structural position coordinate to a new physical room object.
   *
   * @param currentRoom The destination room record to anchor
   */
  public void setCurrentRoom(Room currentRoom) {
    this.currentRoom = currentRoom;
  }

  /**
   * Extracts the current operational location reference.
   *
   * @return The active room record pointer
   */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }
}