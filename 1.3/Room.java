// --- FILE: Room.java ---
import java.util.ArrayList;

/**
 * Represents a single location or room within the classroom map grid.
 *
 * This class encapsulates information about the room's identity, inventory, and exits.
 * It relies on the Game instance to evaluate state flags dynamically and construct
 * adaptive room descriptions matching the game's progression.
 */
public class Room {
  private String name;
  private String baseDescription;
  private ArrayList<Item> items;
  private ArrayList<String> exits;

  /**
   * Constructs a Room instance with an empty set of items and exits.
   *
   * @param name The unique name identifier of the room.
   * @param baseDescription The baseline text description of the room.
   */
  public Room(String name, String baseDescription) {
    this.name = name;
    this.baseDescription = baseDescription;
    this.items = new ArrayList<Item>();
    this.exits = new ArrayList<String>();
  }

  /**
   * Retrieves the room's unique identifying name.
   *
   * @return The room name string.
   */
  public String getName() {
    return name;
  }

  /**
   * Evaluates global game states to return the appropriate dynamic room description.
   *
   * @param game The main Game instance containing the necessary global state booleans.
   * @return The exact description string dictated by the current state of the game.
   */
  public String getDescription(Game game) {
    if (name.equalsIgnoreCase("Whiteboard Wall")) {
      if (game.isBooleanFixed()) {
        return "The whiteboard is covered with a giant unsolved boolean expression written in red marker.\nThe boolean expression has been simplified correctly. Someone wrote 'DeMorgan was here' in the corner.";
      } else {
        return "The whiteboard is covered with a giant unsolved boolean expression written in red marker.";
      }
    } else if (name.equalsIgnoreCase("Computer Station")) {
      if (game.isCodeDebugged()) {
        return "The Java program compiles successfully. The monitor proudly displays '0 ERRORS'.";
      } else {
        return "A Code.org project is open on the monitor. The compiler window is filled with red error messages.";
      }
    } else if (name.equalsIgnoreCase("Back Table")) {
      if (game.isAlgorithmBuilt()) {
        return "The algorithm cards are neatly organized. A hidden compartment inside the table has opened.";
      } else {
        return "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.";
      }
    } else if (name.equalsIgnoreCase("Bookshelf")) {
      if (game.hasBookshelfUnlocked()) {
        return "The locked shelf is now open.";
      } else {
        return "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.";
      }
    } else if (name.equalsIgnoreCase("Printer Corner")) {
      if (game.isSpecPrinted()) {
        return "The printer finally rests quietly after printing a perfectly formatted spec.";
      } else {
        return "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.";
      }
    } else if (name.equalsIgnoreCase("Rubric Board")) {
      if (game.hasApprovalStamp()) {
        return "A bright green APPROVED stamp now covers the rubric.";
      } else {
        return "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.";
      }
    } else if (name.equalsIgnoreCase("Classroom Door")) {
      if (game.hasEscaped()) {
        return "The classroom door swings open. Freedom at last.";
      } else {
        return "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.";
      }
    }
    return baseDescription;
  }

  /**
   * Adds an item to the room's inventory collection.
   *
   * @param item The Item object to be placed into the room.
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * Removes an item from the room's inventory by standard index removal loop.
   *
   * @param item The Item object to find and remove from the room.
   */
  public void removeItem(Item item) {
    // Manual loop required to follow AP CS A constraints and use remove-by-index
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i) == item) {
        items.remove(i);
        return;
      }
    }
  }

  /**
   * Searches the room for an item by its name using case-insensitive comparison.
   *
   * @param itemName The name string of the item to search for.
   * @return The matching Item object, or null if no item matches.
   */
  public Item findItem(String itemName) {
    for (Item item : items) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Appends an exit connection string to this room's available pathways.
   *
   * @param exit The encoded exit string formatted as "Direction:Destination".
   */
  public void addExit(String exit) {
    exits.add(exit);
  }

  /**
   * Retrieves the raw list of exit strings connected to this room.
   *
   * @return An ArrayList containing the direction mappings.
   */
  public ArrayList<String> getExits() {
    return exits;
  }

  /**
   * Retrieves the collection of items currently inside the room.
   *
   * @return An ArrayList containing the items.
   */
  public ArrayList<Item> getItems() {
    return items;
  }
}