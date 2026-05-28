import java.util.ArrayList;

/**
 * Encapsulates a spatial node within the classroom structure.
 *
 * This class tracks physical contents, directional map pathways, and dynamic environmental text.
 * It relies on the Game container's flag evaluations to selectively render appropriate descriptions.
 */
public class Room {
  private String name;
  private String description;
  private ArrayList<Item> items;
  private ArrayList<String> exits;

  /**
   * Constructs an empty Room with spatial parameters.
   *
   * @param name The formal unique name identifier of the classroom room zone
   * @param description The default starting text block for the zone
   */
  public Room(String name, String description) {
    this.name = name;
    this.description = description;
    this.items = new ArrayList<Item>();
    this.exits = new ArrayList<String>();
  }

  /**
   * Retrieves the formal structural name identifier.
   *
   * @return The string representation of the room's title
   */
  public String getName() {
    return this.name;
  }

  /**
   * Evaluates global states dynamically to render text matching the current layout scenario.
   *
   * @param game The main execution module instance hosting state flags
   * @return The correct context-sensitive text block string
   */
  public String getDescription(Game game) {
    // Evaluation rules strictly derived from Room Definitions specification matching state changes
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
        return "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.\nThe algorithm cards are neatly organized. A hidden compartment inside the table has opened.";
      } else {
        return "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.";
      }
    }
    if (this.name.equalsIgnoreCase("Bookshelf")) {
      if (game.hasBookshelfUnlocked()) {
        return "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.\nThe locked shelf is now open.";
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
      if (game.hasApprovalStamp()) {
        return "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.\nA bright green APPROVED stamp now covers the rubric.";
      } else {
        return "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.";
      }
    }
    if (this.name.equalsIgnoreCase("Classroom Door")) {
      if (game.hasEscaped()) {
        return "The classroom door swings open. Freedom at last.";
      } else {
        return "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.";
      }
    }
    return this.description;
  }

  /**
   * Registers a movement vector pathway linking outward from this room.
   *
   * @param direction The target steering cardinal word (e.g. "East")
   * @param destination The matching room name identity string linked to it
   */
  public void addExit(String direction, String destination) {
    this.exits.add(direction + ":" + destination);
  }

  /**
   * Exposes the backing list containing direction-destination exit pairs.
   *
   * @return The underlying ArrayList housing the text connection mappings
   */
  public ArrayList<String> getExits() {
    return this.exits;
  }

  /**
   * Registers a loose physical item asset directly on this floor space.
   *
   * @param item The newly spawned Item entity configuration
   */
  public void addItem(Item item) {
    this.items.add(item);
  }

  /**
   * Extracts a concrete physical item out of the room space by list-index.
   *
   * @param index The coordinate position matching the item slot inside the list
   * @return The specific Item instance being pulled out
   */
  public Item removeItem(int index) {
    // Standard primitive tracking variant utilized for safe compliance
    return this.items.remove(index);
  }

  /**
   * Exposes raw local physical layout content references.
   *
   * @return The baseline array list tracking floor item data
   */
  public ArrayList<Item> getItems() {
    return this.items;
  }
}