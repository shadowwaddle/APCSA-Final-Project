// --- FILE: Game.java ---
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Orchestrates the primary game loop, state transitions, parsing, and world mechanics.
 *
 * This class maintains all structural flags describing the layout of puzzles completed,
 * coordinates spatial room routing configurations, and executes the central turn cycle.
 * Relates to Player, Room, and Item to connect mechanics into a coherent loop.
 */
public class Game {
  private ArrayList<Room> rooms;
  private Player player;
  private boolean isRunning;
  private int turnCount;

  // Global State Puzzles Flags
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
   * Initializes structural game components, tracking parameters, and room states.
   */
  public Game() {
    this.rooms = new ArrayList<Room>();
    this.player = new Player();
    this.isRunning = true;
    this.turnCount = 0;

    // Defaulting progression states
    this.isBooleanFixed = false;
    this.isCodeDebugged = false;
    this.isAlgorithmBuilt = false;
    this.hasBookshelfUnlocked = false;
    this.isSpecPrinted = false;
    this.hasApprovalStamp = false;
    this.isCaught = false;
    this.hasLeftFrontDesk = false;
    this.hasEscaped = false;

    buildWorld();
  }

  /**
   * Assembles the academic layout by creating rooms, establishing paths, and mapping tools.
   */
  private void buildWorld() {
    // Instantiate all map layout nodes with structural or interactive specs
    Room frontDesk = new Room("Front Desk", 
      "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. Before you leave, she hands you a laminated Truth Table sheet and tells you not to lose it. The classroom door behind her is locked.", 
      "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. Before you leave, she hands you a laminated Truth Table sheet and tells you not to lose it. The classroom door behind her is locked.", 
      "none");

    Room whiteboardWall = new Room("Whiteboard Wall",
      "The whiteboard is covered with a giant unsolved boolean expression written in red marker.",
      "The boolean expression has been simplified correctly. Someone wrote 'DeMorgan was here' in the corner.",
      "isBooleanFixed");

    Room computerStation = new Room("Computer Station",
      "A Code.org project is open on the monitor. The compiler window is filled with red error messages.",
      "The Java program compiles successfully. The monitor proudly displays '0 ERRORS'.",
      "isCodeDebugged");

    Room backTable = new Room("Back Table",
      "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.",
      "The algorithm cards are neatly organized. A hidden compartment inside the table has opened.",
      "isAlgorithmBuilt");

    Room bookshelf = new Room("Bookshelf",
      "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.",
      "The locked shelf is now open.",
      "hasBookshelfUnlocked");

    Room printerCorner = new Room("Printer Corner",
      "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.",
      "The printer finally rests quietly after printing a perfectly formatted spec.",
      "isSpecPrinted");

    Room rubricBoard = new Room("Rubric Board",
      "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.",
      "A bright green APPROVED stamp now covers the rubric.",
      "hasApprovalStamp");

    Room classroomDoor = new Room("Classroom Door",
      "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.",
      "The classroom door swings open. Freedom at last.",
      "hasEscaped");

    // Map and configure boundaries/exits using spatial labels
    frontDesk.addExit("East:Whiteboard Wall");
    frontDesk.addExit("South:Classroom Door");

    whiteboardWall.addExit("West:Front Desk");
    whiteboardWall.addExit("East:Computer Station");
    whiteboardWall.addExit("South:Rubric Board");

    computerStation.addExit("West:Whiteboard Wall");
    computerStation.addExit("South:Back Table");

    backTable.addExit("North:Computer Station");
    backTable.addExit("West:Rubric Board");
    backTable.addExit("East:Bookshelf");

    bookshelf.addExit("North:Back Table");
    bookshelf.addExit("West:Printer Corner");

    printerCorner.addExit("East:Bookshelf");
    printerCorner.addExit("North:Rubric Board");

    rubricBoard.addExit("West:Classroom Door");
    rubricBoard.addExit("North:Whiteboard Wall");
    rubricBoard.addExit("East:Back Table");
    rubricBoard.addExit("South:Printer Corner");

    classroomDoor.addExit("North:Front Desk");
    classroomDoor.addExit("East:Rubric Board");

    // Put initial foundational item structures into place
    computerStation.addItem(new Tool("Rubber Duck", "A small yellow duck helpful for processing logic errors out loud.", "computer"));
    backTable.addItem(new Tool("Index Cards", "Cards containing fractured segments of a sorting loop configuration.", "table"));

    // Add rooms to collection loop
    rooms.add(frontDesk);
    rooms.add(whiteboardWall);
    rooms.add(computerStation);
    rooms.add(backTable);
    rooms.add(bookshelf);
    rooms.add(printerCorner);
    rooms.add(rubricBoard);
    rooms.add(classroomDoor);

    // Seed player setup data contextually
    player.setCurrentRoom(frontDesk);
    player.addItem(new Tool("Truth Table", "A reference sheet detailing boolean expressions logic tracking.", "whiteboard"));
  }

