import java.util.ArrayList;

/**
 * Manages spatial locations, tracking items, paths, and local states.
 * Generates adaptive room summaries relying directly on Game state flags.
 * Communicates structural exits dynamically back to the core routing system.
 */
public class Room {
  private String name;
  private String description;
  private ArrayList<Item> items;
  private ArrayList<String> exits;

  /**
   * Initializing empty item and routing arrays inside the spatial boundary.
   *
   * @param name The identifiable room label
   * @param description The fallback standard room overview
   */
  public Room(String name, String description) {
    this.name = name;
    this.description = description;
    this.items = new ArrayList<Item>();
    this.exits = new ArrayList<String>();
  }

  /**
   * Returns the structural lookup label of the space.
   *
   * @return The text title matching data files
   */
  public String getName() {
    return this.name;
  }

  /**
   * Evaluates global game progression flags to generate dynamic context.
   * Interrogates state flags cleanly to return exact prompt strings.
   *
   * @param game The root game context monitoring boolean structures
   * @return The custom contextual text summary string
   */
  public String getDescription(Game game) {
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
        return "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.\nThe algorithm cards are neatly organized. A hidden compartment inside the table has opened.";
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
    return this.description;
  }

  /**
   * Pushes a freshly discovered or transformed item into local room coordinates.
   *
   * @param item The physical item instance to store
   */
  public void addItem(Item item) {
    this.items.add(item);
  }

  /**
   * Removes a selected item from the room grid via safe manual index scanning.
   * Avoids internal search routines using index numbers.
   *
   * @param item The target reference object to strip
   */
  public void removeItem(Item item) {
    // Manual loop required — built-in search methods are not allowed per AP CS A constraints
    for (int i = 0; i < this.items.size(); i++) {
      if (this.items.get(i) == item) {
        this.items.remove(i);
        return;
      }
    }
  }

  /**
   * Direct access handle tracking items resting in the room.
   *
   * @return The underlying dynamic list configuration
   */
  public ArrayList<Item> getItems() {
    return this.items;
  }

  /**
   * Appends an operational direction mapping string into the exit array.
   *
   * @param exitMapping The formatted string structured as "Direction:Destination"
   */
  public void addExit(String exitMapping) {
    this.exits.add(exitMapping);
  }

  /**
   * Exposes raw exit data for manual processing loops.
   *
   * @return The full list configuration containing vector links
   */
  public ArrayList<String> getExits() {
    return this.exits;
  }
}