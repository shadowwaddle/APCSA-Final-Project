import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game handles core control flows, rules processing, and the execution loop.
 *
 * This class coordinates the initial setup of rooms, connectivity definitions, 
 * active logic checks, and command execution dispatches, serving as the central 
 * architectural controller.
 */
public class Game {
  private ArrayList<Room> rooms;
  private Player player;
  private int turnCount;
  private boolean isRunning;

  // Global game state flags defined directly from the specification sheets
  private boolean isBooleanFixed;
  private boolean isCodeDebugged;
  private boolean isAlgorithmBuilt;
  private boolean hasBookshelfUnlocked;
  private boolean isSpecPrinted;
  private boolean hasApprovalStamp;
  private boolean isCaught;
  private boolean hasLeftFrontDesk;
  private boolean hasEscaped;

  /**
   * Constructs the Game instance and instantiates global tracking values.
   */
  public Game() {
    this.rooms = new ArrayList<Room>();
    this.turnCount = 0;
    this.isRunning = false;

    // Set defaults matching specification standards
    this.isBooleanFixed = false;
    this.isCodeDebugged = false;
    this.isAlgorithmBuilt = false;
    this.hasBookshelfUnlocked = false;
    this.isSpecPrinted = false;
    this.hasApprovalStamp = false;
    this.isCaught = false;
    this.hasLeftFrontDesk = false;
    this.hasEscaped = false;
  }

  /**
   * Initializes the game world infrastructure, links maps, places assets, and starts loop processing.
   */
  public void start() {
    initializeWorld();
    this.isRunning = true;
    Scanner scanner = new Scanner(System.in);

    // Initial greeting and look trigger to construct state context
    System.out.println("Welcome to Escape From Satija's Class!");
    printRoomDetails();

    // Central execution processing framework loop
    while (this.isRunning) {
      System.out.print("\n> ");
      if (!scanner.hasNextLine()) {
        break;
      }
      String rawInput = scanner.nextLine();
      processCommand(rawInput);

      // Check win/loss state changes at conclusion of interaction pipeline
      if (this.isCaught) {
        System.out.println("You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss.");
        this.isRunning = false;
      } else if (this.hasEscaped) {
        System.out.println("Satija unlocks the classroom door. You survived the Logic Audit.");
        this.isRunning = false;
      }
    }
    scanner.close();
    System.out.println("Game Over. Total Turns: " + this.turnCount);
  }

  /**
   * Seeds the system layout map connections, setups assets and maps tracking indices.
   */
  private void initializeWorld() {
    // Instantiating basic nodes
    Room frontDesk = new Room("Front Desk", "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. The classroom door behind her is locked.");
    Room whiteboardWall = new Room("Whiteboard Wall", "");
    Room computerStation = new Room("Computer Station", "");
    Room backTable = new Room("Back Table", "");
    Room bookshelf = new Room("Bookshelf", "");
    Room printerCorner = new Room("Printer Corner", "");
    Room rubricBoard = new Room("Rubric Board", "");
    Room classroomDoor = new Room("Classroom Door", "");

    // Setting architectural pathways manually derived from room schemas
    frontDesk.addExit("East", "Whiteboard Wall");
    frontDesk.addExit("South", "Classroom Door");

    whiteboardWall.addExit("West", "Front Desk");
    whiteboardWall.addExit("East", "Computer Station");
    whiteboardWall.addExit("South", "Rubric Board");

    computerStation.addExit("West", "Whiteboard Wall");
    computerStation.addExit("South", "Back Table");

    backTable.addExit("North", "Computer Station");
    backTable.addExit("West", "Rubric Board");
    backTable.addExit("East", "Bookshelf");

    bookshelf.addExit("North", "Back Table");
    bookshelf.addExit("West", "Printer Corner");

    printerCorner.addExit("East", "Bookshelf");
    printerCorner.addExit("North", "Rubric Board");

    rubricBoard.addExit("West", "Classroom Door");
    rubricBoard.addExit("North", "Whiteboard Wall");
    rubricBoard.addExit("East", "Back Table");
    rubricBoard.addExit("South", "Printer Corner");

    classroomDoor.addExit("North", "Front Desk");
    classroomDoor.addExit("East", "Rubric Board");

    // Adding items to their specific initial starting vectors per specification item maps
    frontDesk.addItem(new Tool("Truth Table", "A foundational grid outlining logical operations.", "whiteboard"));
    computerStation.addItem(new Tool("Rubber Duck", "An invaluable tool for conversational debugging.", "computer"));
    backTable.addItem(new Tool("Index Cards", "Scattered elements tracking distinct algorithmic execution steps.", "table"));

    // Register all elements inside tracking layout structures
    this.rooms.add(frontDesk);
    this.rooms.add(whiteboardWall);
    this.rooms.add(computerStation);
    this.rooms.add(backTable);
    this.rooms.add(bookshelf);
    this.rooms.add(printerCorner);
    this.rooms.add(rubricBoard);
    this.rooms.add(classroomDoor);

    // Bind player character reference to root entry point
    this.player = new Player(frontDesk);
  }