  // Getters targeting state verification requirements from inside Room instances
  public boolean isBooleanFixed() { return isBooleanFixed; }
  public boolean isCodeDebugged() { return isCodeDebugged; }
  public boolean isAlgorithmBuilt() { return isAlgorithmBuilt; }
  public boolean hasBookshelfUnlocked() { return hasBookshelfUnlocked; }
  public boolean isSpecPrinted() { return isSpecPrinted; }
  public boolean hasApprovalStamp() { return hasApprovalStamp; }
  public boolean hasEscaped() { return hasEscaped; }

  /**
   * Starts and executes the interactive command interpretation loop until a terminal state is hit.
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("--- ESCAPE FROM SATIJA'S CLASS ---");
    System.out.println(player.getCurrentRoom().getDescription(this));

    while (isRunning) {
      System.out.print("\n> ");
      String rawInput = scanner.nextLine();
      processCommand(rawInput);
      checkEndConditions();
    }
    scanner.close();
  }

  /**
   * Breaks down and dispatches actions input by the student during structural evaluation steps.
   *
   * @param rawInput the plain text entered by the user
   */
  private void processCommand(String rawInput) {
    String input = rawInput.trim();
    if (input.isEmpty()) {
      return;
    }

    String lowerInput = input.toLowerCase();
    boolean commandActioned = false;

    if (lowerInput.equals("quit")) {
      isRunning = false;
      System.out.println("Exiting puzzle context loop.");
      return;
    } else if (lowerInput.equals("look")) {
      System.out.println(player.getCurrentRoom().getDescription(this));
      printRoomItems(player.getCurrentRoom());
      commandActioned = true;
    } else if (lowerInput.equals("inventory")) {
      printPlayerInventory();
      commandActioned = true;
    } else if (lowerInput.startsWith("go ")) {
      String direction = input.substring(3).trim();
      commandActioned = executeMovement(direction);
    } else if (lowerInput.startsWith("take ")) {
      String targetItem = input.substring(5).trim();
      commandActioned = executeTakeItem(targetItem);
    } else if (lowerInput.startsWith("use ") && lowerInput.contains(" on ")) {
      commandActioned = parseAndExecuteUseInteraction(input);
    } else {
      System.out.println("Command not recognized or malformed syntax used.");
    }

    // Process environmental turn tracking if command actions successfully cleared bounds
    if (commandActioned) {
      turnCount++;
      processTurn();
    }
  }

  /**
   * Routes the student across physical layout nodes based on valid connection indicators.
   *
   * @param direction the direction keyword entered by the user
   * @return true if structural relocation succeeded, false if pathing was rejected
   */
  private boolean executeMovement(String direction) {
    Room current = player.getCurrentRoom();
    ArrayList<String> localExits = current.getExits();

    // Trace path listings via manual collection loop traversal
    for (String exitStr : localExits) {
      int colonIndex = exitStr.indexOf(":");
      String dirPart = exitStr.substring(0, colonIndex);
      String destPart = exitStr.substring(colonIndex + 1);

      if (dirPart.equalsIgnoreCase(direction)) {
        Room targetRoom = findRoom(destPart);
        if (targetRoom != null) {
          // Trap flag evaluation logic for the Front Desk Room Re-entry sequence
          if (current.getName().equals("Front Desk") && !hasLeftFrontDesk) {
            hasLeftFrontDesk = true;
          }

          player.setCurrentRoom(targetRoom);
          System.out.println("You move to the " + targetRoom.getName() + ".");
          System.out.println(targetRoom.getDescription(this));
          return true;
        }
      }
    }

    System.out.println("You cannot go that way.");
    return false;
  }

