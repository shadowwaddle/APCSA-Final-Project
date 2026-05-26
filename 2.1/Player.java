import java.util.ArrayList;

/**
 * Player tracks the participant's tracking metrics, location, and inventory.
 *
 * It models individual state data, manages backpack capacity rules according to 
 * specification definitions, and processes items query actions safely.
 */
public class Player {
  private ArrayList<Item> inventory;
  private Room currentRoom;
  private final int maxInventorySize = 6;

  /**
   * Constructs a Player instance stationed at an initial room point.
   *
   * @param startingRoom the room node where the player begins the game
   */
  public Player(Room startingRoom) {
    this.inventory = new ArrayList<Item>();
    this.currentRoom = startingRoom;
  }

  /**
   * Attempts to add an item to the player inventory while validating capacity constraints.
   *
   * @param item the item target to be put in the inventory
   * @return true if added successfully, false if rejected due to inventory limit
   */
  public boolean addItem(Item item) {
    if (this.inventory.size() >= this.maxInventorySize) {
      System.out.println("Your backpack is full. Drop something first.");
      return false;
    }
    this.inventory.add(item);
    return true;
  }

  /**
   * Removes an item instance explicitly from the collection tracking inventory.
   *
   * @param item the item reference to excise
   */
  public void removeItem(Item item) {
    // Manual loop required — built-in search/remove methods are not allowed
    for (int i = 0; i < this.inventory.size(); i++) {
      if (this.inventory.get(i) == item) {
        this.inventory.remove(i);
        return;
      }
    }
  }

  /**
   * Locates an item within the player's personal collection matching by name.
   *
   * @param name the target text string identifying the item to find
   * @return the matching Item object instance, or null if nothing matches
   */
  public Item findItem(String name) {
    // Manual loop required — built-in search methods are not allowed per AP CS A constraints
    for (Item item : this.inventory) {
      if (item.getName().equalsIgnoreCase(name)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Retrieves the raw list collection containing all held items.
   *
   * @return the player inventory ArrayList
   */
  public ArrayList<Item> getInventory() {
    return this.inventory;
  }

  /**
   * Obtains the current room location reference where the player is positioned.
   *
   * @return the current Room object
   */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /**
   * Updates the current room tracking reference location of the player.
   *
   * @param room the new Room object to move the player to
   */
  public void setCurrentRoom(Room room) {
    this.currentRoom = room;
  }
}
