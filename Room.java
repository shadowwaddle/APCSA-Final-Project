import java.util.ArrayList;

/**
 * Models an isolated spatial region within the AP CSA classroom game world.
 *
 * Rooms track internal item layout lists, exit transitions, and coordinate with 
 * the Game class to evaluate state flags for dynamic description outputs.
 */
public class Room {
  private String name;
  private String description;
  private ArrayList<Item> items;
  private ArrayList<String> exits;

  /**
   * Creates a room configuration node with distinct inventory tracking.
   *
   * @param name the unique identity string of the room
   * @param description the primary base description text
   */
  public Room(String name, String description) {
    this.name = name;
    this.description = description;
    this.items = new ArrayList<Item>();
    this.exits = new ArrayList<String>();
  }

  /**
   * Returns the identification name of this room.
   *
   * @return the unique string label
   */
  public String getName() {
    return this.name;
  }

  /**
   * Appends an active exit mapping to this room's navigational structure.
   *
   * @param exit string pairing encoded exactly as "Direction:Destination"
   */
  public void addExit(String exit) {
    this.exits.add(exit);
  }

  /**
   * Appends a real physical item into this room's floor directory.
   *
   * @param item the concrete object reference being deposited
   */
  public void addItem(Item item) {
    this.items.add(item);
  }

  /**
   * Removes an item from the floor directory utilizing index iteration safety.
   *
   * @param item the concrete object instance being wiped from the floor array
   */
  public void removeItem(Item item) {
    for (int i = 0; i < this.items.size(); i++) {
      if (this.items.get(i) == item) {
        this.items.remove(i);
        return;
      }
    }
  }

  /**
   * Fetches the underlying inventory list allocated to this specific room space.
   *
   * @return the raw ArrayList containing localized floor items
   */
  public ArrayList<Item> getItems() {
    return this.items;
  }

  /**
   * Fetches the direction exit strings bound to this specific room layout.
   *
   * @return the internal exits array strings
   */
  public ArrayList<String> getExits() {
    return this.exits;
  }

  /**
   * Resolves dynamic descriptions by reading conditional global state tracking variables.
   *
   * @param game the global engine instance containing state machine tracking metrics
   * @return the accurate text context reflecting ongoing structural shifts
   */
  public String getDescription(Game game) {
    if (this.name.equalsIgnoreCase("Whiteboard Wall")) {
      if (game.isBooleanFixed()) {
        return "The boolean expression has been simplified correctly. Someone wrote 'DeMorgan was here' in the corner.";
      } else {
        return "The whiteboard is covered with a giant unsolved boolean expression written in red marker.";
      }
    }
    if (this.name.equalsIgnoreCase("Computer Station")) {
      if (game.isCodeDebugged()) {
        return "The Java program compiles successfully. The monitor proudly displays '0 ERRORS'.";
      } else {
        return "A Code.org project is open on the monitor. The compiler window is filled with red error messages.";
      }
    }
    if (this.name.equalsIgnoreCase("Back Table")) {
      if (game.isAlgorithmBuilt()) {
        return "The algorithm cards are neatly organized. A hidden compartment inside the table has opened.";
      } else {
        return "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.";
      }
    }
    if (this.name.equalsIgnoreCase("Bookshelf")) {
      if (game.isHasBookshelfUnlocked()) {
        return "The locked shelf is now open.";
      } else {
        return "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.";
      }
    }
    if (this.name.equalsIgnoreCase("Printer Corner")) {
      if (game.isSpecPrinted()) {
        return "The printer finally rests quietly after printing a perfectly formatted spec.";
      } else {
        return "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.";
      }
    }
    if (this.name.equalsIgnoreCase("Rubric Board")) {
      if (game.isHasApprovalStamp()) {
        return "A bright green APPROVED stamp now covers the rubric.";
      } else {
        return "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.";
      }
    }
    if (this.name.equalsIgnoreCase("Classroom Door")) {
      if (game.isHasEscaped()) {
        return "The classroom door swings open. Freedom at last.";
      } else {
        return "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.";
      }
    }
    return this.description;
  }
}