  /**
   * Interprets text strings provided by user input, resolving specific targeting keywords.
   *
   * @param rawInput the plain text line supplied directly by user input via system loops
   */
  private void processCommand(String rawInput) {
    String cleanInput = rawInput.trim();
    if (cleanInput.isEmpty()) {
      return;
    }

    String lowerInput = cleanInput.toLowerCase();

    // Mapping core functional pathways
    if (lowerInput.startsWith("go ")) {
      String direction = cleanInput.substring(3).trim();
      handleMovement(direction);
    } else if (lowerInput.equalsIgnoreCase("look")) {
      printRoomDetails();
      incrementTurn();
    } else if (lowerInput.equalsIgnoreCase("inventory")) {
      printInventory();
      incrementTurn();
    } else if (lowerInput.startsWith("take ")) {
      String itemName = cleanInput.substring(5).trim();
      handleTake(itemName);
    } else if (lowerInput.startsWith("use ") && lowerInput.contains(" on ")) {
      handleInteraction(cleanInput);
    } else if (lowerInput.equalsIgnoreCase("quit")) {
      this.isRunning = false;
      System.out.println("Quitting game execution loop.");
    } else {
      System.out.println("Unknown action phrasing. Try commands matching look, go, take, use, or inventory.");
    }
  }

  /**
   * Processes player movement inputs along relative cardinal points.
   *
   * @param direction the raw targeted movement parameter input
   */
  private void handleMovement(String direction) {
    Room current = this.player.getCurrentRoom();
    String destinationName = null;

    // Parse existing room paths matching exact parameters
    for (String exitStr : current.getExits()) {
      int splitIdx = exitStr.indexOf(":");
      String dirKey = exitStr.substring(0, splitIdx);
      String destVal = exitStr.substring(splitIdx + 1);

      if (dirKey.equalsIgnoreCase(direction)) {
        destinationName = destVal;
        break;
      }
    }

    if (destinationName == null) {
      System.out.println("You cannot go that way from here.");
      return;
    }

    Room nextRoom = findRoom(destinationName);
    if (nextRoom == null) {
      System.out.println("Error: Room structure link broken inside world configuration.");
      return;
    }

    // Process structural trapping logic hooks associated with leaving the Front Desk area
    if (current.getName().equalsIgnoreCase("Front Desk")) {
      this.hasLeftFrontDesk = true;
    }

    // Set updated location vectors inside entity trackers
    this.player.setCurrentRoom(nextRoom);
    System.out.println("You move to the " + nextRoom.getName() + ".");
    
    // Evaluate if the room re-entry trap triggers
    if (nextRoom.getName().equalsIgnoreCase("Front Desk") && this.hasLeftFrontDesk && !this.hasEscaped) {
      this.isCaught = true;
      return;
    }

    printRoomDetails();
    incrementTurn();
  }

