// --- FILE: Room.java ---
import java.util.ArrayList;

/**
 * Represents a distinct physical location inside the AP CSA classroom game world.
 *
 * This class tracks room descriptions, contains items residing in the environment, 
 * and stores exits linking to other rooms. It handles conditional state reporting 
 * to provide context-dependent descriptions based on the global story progression.
 * Relates to Item (stores local items) and Game (navigated by movement processing).
 */
public class Room {
  private String name;
  private String baseDescription;
  private String alternateDescription;
  private String dynamicFlagName;
  private ArrayList<Item> items;
  private ArrayList<String> exits;

  /**
   * Constructs a standard Room that contains static or conditionally dynamic descriptions.
   *
   * @param name the name identifier of the room
   * @param baseDescription the default description string when the room flag is false
   * @param alternateDescription the updated description string when the room flag is true
   * @param dynamicFlagName the name of the boolean state tracking flag governing this room
   */
  public Room(String name, String baseDescription, String alternateDescription, String dynamicFlagName) {
    this.name = name;
    this.baseDescription = baseDescription;
    this.alternateDescription = alternateDescription;
    this.dynamicFlagName = dynamicFlagName;
    this.items = new ArrayList<Item>();
    this.exits = new ArrayList<String>();
  }

  /**
   * Retrieves the identifying name of the room.
   *
   * @return the room name string
   */
  public String getName() {
    return name;
  }

  /**
   * Generates the contextual room description based on the state variables passed from Game.
   *
   * @param game the instance of the running game to evaluate current condition flags
   * @return the appropriate room description string reflecting the state changes
   */
  public String getDescription(Game game) {
    // Return appropriate string based on the status of the associated state flag
    if (dynamicFlagName.equals("isBooleanFixed") && game.isBooleanFixed()) {
      return alternateDescription;
    } else if (dynamicFlagName.equals("isCodeDebugged") && game.isCodeDebugged()) {
      return alternateDescription;
    } else if (dynamicFlagName.equals("isAlgorithmBuilt") && game.isAlgorithmBuilt()) {
      return alternateDescription;
    } else if (dynamicFlagName.equals("hasBookshelfUnlocked") && game.hasBookshelfUnlocked()) {
      return alternateDescription;
    } else if (dynamicFlagName.equals("isSpecPrinted") && game.isSpecPrinted()) {
      return alternateDescription;
    } else if (dynamicFlagName.equals("hasApprovalStamp") && game.hasApprovalStamp()) {
      return alternateDescription;
    } else if (dynamicFlagName.equals("hasEscaped") && game.hasEscaped()) {
      return alternateDescription;
    }
    return baseDescription;
  }

  /**
   * Appends an item to the collection of items sitting in this room.
   *
   * @param item the Item object to place in the room
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * Removes an item from the room's inventory collection by its current index.
   *
   * @param index the position index of the item inside the internal list
   */
  public void removeItemAt(int index) {
    // Manual index removal used to fulfill non-delegated array operations securely
    items.remove(index);
  }

  /**
   * Retrieves the raw list of items currently situated inside this room.
   *
   * @return an ArrayList containing the items
   */
  public ArrayList<Item> getItems() {
    return items;
  }

  /**
   * Appends a structural directional exit connector to another room.
   *
   * @param exitMapping the exit details stored as a "Direction:Destination" pair
   */
  public void addExit(String exitMapping) {
    exits.add(exitMapping);
  }

  /**
   * Retrieves all available exit paths configuration strings assigned to this room.
   *
   * @return an ArrayList containing the directional paths mapping
   */
  public ArrayList<String> getExits() {
    return exits;
  }
}