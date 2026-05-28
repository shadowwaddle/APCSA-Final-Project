import java.util.ArrayList;
import java.util.Scanner;

/**
 * Orchestrates loop processing, global event variables, parsing operations, and milestone validation.
 *
 * This driver engine sets up all room states, maps spatial layouts, handles 
 * step progression logic, and handles interactions cleanly.
 */
public class Game {
  private ArrayList<Room> rooms;
  private Player player;
  private boolean isRunning;
  private int turnCount;

  // Global game state trackers explicitly defined by section I of user specification
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
   * Initializes state flags, maps collection nodes, and builds world structures.
   */
  public Game() {
    this.rooms = new ArrayList<Room>();
    this.player = new Player();
    this.isRunning = false;
    this.turnCount = 0;

    // Default initialization setup
    this.isBooleanFixed = false;
    this.isCodeDebugged = false;
    this.isAlgorithmBuilt = false;
    this.hasBookshelfUnlocked = false;
    this.isSpecPrinted = false;
    this.hasApprovalStamp = false;
    this.isCaught = false;
    this.hasLeftFrontDesk = false;
    this.hasEscaped = false;

    this.initializeWorld();
  }

  /**
   * Instantiates all game elements and maps layout exits manually.
   */
  private void initializeWorld() {
    // Instantiate structural rooms
    Room frontDesk = new Room("Front Desk", "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. Before you leave, she hands you a laminated Truth Table sheet and tells you not to lose it. The classroom door behind her is locked.");
    Room whiteboardWall = new Room("Whiteboard Wall", "The whiteboard is covered with a giant unsolved boolean expression written in red marker.");
    Room computerStation = new Room("Computer Station", "A Code.org project is open on the monitor. The compiler window is filled with red error messages.");
    Room backTable = new Room("Back Table", "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.");
    Room bookshelf = new Room("Bookshelf", "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.");
    Room printerCorner = new Room("Printer Corner", "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.");
    Room rubricBoard = new Room("Rubric Board", "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.");
    Room classroomDoor = new Room("Classroom Door", "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.");

    // Map geometric links using explicit directional data from Section III
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

    // Populate global collection
    this.rooms.add(frontDesk);
    this.rooms.add(whiteboardWall);
    this.rooms.add(computerStation);
    this.rooms.add(backTable);
    this.rooms.add(bookshelf);
    this.rooms.add(printerCorner);
    this.rooms.add(rubricBoard);
    this.rooms.add(classroomDoor);

    // Initial item placement configurations matching Section VI specifications
    computerStation.addItem(new Tool("Rubber Duck", "A small yellow tool used to handle programmatic errors.", "computer"));
    backTable.addItem(new Tool("Index Cards", "Cards containing chaotic mixed up algorithms steps.", "table"));

    // Inject starter pack equipment directly into student backpack container
    this.player.addItem(new Tool("Truth Table", "A hand laminated truth configuration matrix document.", "whiteboard"));

    // Anchor starting coordinates
    this.player.setCurrentRoom(frontDesk);
  }

  /**
   * Starts the user interaction pipeline execution loop.
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    this.isRunning = true;

    System.out.println("=== ESCAPE FROM SATIJA'S CLASS ===");
    System.out.println(this.player.getCurrentRoom().getDescription(this));

    while (this.isRunning) {
      System.out.print("\n> ");
      String rawInput = scanner.nextLine();
      this.processCommand(rawInput);
      this.checkEndConditions();
    }
    scanner.close();
  }

  /**
   * Processes individual user terminal statements into logical action pathways.
   *
   * @param rawInput Raw sequence alphanumeric line string entered by student
   */
  private void processCommand(String rawInput) {
    String cleanInput = rawInput.trim();
    if (cleanInput.isEmpty()) {
      return;
    }

    String lowerInput = cleanInput.toLowerCase();

    // Command Option: Quit Game
    if (lowerInput.equals("quit")) {
      System.out.println("Exiting logic audit. Goodbye.");
      this.isRunning = false;
      return;
    }

    // Command Option: Look Room Context
    if (lowerInput.equals("look")) {
      System.out.println(this.player.getCurrentRoom().getDescription(this));
      ArrayList<Item> itemsInRoom = this.player.getCurrentRoom().getItems();
      if (itemsInRoom.isEmpty()) {
        System.out.println("There are no loose items lying around here.");
      } else {
        System.out.print("Items visible around you: ");
        for (int i = 0; i < itemsInRoom.size(); i++) {
          System.out.print("[" + itemsInRoom.get(i).getName() + "]");
          if (i < itemsInRoom.size() - 1) {
            System.out.print(", ");
          }
        }
        System.out.println();
      }
      return;
    }

    // Command Option: Print Player Inventory
    if (lowerInput.equals("inventory")) {
      ArrayList<Item> backpack = this.player.getInventory();
      if (backpack.isEmpty()) {
        System.out.println("Your backpack is completely empty.");
      } else {
        System.out.println("Your current backpack assets:");
        for (Item item : backpack) {
          System.out.println("- " + item.getName() + ": " + item.getDescription());
        }
      }
      return;
    }

    // Command Option: Spatial Movement navigation handling 
    if (lowerInput.startsWith("go ")) {
      String directionTarget = cleanInput.substring(3).trim();
      this.handleMovement(directionTarget);
      return;
    }

    // Command Option: Pick up loose spatial asset targets
    if (lowerInput.startsWith("take ")) {
      String itemTarget = cleanInput.substring(5).trim();
      this.handleTakeItem(itemTarget);
      return;
    }

    // Command Option: Custom operational item interaction logic
    if (lowerInput.startsWith("use ")) {
      this.handleUseInteraction(cleanInput);
      return;
    }

    System.out.println("Unknown action phrasing string template. Verify usage guidelines.");
  }