  /**
   * Relocates objects out of room containment environments into student possession arrays.
   *
   * @param itemName the text description of the object item targeted
   * @return true if item transferred into inventory successfully, false otherwise
   */
  private boolean executeTakeItem(String itemName) {
    Room current = player.getCurrentRoom();
    ArrayList<Item> roomItems = current.getItems();

    for (int i = 0; i < roomItems.size(); i++) {
      if (roomItems.get(i).getName().equalsIgnoreCase(itemName)) {
        Item requestedItem = roomItems.get(i);

        // Attempt transfer to pack while respecting capacity validation bounds
        if (player.addItem(requestedItem)) {
          current.removeItemAt(i);
          System.out.println("You take the " + requestedItem.getName() + ".");
          return true;
        }
        return false;
      }
    }

    System.out.println("That item is not here.");
    return false;
  }

  /**
   * Identifies components within user command structures to route item activation scripts.
   *
   * @param fullCommand the complete text string containing targeted tools and targets
   * @return true if the execution parsed through standard logic paths without dropping out
   */
  private boolean parseAndExecuteUseInteraction(String fullCommand) {
    String lowerCommand = fullCommand.toLowerCase();
    int useIndex = 4; // Length of "use "
    int onIndex = lowerCommand.indexOf(" on ");

    String itemName = fullCommand.substring(useIndex, onIndex).trim();
    String targetName = fullCommand.substring(onIndex + 4).trim();

    Item matchingItem = player.findItem(itemName);
    Room currentRoom = player.getCurrentRoom();

    if (matchingItem == null) {
      System.out.println("You do not have that item in your inventory.");
      return false;
    }

    String currentRoomName = currentRoom.getName();

    // 1. Boolean Puzzle Verification
    if (itemName.equalsIgnoreCase("Truth Table") && targetName.equalsIgnoreCase("Whiteboard")) {
      if (currentRoomName.equals("Whiteboard Wall") && !isBooleanFixed) {
        isBooleanFixed = true;
        System.out.println("You simplify the boolean expression correctly. Satija silently nods in approval.");
        return true;
      }
    }

    // 2. Debug Puzzle Verification
    if (itemName.equalsIgnoreCase("Rubber Duck") && targetName.equalsIgnoreCase("Computer")) {
      if (currentRoomName.equals("Computer Station") && isBooleanFixed && !isCodeDebugged) {
        isCodeDebugged = true;
        System.out.println("You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious.");
        return true;
      }
    }

    // 3. Algorithm Puzzle Verification
    if (itemName.equalsIgnoreCase("Index Cards") && targetName.equalsIgnoreCase("Table")) {
      if (currentRoomName.equals("Back Table") && isCodeDebugged && !isAlgorithmBuilt) {
        isAlgorithmBuilt = true;
        currentRoom.addItem(new KeyItem("Algorithm Card", "A specialized validation sequence identification card.", "Bookshelf"));
        System.out.println("You arrange the algorithm steps into the correct order. A hidden compartment opens.");
        return true;
      }
    }

    // 4. Unlock Bookshelf Verification
    if (itemName.equalsIgnoreCase("Algorithm Card") && targetName.equalsIgnoreCase("Bookshelf")) {
      if (currentRoomName.equals("Bookshelf") && isAlgorithmBuilt && !hasBookshelfUnlocked) {
        hasBookshelfUnlocked = true;
        currentRoom.addItem(new Document("Java Manual", "The ultimate book detailing the syntax laws of the language.", "Syntax structures definition..."));
        System.out.println("The locked shelf clicks open, revealing the sacred Java Manual.");
        removePlayerItem(itemName);
        return true;
      }
    }

    // 5. Print Spec Verification
    if (itemName.equalsIgnoreCase("Java Manual") && targetName.equalsIgnoreCase("Printer")) {
      if (currentRoomName.equals("Printer Corner") && hasBookshelfUnlocked && !isSpecPrinted) {
        isSpecPrinted = true;
        currentRoom.addItem(new Document("Printed Spec", "Your freshly compiled design document.", "Class layout blueprints..."));
        System.out.println("The printer groans dramatically before printing your completed spec.");
        removePlayerItem(itemName);
        return true;
      }
    }

    // 6. Approval Puzzle Verification
    if (itemName.equalsIgnoreCase("Printed Spec") && targetName.equalsIgnoreCase("Rubric")) {
      if (currentRoomName.equals("Rubric Board") && isSpecPrinted && !hasApprovalStamp) {
        hasApprovalStamp = true;
        currentRoom.addItem(new KeyItem("Final Approval Stamp", "A validation token providing clearance to exit.", "Door"));
        System.out.println("A giant green APPROVED stamp slams onto your spec.");
        removePlayerItem(itemName);
        return true;
      }
    }

    // 7. Escape Sequence Verification
    if (itemName.equalsIgnoreCase("Final Approval Stamp") && targetName.equalsIgnoreCase("Door")) {
      if (currentRoomName.equals("Classroom Door") && hasApprovalStamp && !hasEscaped) {
        hasEscaped = true;
        System.out.println("Satija unlocks the classroom door. You survived the Logic Audit.");
        removePlayerItem(itemName);
        return true;
      }
    }

    System.out.println("Nothing happens. Check your prerequisites or interaction target names.");
    return false;
  }

