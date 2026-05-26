import java.util.ArrayList;

/**
 * Room models a individual spatial node within the game's map topology.
 *
 * It manages an inventory of physical items, tracks directional exits via formatted 
 * strings, and evaluates global game variables dynamically to output context-aware 
 * room environment descriptions.
 */
public class Room {
  private String name;
  private String description;
  private ArrayList<Item> items;
  private ArrayList<String> exits;

  /**
   * Constructs a Room instance with an identifying name and initial static description.
   *
   * @param name the unique descriptive identifier of the room
   * @param description the primary description text of the environment
   */
  public Room(String name, String description) {
    this.name = name;
    this.description = description;
    this.items = new ArrayList<Item>();
    this.exits = new ArrayList<String>();
  }

  /**
   * Retrieves the dynamic, state-sensitive description text for the room.
   *
   * @param game the global Game instance containing relevant puzzle state flags
   * @return the appropriate context-driven room description string
   */
  public String getDescription(Game game) {
    // Check specific flags mapped to rooms to present dynamic environmental text
    if (this.name.equalsIgnoreCase("Whiteboard Wall")) {
      if (game.isBooleanFixed()) {
        return "The boolean expression has been simplified correctly. Someone wrote 'DeMorgan was here' in the corner.";
      } else {
        return "The whiteboard is covered with a giant unsolved boolean expression written in red marker.";
      }
    } else if (this.name.equalsIgnoreCase("Computer Station")) {
      if (game.isCodeDebugged()) {
        return "The Java program compiles successfully. The monitor proudly displays '0 ERRORS'.";
      } else {
        return "A Code.org project is open on the monitor. The compiler window is filled with red error messages.";
      }
    } else if (this.name.equalsIgnoreCase("Back Table")) {
      if (game.isAlgorithmBuilt()) {
        return "The algorithm cards are neatly organized. A hidden compartment inside the table has opened.";
      } else {
        return "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.";
      }
    } else if (this.name.equalsIgnoreCase("Bookshelf")) {
      if (game.hasBookshelfUnlocked()) {
        return "The locked shelf is now open.";
      } else {
        return "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.";
      }
    } else if (this.name.equalsIgnoreCase("Printer Corner")) {
      if (game.isSpecPrinted()) {
        return "The printer finally rests quietly after printing a perfectly formatted spec.";
      } else {
        return "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.";
      }
    } else if (this.name.equalsIgnoreCase("Rubric Board")) {
      if (game.hasApprovalStamp()) {
        return "A bright green APPROVED stamp now covers the rubric.";
      } else {
        return "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.";
      }
    } else if (this.name.equalsIgnoreCase("Classroom Door")) {
      if (game.hasEscaped()) {
        return "The classroom door swings open. Freedom at last.";
      } else {
        return "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.";
      }
    }
    // Fallback to static description if no dynamic criteria match
    return this.description;
  }

  /**
   * Adds an item to the room's internal inventory.
   *
   * @param item the item object to place in the room
   */
  public void addItem(Item item) {
    this.items.add(item);
  }

  /**
   * Removes an item from the room's inventory collection by matching reference.
   *
   * @param item the target item instance to extract
   */
  public void removeItem(Item item) {
    // Manual loop required — built-in search/remove methods are not allowed
    for (int i = 0; i < this.items.size(); i++) {
      if (this.items.get(i) == item) {
        this.items.remove(i);
        return;
      }
    }
  }

  /**
   * Adds an exit path from this room using a structural formatting string.
   *
   * @param direction the layout cardinal path direction (e.g., "East")
   * @param destination the destination room identity string
   */
  public void addExit(String direction, String destination) {
    this.exits.add(direction + ":" + destination);
  }

  /**
   * Obtains the raw list of item instances currently sitting in the room.
   *
   * @return an ArrayList containing the items present in this room
   */
  public ArrayList<Item> getItems() {
    return this.items;
  }

  /**
   * Obtains the complete list of exits bound to this specific room context.
   *
   * @return an ArrayList containing the formatted exit strings
   */
  public ArrayList<String> getExits() {
    return this.exits;
  }

  /**
   * Obtains the unique identifying name of the room.
   *
   * @return the string representing the room name
   */
  public String getName() {
    return this.name;
  }
}