  /**
   * Evaluates pathways out of the zone node and executes movement actions.
   *
   * @param direction Target directional input vector word
   */
  private void handleMovement(String direction) {
    Room current = this.player.getCurrentRoom();
    ArrayList<String> directionalExits = current.getExits();
    String targetedDestinationRoomName = null;

    for (String exitPair : directionalExits) {
      int colonIdx = exitPair.indexOf(":");
      String dirPart = exitPair.substring(0, colonIdx);
      String roomPart = exitPair.substring(colonIdx + 1);

      if (dirPart.equalsIgnoreCase(direction)) {
        targetedDestinationRoomName = roomPart;
        break;
      }
    }

    if (targetedDestinationRoomName == null) {
      System.out.println("You cannot walk in that direction from here.");
      return;
    }

    Room nextRoom = this.findRoom(targetedDestinationRoomName);
    if (nextRoom == null) {
      System.out.println("Spatial tracking link anomaly detected targeting: " + targetedDestinationRoomName);
      return;
    }

    // Evaluate room entry trap state restrictions matching Section VII guidelines
    if (current.getName().equalsIgnoreCase("Front Desk")) {
      this.hasLeftFrontDesk = true;
    }

    // Transition coordinate fields
    this.player.setCurrentRoom(nextRoom);
    System.out.println("You move toward the " + nextRoom.getName() + ".");
    System.out.println(nextRoom.getDescription(this));

    // Handle immediate spatial trigger traps post execution sequence shifts
    if (nextRoom.getName().equalsIgnoreCase("Front Desk") && this.hasLeftFrontDesk) {
      this.isCaught = true;
    }

    this.incrementTurnCounter();
  }

  /**
   * Moves a local room asset into the player's pack space.
   *
   * @param itemName Name key matching the room item to pick up
   */
  private void handleTakeItem(String itemName) {
    Room current = this.player.getCurrentRoom();
    ArrayList<Item> groundAssets = current.getItems();
    int locatedIndex = -1;

    for (int i = 0; i < groundAssets.size(); i++) {
      if (groundAssets.get(i).getName().equalsIgnoreCase(itemName)) {
        locatedIndex = i;
        break;
      }
    }

    if (locatedIndex == -1) {
      System.out.println("No item matching that description exists in this room.");
      return;
    }

    Item targetedAsset = groundAssets.get(locatedIndex);
    
    // Validate inventory limits before extraction
    if (this.player.addItem(targetedAsset)) {
      current.removeItem(locatedIndex);
      System.out.println("You placed the [" + targetedAsset.getName() + "] inside your backpack.");
      this.incrementTurnCounter();
    }
  }