  /**
   * Checks conditions per turn cycle and updates active structural trap parameters.
   */
  private void processTurn() {
    // Evaluation sequence monitoring if the player re-enters the primary desk cell boundary
    if (player.getCurrentRoom().getName().equals("Front Desk") && hasLeftFrontDesk && !hasEscaped) {
      isCaught = true;
    }
  }

  /**
   * Terminates processing states instantly if conditional win/loss parameters check positive.
   */
  private void checkEndConditions() {
    if (isCaught) {
      System.out.println("You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss.");
      isRunning = false;
    } else if (hasEscaped) {
      isRunning = false;
    }
  }

  /**
   * Iterates local room items cleanly without using automated native map/search libraries.
   *
   * @param room the specific Room targeted for examination
   */
  private void printRoomItems(Room room) {
    ArrayList<Item> items = room.getItems();
    if (items.isEmpty()) {
      System.out.println("There are no items here.");
      return;
    }
    System.out.print("Items here: ");
    for (int i = 0; i < items.size(); i++) {
      System.out.print("[" + items.get(i).getName() + "]");
      if (i < items.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();
  }

  /**
   * Reports the collection array summary of components carried inside player backpack storage.
   */
  private void printPlayerInventory() {
    ArrayList<Item> inv = player.getInventory();
    if (inv.isEmpty()) {
      System.out.println("Your inventory is empty.");
      return;
    }
    System.out.print("Your inventory: ");
    for (int i = 0; i < inv.size(); i++) {
      System.out.print("[" + inv.get(i).getName() + "]");
      if (i < inv.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();
  }

  /**
   * Iterates over internal listings to locate a target map area using manual match strategies.
   *
   * @param roomName the text description label identifying the target zone
   * @return the located Room object references, or null if missing from world data
   */
  private Room findRoom(String roomName) {
    // Explicit list search enforced manually without using underlying search wrappers
    for (Room room : rooms) {
      if (room.getName().equalsIgnoreCase(roomName)) {
        return room;
      }
    }
    return null;
  }

  /**
   * Safely clears elements out of user tracking structures after single-use puzzle logic maps.
   *
   * @param itemName the name label of the item targeted for destruction
   */
  private void removePlayerItem(String itemName) {
    ArrayList<Item> inv = player.getInventory();
    for (int i = 0; i < inv.size(); i++) {
      if (inv.get(i).getName().equalsIgnoreCase(itemName)) {
        player.removeItemAt(i);
        return;
      }
    }
  }
}