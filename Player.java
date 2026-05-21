import java.util.ArrayList;

/**
 * Tracks individual structural metadata belonging to the interactive player asset.
 *
 * Encapsulates full system control of the private player inventory list up to 
 * explicit maximum structural bounds defined by the game blueprint specifications.
 */
public class Player {
  private ArrayList<Item> inventory;
  private Room currentRoom;
  private final int maxInventorySize = 6;

  /**
   * Initializes the student traveler profile inside an entry-point room.
   *
   * @param startingRoom the room asset where player entities construct initial layout placements
   */
  public Player(Room startingRoom) {
    this.inventory = new ArrayList<Item>();
    this.currentRoom = startingRoom;
  }

  /**
   * Inspects the current map coordinate location node of the entity player.
   *
   * @return the structural Room reference where the player stands
   */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /**
   * Adjusts the location parameter of the user to a target room.
   *
   * @param room the destination room asset
   */
  public void setCurrentRoom(Room room) {
    this.currentRoom = room;
  }

  /**
   * Safely fetches the inventory array resource allocation assigned to the player.
   *
   * @return the raw array holding structural interactive user configurations
   */
  public ArrayList<Item> getInventory() {
    return this.inventory;
  }

  /**
   * Intercepts item pickups to enforce inventory rules.
   *
   * @param item the component targeting assignment to be pocketed
   * @return true if assignment succeeds, false if capacity limit cancels execution
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
   * Safely unloads a inventory piece using sequential index isolation parameters.
   *
   * @param item the precise reference being dumped out of physical player storage arrays
   */
  public void removeItem(Item item) {
    for (int i = 0; i < this.inventory.size(); i++) {
      if (this.inventory.get(i) == item) {
        this.inventory.remove(i);
        return;
      }
    }
  }

  /**
   * Searches the inventory list sequentially using loop logic.
   *
   * @param itemName the text title of the requested item component
   * @return the matching concrete Item resource instance, or null if missing completely
   */
  public Item findItem(String itemName) {
    for (Item item : this.inventory) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }
}