  /**
   * Parses and executes semantic multi-word action commands like "use [X] on [Y]".
   *
   * @param fullCommand Context statement string containing operands
   */
  private void handleUseInteraction(String fullCommand) {
    String lower = fullCommand.toLowerCase();
    int onIndex = lower.indexOf(" on ");

    if (onIndex == -1 || !lower.startsWith("use ")) {
      System.out.println("Invalid format. Use the phrasing: use [item] on [object]");
      return;
    }

    String itemLabel = fullCommand.substring(4, onIndex).trim();
    String objectLabel = fullCommand.substring(onIndex + 4).trim();

    Item matchingPackItem = this.player.findItem(itemLabel);
    if (matchingPackItem == null) {
      System.out.println("You do not possess a \"" + itemLabel + "\" inside your pack inventory.");
      return;
    }

    Room roomContext = this.player.getCurrentRoom();

    // Puzzle Interaction 1: Boolean Puzzle handling
    if (itemLabel.equalsIgnoreCase("Truth Table") && objectLabel.equalsIgnoreCase("Whiteboard")) {
      if (!roomContext.getName().equalsIgnoreCase("Whiteboard Wall")) {
        System.out.println("You aren't near the proper target canvas whiteboard area.");
        return;
      }
      if (this.isBooleanFixed) {
        System.out.println("The giant boolean grid puzzle has already been completed here.");
        return;
      }
      this.isBooleanFixed = true;
      System.out.println("You simplify the boolean expression correctly. Satija silently nods in approval.");
      this.incrementTurnCounter();
      return;
    }

    // Puzzle Interaction 2: Debug Puzzle handling
    if (itemLabel.equalsIgnoreCase("Rubber Duck") && objectLabel.equalsIgnoreCase("Computer")) {
      if (!roomContext.getName().equalsIgnoreCase("Computer Station")) {
        System.out.println("There is no compiler terminal interface monitoring setup within this room context.");
        return;
      }
      if (!this.isBooleanFixed) {
        System.out.println("You try to debug, but unresolved logic blockers across the room stall your thoughts.");
        return;
      }
      if (this.isCodeDebugged) {
        System.out.println("The compiler engine monitoring layout shows absolute zero errors remaining.");
        return;
      }
      this.isCodeDebugged = true;
      System.out.println("You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious.");
      this.incrementTurnCounter();
      return;
    }

    // Puzzle Interaction 3: Algorithm Puzzle handling
    if (itemLabel.equalsIgnoreCase("Index Cards") && objectLabel.equalsIgnoreCase("Table")) {
      if (!roomContext.getName().equalsIgnoreCase("Back Table")) {
        System.out.println("The workspace horizontal layouts inside this area cannot support structural layout arrays.");
        return;
      }
      if (!this.isCodeDebugged) {
        System.out.println("You try shuffling cards, but uncompiled code bugs prevent algorithm structure completion.");
        return;
      }
      if (this.isAlgorithmBuilt) {
        System.out.println("The compartment array door is already lying completely open on this table surface.");
        return;
      }
      this.isAlgorithmBuilt = true;
      roomContext.addItem(new KeyItem("Algorithm Card", "A thick cardboard structural key mapping access configurations.", "Bookshelf"));
      System.out.println("You arrange the algorithm steps into the correct order. A hidden compartment opens.");
      this.incrementTurnCounter();
      return;
    }

    // Puzzle Interaction 4: Unlock Bookshelf puzzle path handling
    if (itemLabel.equalsIgnoreCase("Algorithm Card") && objectLabel.equalsIgnoreCase("Bookshelf")) {
      if (!roomContext.getName().equalsIgnoreCase("Bookshelf")) {
        System.out.println("No high density wooden storage furniture layout is observable inside this coordinate area.");
        return;
      }
      if (!this.isAlgorithmBuilt) {
        System.out.println("The key card properties remain structurally useless at this developmental stage.");
        return;
      }
      if (this.hasBookshelfUnlocked) {
        System.out.println("The specific panel door structures have already locked open safely.");
        return;
      }
      this.hasBookshelfUnlocked = true;
      roomContext.addItem(new Document("Java Manual", "The documentation tome guiding AP CSA engineering rules.", "Standard Reference Material"));
      System.out.println("The locked shelf clicks open, revealing the sacred Java Manual.");
      
      // Consume KeyItem structural resource via iterative removal loops
      this.removeInventoryItem(itemLabel);
      this.incrementTurnCounter();
      return;
    }

    // Puzzle Interaction 5: Print Spec generation logic tracking
    if (itemLabel.equalsIgnoreCase("Java Manual") && objectLabel.equalsIgnoreCase("Printer")) {
      if (!roomContext.getName().equalsIgnoreCase("Printer Corner")) {
        System.out.println("No printing mechanics equipment exists here to interface document elements.");
        return;
      }
      if (!this.hasBookshelfUnlocked) {
        System.out.println("Prerequisite authorization blocks deny connection access into the printer node.");
        return;
      }
      if (this.isSpecPrinted) {
        System.out.println("The machine rests cleanly after delivering output copies.");
        return;
      }
      this.isSpecPrinted = true;
      roomContext.addItem(new Document("Printed Spec", "A beautifully rendered layout defining standard system specifications.", "Complete Architectural Rules"));
      System.out.println("The printer groans dramatically before printing your completed spec.");
      
      // Consume Document resource via manual iterative loops
      this.removeInventoryItem(itemLabel);
      this.incrementTurnCounter();
      return;
    }

    // Puzzle Interaction 6: Approval validation puzzle logic sequence
    if (itemLabel.equalsIgnoreCase("Printed Spec") && objectLabel.equalsIgnoreCase("Rubric")) {
      if (!roomContext.getName().equalsIgnoreCase("Rubric Board")) {
        System.out.println("You see no standard validation grading rubric board frame in this room context.");
        return;
      }
      if (!this.isSpecPrinted) {
        System.out.println("You cannot validate empty requirements specs on this testing board matrix.");
        return;
      }
      if (this.hasApprovalStamp) {
        System.out.println("The validation rubric layout sheet already displays structural green approvals.");
        return;
      }
      this.hasApprovalStamp = true;
      roomContext.addItem(new KeyItem("Final Approval Stamp", "An official weighted verification mechanism tool.", "Door"));
      System.out.println("A giant green APPROVED stamp slams onto your spec.");
      
      // Consume used Document template item
      this.removeInventoryItem(itemLabel);
      this.incrementTurnCounter();
      return;
    }

    // Puzzle Interaction 7: Final Escape step verification mechanism
    if (itemLabel.equalsIgnoreCase("Final Approval Stamp") && objectLabel.equalsIgnoreCase("Door")) {
      if (!roomContext.getName().equalsIgnoreCase("Classroom Door")) {
        System.out.println("No egress door framework structures are observable inside this coordinate location.");
        return;
      }
      if (!this.hasApprovalStamp) {
        System.out.println("The scanner blinks amber rejecting the interface stamp tools.");
        return;
      }
      if (this.hasEscaped) {
        return;
      }
      this.hasEscaped = true;
      this.removeInventoryItem(itemLabel);
      this.incrementTurnCounter();
      return;
    }

    System.out.println("Using the [" + matchingPackItem.getName() + "] on the \"" + objectLabel + "\" yields absolutely zero systemic response.");
  }