  /**
   * Processes resource acquisition attempts targeting items in the immediate room container.
   *
   * @param itemName the text string identifying the item to take
   */
  private void handleTake(String itemName) {
    Room currentRoom = this.player.getCurrentRoom();
    Item targetItem = null;

    // Search room context contents manually per constraints requirements
    for (Item item : currentRoom.getItems()) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        targetItem = item;
        break;
      }
    }

    if (targetItem == null) {
      System.out.println("There is no item named '" + itemName + "' present in this room.");
      return;
    }

    // Attempt implementation insertion to target inventory tracking limits
    if (this.player.addItem(targetItem)) {
      currentRoom.removeItem(targetItem);
      System.out.println("You pick up the " + targetItem.getName() + ".");
      incrementTurn();
    }
  }

  /**
   * Deconstructs multi-argument structural phrases to handle specialized puzzle interactions.
   *
   * @param cleanInput the raw text input known to contain specific interaction verbs
   */
  private void handleInteraction(String cleanInput) {
    String lower = cleanInput.toLowerCase();
    int onIdx = lower.indexOf(" on ");
    
    // Deconstruct fields safely bypassing initial invocation terms
    String itemName = cleanInput.substring(4, onIdx).trim();
    String targetName = cleanInput.substring(onIdx + 4).trim();

    Item inventoryItem = this.player.findItem(itemName);
    Room currentRoom = this.player.getCurrentRoom();

    if (inventoryItem == null) {
      System.out.println("You do not have '" + itemName + "' in your backpack.");
      return;
    }

    // --- PUZZLE 1: Boolean Puzzle ---
    if (inventoryItem.getName().equalsIgnoreCase("Truth Table") && targetName.equalsIgnoreCase("Whiteboard")) {
      if (!currentRoom.getName().equalsIgnoreCase("Whiteboard Wall")) {
        System.out.println("You aren't in the correct room to use that item on the whiteboard.");
        return;
      }
      if (this.isBooleanFixed) {
        System.out.println("The boolean expression has already been solved.");
        return;
      }
      this.isBooleanFixed = true;
      System.out.println("You simplify the boolean expression correctly. Satija silently nods in approval.");
      incrementTurn();
      return;
    }

    // --- PUZZLE 2: Debug Puzzle ---
    if (inventoryItem.getName().equalsIgnoreCase("Rubber Duck") && targetName.equalsIgnoreCase("Computer")) {
      if (!currentRoom.getName().equalsIgnoreCase("Computer Station")) {
        System.out.println("You aren't in the correct room to use that item on the computer.");
        return;
      }
      if (!this.isBooleanFixed) {
        System.out.println("The screen errors continue flashing. Perhaps another task must be completed first.");
        return;
      }
      if (this.isCodeDebugged) {
        System.out.println("The program already compiles flawlessly.");
        return;
      }
      this.isCodeDebugged = true;
      System.out.println("You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious.");
      incrementTurn();
      return;
    }

    // --- PUZZLE 3: Algorithm Puzzle ---
    if (inventoryItem.getName().equalsIgnoreCase("Index Cards") && targetName.equalsIgnoreCase("Table")) {
      if (!currentRoom.getName().equalsIgnoreCase("Back Table")) {
        System.out.println("You aren't in the correct room to use that item on the table.");
        return;
      }
      if (!this.isCodeDebugged) {
        System.out.println("The components of the workspace do not align yet. Finish debugging first.");
        return;
      }
      if (this.isAlgorithmBuilt) {
        System.out.println("The compartment has already popped open.");
        return;
      }
      this.isAlgorithmBuilt = true;
      currentRoom.addItem(new KeyItem("Algorithm Card", "A stiff structural item storing an optimization blueprint.", "Bookshelf"));
      System.out.println("You arrange the algorithm steps into the correct order. A hidden compartment opens.");
      incrementTurn();
      return;
    }

    // --- PUZZLE 4: Unlock Bookshelf ---
    if (inventoryItem.getName().equalsIgnoreCase("Algorithm Card") && targetName.equalsIgnoreCase("Bookshelf")) {
      if (!currentRoom.getName().equalsIgnoreCase("Bookshelf")) {
        System.out.println("You aren't in the correct room to use that item on the bookshelf.");
        return;
      }
      if (!this.isAlgorithmBuilt) {
        System.out.println("The prerequisite requirements are unfulfilled.");
        return;
      }
      if (this.hasBookshelfUnlocked) {
        System.out.println("The bookshelf sections are already open.");
        return;
      }
      this.hasBookshelfUnlocked = true;
      currentRoom.addItem(new Document("Java Manual", "A comprehensive repository containing official syntax rules.", "Sacred reference materials."));
      this.player.removeItem(inventoryItem);
      System.out.println("The locked shelf clicks open, revealing the sacred Java Manual.");
      incrementTurn();
      return;
    }

    // --- PUZZLE 5: Print Spec ---
    if (inventoryItem.getName().equalsIgnoreCase("Java Manual") && targetName.equalsIgnoreCase("Printer")) {
      if (!currentRoom.getName().equalsIgnoreCase("Printer Corner")) {
        System.out.println("You aren't in the correct room to use that item on the printer.");
        return;
      }
      if (!this.hasBookshelfUnlocked) {
        System.out.println("The components remain systemically locked down.");
        return;
      }
      if (this.isSpecPrinted) {
        System.out.println("The spec output has already finished rendering.");
        return;
      }
      this.isSpecPrinted = true;
      currentRoom.addItem(new Document("Printed Spec", "A physical sheet detailing required functional rules specifications.", "The finalized project blueprint layout."));
      this.player.removeItem(inventoryItem);
      System.out.println("The printer groans dramatically before printing your completed spec.");
      incrementTurn();
      return;
    }

    // --- PUZZLE 6: Approval Puzzle ---
    if (inventoryItem.getName().equalsIgnoreCase("Printed Spec") && targetName.equalsIgnoreCase("Rubric")) {
      if (!currentRoom.getName().equalsIgnoreCase("Rubric Board")) {
        System.out.println("You aren't in the correct room to use that item on the rubric.");
        return;
      }
      if (!this.isSpecPrinted) {
        System.out.println("You do not possess the required data outputs yet.");
        return;
      }
      if (this.hasApprovalStamp) {
        System.out.println("The structural document validation is complete.");
        return;
      }
      this.hasApprovalStamp = true;
      currentRoom.addItem(new KeyItem("Final Approval Stamp", "A heavy mechanical device featuring bright green validation ink.", "Door"));
      this.player.removeItem(inventoryItem);
      System.out.println("A giant green APPROVED stamp slams onto your spec.");
      incrementTurn();
      return;
    }

    // --- PUZZLE 7: Escape Sequence ---
    if (inventoryItem.getName().equalsIgnoreCase("Final Approval Stamp") && targetName.equalsIgnoreCase("Door")) {
      if (!currentRoom.getName().equalsIgnoreCase("Classroom Door")) {
        System.out.println("You aren't near the exit door structure.");
        return;
      }
      if (!this.hasApprovalStamp) {
        System.out.println("The lock framework rejects incomplete authentication frameworks.");
        return;
      }
      if (this.hasEscaped) {
        return;
      }
      this.hasEscaped = true;
      this.player.removeItem(inventoryItem);
      incrementTurn();
      return;
    }

    System.out.println("Nothing meaningful happens when trying to use that item on that target object.");
  }

  /**
   * Locates a room node from internal indexing records using simple string lookup filters.
   *
   * @param roomName the textual name designation of the room being evaluated
   * @return the matched Room instance found, or null if unresolvable
   */
  public Room findRoom(String roomName) {
    // Manual loop required — built-in lookup tools are not permitted per structural constraints
    for (Room r : this.rooms) {
      if (r.getName().equalsIgnoreCase(roomName)) {
        return r;
      }
    }
    return null;
  }

  /**
   * Increments internal progression metrics and handles automated turn pipeline hooks.
   */
  private void incrementTurn() {
    this.turnCount++;
    processTurn();
  }

  /**
   * Tracks chronological logic execution and cyclic turn hooks.
   */
  private void processTurn() {
    // Left purposefully empty as the current specification demands no timed/cyclic resource depletion mechanics
  }

  /**
   * Displays structural information detailing the contents and descriptions of the current space.
   */
  private void printRoomDetails() {
    Room current = this.player.getCurrentRoom();
    System.out.println("\n=== " + current.getName() + " ===");
    System.out.println(current.getDescription(this));

    // List out environmental items manually if present inside the room container boundaries
    ArrayList<Item> roomItems = current.getItems();
    if (!roomItems.isEmpty()) {
      System.out.print("Items here: ");
      for (int i = 0; i < roomItems.size(); i++) {
        System.out.print(roomItems.get(i).getName());
        if (i < roomItems.size() - 1) {
          System.out.print(", ");
        }
      }
      System.out.println();
    }

    // List explicit directional path mappings to give environmental navigation cues
    System.out.print("Available exits: ");
    ArrayList<String> exits = current.getExits();
    for (int i = 0; i < exits.size(); i++) {
      int splitIdx = exits.get(i).indexOf(":");
      System.out.print(exits.get(i).substring(0, splitIdx));
      if (i < exits.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();
  }

  /**
   * Aggregates and displays current items held by the player backpack container.
   */
  private void printInventory() {
    ArrayList<Item> inv = this.player.getInventory();
    if (inv.isEmpty()) {
      System.out.println("Your backpack is currently empty.");
    } else {
      System.out.print("Your Inventory: ");
      for (int i = 0; i < inv.size(); i++) {
        System.out.print(inv.get(i).getName());
        if (i < inv.size() - 1) {
          System.out.print(", ");
        }
      }
      System.out.println();
    }
  }

  /**
   * Public accessor obtaining state information regarding the status of boolean simplifications.
   *
   * @return true if the boolean puzzle has been solved
   */
  public boolean isBooleanFixed() {
    return this.isBooleanFixed;
  }

  /**
   * Public accessor obtaining state information tracking program compiler errors.
   *
   * @return true if the debugging code puzzle has been solved
   */
  public boolean isCodeDebugged() {
    return this.isCodeDebugged;
  }

  /**
   * Public accessor obtaining state information tracking organizational array layouts.
   *
   * @return true if index cards sorting steps have been structured
   */
  public boolean isAlgorithmBuilt() {
    return this.isAlgorithmBuilt;
  }

  /**
   * Public accessor checking structural visibility parameters mapping bookshelf locking systems.
   *
   * @return true if the bookshelf lock mechanism was solved
   */
  public boolean hasBookshelfUnlocked() {
    return this.hasBookshelfUnlocked;
  }

  /**
   * Public accessor tracking specialized machine hardware output components.
   *
   * @return true if the specifications sheets printed cleanly
   */
  public boolean isSpecPrinted() {
    return this.isSpecPrinted;
  }

  /**
   * Public accessor documenting tracking status validations for verification markers.
   *
   * @return true if validation stamping logic is complete
   */
  public boolean hasApprovalStamp() {
    return this.hasApprovalStamp;
  }

  /**
   * Public accessor evaluating if environmental triggers completed core exit operations safely.
   *
   * @return true if the win scenario triggers successfully
   */
  public boolean hasEscaped() {
    return this.hasEscaped;
  }
}