  /**
   * Extricates an item from the inventory list manually via its target string identity.
   *
   * @param name Key string matching the targeted inventory asset name to destroy
   */
  private void removeInventoryItem(String name) {
    ArrayList<Item> inventoryList = this.player.getInventory();
    for (int i = 0; i < inventoryList.size(); i++) {
      if (inventoryList.get(i).getName().equalsIgnoreCase(name)) {
        this.player.removeItem(i);
        break;
      }
    }
  }

  /**
   * Tracks elapsed steps and loops turn-based world hooks.
   */
  private void incrementTurnCounter() {
    this.turnCount++;
    this.processTurn();
  }

  /**
   * Triggers scheduled environmental modifications and updates NPC tasks.
   */
  private void processTurn() {
    // Left purposefully open for modular updates as explicitly structural rule baseline setup dictates
  }

  /**
   * Scans global layout node collections for matching room coordinates.
   *
   * @param roomName Title signature text of the targeted room node
   * @return The functional matched Room object, or null if unlinked
   */
  private Room findRoom(String roomName) {
    // Manual loop required — built-in search methods are not allowed per AP CS A constraints
    for (Room room : this.rooms) {
      if (room.getName().equalsIgnoreCase(roomName)) {
        return room;
      }
    }
    return null;
  }

  /**
   * Evaluates completion matrices and outputs win/loss screens text matching specifications.
   */
  private void checkEndConditions() {
    if (this.isCaught) {
      System.out.println("You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss.");
      this.isRunning = false;
    } else if (this.hasEscaped) {
      System.out.println("Satija unlocks the classroom door. You survived the Logic Audit.");
      this.isRunning = false;
    }
  }

  /**
   * Accesses the operational state status flag tracking Boolean puzzle setups.
   *
   * @return True if boolean parameters are fixed
   */
  public boolean isBooleanFixed() {
    return this.isBooleanFixed;
  }

  /**
   * Accesses active tracking indicators measuring computer compiler optimization status.
   *
   * @return True if code error traces are fully corrected
   */
  public boolean isCodeDebugged() {
    return this.isCodeDebugged;
  }

  /**
   * Accesses tracking indices defining algorithmic table sort puzzles status.
   *
   * @return True if step index card assets are sequenced correctly
   */
  public boolean isAlgorithmBuilt() {
    return this.isAlgorithmBuilt;
  }

  /**
   * Accesses locked parameter states governing structural bookshelf access paths.
   *
   * @return True if access blocks have dropped open
   */
  public boolean hasBookshelfUnlocked() {
    return this.hasBookshelfUnlocked;
  }

  /**
   * Accesses state properties identifying print job output statuses.
   *
   * @return True if printer engine cycle completes successfully
   */
  public boolean isSpecPrinted() {
    return this.isSpecPrinted;
  }

  /**
   * Accesses certification indices governing assignment status flags.
   *
   * @return True if rubric metrics match target goals
   */
  public boolean hasApprovalStamp() {
    return this.hasApprovalStamp;
  }

  /**
   * Accesses completion indices measuring current structural exit statuses.
   *
   * @return True if door mechanisms successfully yield egress pathways
   */
  public boolean hasEscaped() {
    return this.hasEscaped;
  }
}