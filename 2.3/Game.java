import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the central application logic loop, text parsing, and puzzle evaluation.
 * Hosts explicit state flags and ensures rules match the student specification.
 * Coordinates movement processing, tracking metrics, and processing ticks.
 */
public class Game {
  private ArrayList<Room> rooms;
  private Player player;
  private boolean isRunning;
  private int turnCount;

  // Global game state trackers defined explicitly by specification
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
   * Instantiates empty lists and tracks tracking primitives safely.
   */
  public Game() {
    this.rooms = new ArrayList<Room>();
    this.player = new Player();
    this.isRunning = true;
    this.turnCount = 0;

    // Establishing default structural states
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
   * Puzzles out initialization routines and drives structural looping commands.
   */
  public void start() {
    initializeWorld();
    Scanner scanner = new Scanner(System.in);

    // Initial structural text introduction display
    System.out.println(player.getCurrentRoom().getDescription(this));

    while (isRunning) {
      System.out.print("\n> ");
      String input = scanner.nextLine().trim();
      if (input.length() > 0) {
        processCommand(input);
      }
      checkGameStatus();
    }
    scanner.close();
  }

  /**
   * Spawns physical structures, connects routing vectors, and fills inventories.
   */
  private void initializeWorld() {
    // Generate Room instances with baseline texts
    Room r1 = new Room("Front Desk", "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. Before you leave, she hands you a laminated Truth Table sheet and tells you not to lose it. The classroom door behind her is locked.");
    Room r2 = new Room("Whiteboard Wall", "The whiteboard is covered with a giant unsolved boolean expression written in red marker.");
    Room r3 = new Room("Computer Station", "A Code.org project is open on the monitor. The compiler window is filled with red error messages.");
    Room r4 = new Room("Back Table", "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.");
    Room r5 = new Room("Bookshelf", "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.");
    Room r6 = new Room("Printer Corner", "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.");
    Room r7 = new Room("Rubric Board", "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.");
    Room r8 = new Room("Classroom Door", "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.");

    // Connect movement pathways via standardized formatting strings
    r1.addExit("East:Whiteboard Wall");
    r1.addExit("South:Classroom Door");

    r2.addExit("West:Front Desk");
    r2.addExit("East:Computer Station");
    r2.addExit("South:Rubric Board");

    r3.addExit("West:Whiteboard Wall");
    r3.addExit("South:Back Table");

    r4.addExit("North:Computer Station");
    r4.addExit("West:Rubric Board");
    r4.addExit("East:Bookshelf");

    r5.addExit("North:Back Table");
    r5.addExit("West:Printer Corner");

    r6.addExit("East:Bookshelf");
    r6.addExit("North:Rubric Board");

    r7.addExit("West:Classroom Door");
    r7.addExit("North:Whiteboard Wall");
    r7.addExit("East:Back Table");
    r7.addExit("South:Printer Corner");

    r8.addExit("North:Front Desk");
    r8.addExit("East:Rubric Board");

    // Add rooms to standard repository arrays
    rooms.add(r1);
    rooms.add(r2);
    rooms.add(r3);
    rooms.add(r4);
    rooms.add(r5);
    rooms.add(r6);
    rooms.add(r7);
    rooms.add(r8);

    // Distribute stationary game utilities based on item spec configurations
    r3.addItem(new Tool("Rubber Duck", "A helpful programming companion.", "computer"));
    r4.addItem(new Tool("Index Cards", "Cards containing steps of an algorithm.", "table"));

    // Prepare character entry points and supply baseline inventory
    player.setCurrentRoom(r1);
    player.addItem(new Tool("Truth Table", "A laminated reference card for boolean values.", "whiteboard"));
  }

  /**
   * Resolves token inputs and routes tasks down exact functional paths.
   *
   * @param input Raw textual instruction from standard user inputs
   */
  public void processCommand(String input) {
    String lowerInput = input.toLowerCase();

    if (lowerInput.startsWith("go ")) {
      String dir = input.substring(3).trim();
      handleMovement(dir);
    } else if (lowerInput.equalsIgnoreCase("look")) {
      handleLook();
    } else if (lowerInput.equalsIgnoreCase("inventory")) {
      handleInventory();
    } else if (lowerInput.startsWith("take ")) {
      String itemName = input.substring(5).trim();
      handleTake(itemName);
    } else if (lowerInput.startsWith("use ") && lowerInput.contains(" on ")) {
      parseAndHandleUse(input);
    } else if (lowerInput.equalsIgnoreCase("quit")) {
      System.out.println("Exiting the simulator.");
      isRunning = false;
    } else {
      System.out.println("Unknown command pattern. Please speak clearly.");
    }
  }

  /**
   * Tracks target room transitions via split directional validation mappings.
   * Employs required non-hash loop scanning mechanics for safety.
   *
   * @param direction Target traversal vector string
   */
  private void handleMovement(String direction) {
    Room current = player.getCurrentRoom();
    ArrayList<String> currentExits = current.getExits();
    String destinationName = null;

    // Search exit pathways manually to protect indexing constraints
    for (String exitStr : currentExits) {
      int splitIndex = exitStr.indexOf(":");
      String dirPart = exitStr.substring(0, splitIndex);
      String destPart = exitStr.substring(splitIndex + 1);

      if (dirPart.equalsIgnoreCase(direction)) {
        destinationName = destPart;
        break;
      }
    }

    if (destinationName == null) {
      System.out.println("You cannot move in that direction here.");
      return;
    }

    Room nextRoom = findRoom(destinationName);
    if (nextRoom != null) {
      // Monitor classroom tracking traps when moving out of Front Desk
      if (current.getName().equalsIgnoreCase("Front Desk")) {
        hasLeftFrontDesk = true;
      }

      player.setCurrentRoom(nextRoom);

      // Trigger tracking traps immediately upon processing entry states
      if (nextRoom.getName().equalsIgnoreCase("Front Desk") && hasLeftFrontDesk) {
        isCaught = true;
        return;
      }

      System.out.println(nextRoom.getDescription(this));
      incrementTurnCounter();
    }
  }

  /**
   * Emits structural definitions and summarizes details regarding visible items.
   */
  private void handleLook() {
    Room current = player.getCurrentRoom();
    System.out.println(current.getDescription(this));
    ArrayList<Item> roomItems = current.getItems();
    if (roomItems.size() > 0) {
      System.out.print("Visible items: ");
      for (int i = 0; i < roomItems.size(); i++) {
        System.out.print(roomItems.get(i).getName() + (i == roomItems.size() - 1 ? "" : ", "));
      }
      System.out.println();
    } else {
      System.out.println("There are no loose items here.");
    }
    incrementTurnCounter();
  }

  /**
   * Spits back active inventory components stored inside tracking states.
   */
  private void handleInventory() {
    ArrayList<Item> inv = player.getInventory();
    if (inv.size() == 0) {
      System.out.println("Your backpack is completely empty.");
    } else {
      System.out.print("Your current inventory: ");
      for (int i = 0; i < inv.size(); i++) {
        System.out.print(inv.get(i).getName() + (i == inv.size() - 1 ? "" : ", "));
      }
      System.out.println();
    }
    incrementTurnCounter();
  }

  /**
   * Moves items from spatial matrices to backpack records using precise matching.
   *
   * @param itemName Text argument detailing target candidate item
   */
  private void handleTake(String itemName) {
    Room current = player.getCurrentRoom();
    Item match = null;

    // Custom loop ensuring verification of room matches without restricted lookups
    for (Item it : current.getItems()) {
      if (it.getName().equalsIgnoreCase(itemName)) {
        match = it;
        break;
      }
    }

    if (match == null) {
      System.out.println("That item is not present in this room.");
      return;
    }

    // Verify system limits beforehand; space removal depends directly on backpack success
    if (player.addItem(match)) {
      current.removeItem(match);
      System.out.println("You securely pocket the " + match.getName() + ".");
      incrementTurnCounter();
    }
  }

  /**
   * Extracts parameter keys out of multi-word text payloads.
   * Splits keywords cleanly across the explicit linking word "on".
   *
   * @param input Full composite use string command
   */
  private void parseAndHandleUse(String input) {
    String base = input.substring(4); // Drop structural leading word "use "
    int onIdx = base.toLowerCase().indexOf(" on ");
    if (onIdx == -1) return;

    String itemName = base.substring(0, onIdx).trim();
    String targetName = base.substring(onIdx + 4).trim();

    executeUseLogic(itemName, targetName);
  }

  /**
   * Evaluates usage requests against puzzle matrices and updates tracking states.
   *
   * @param itemName Candidate object tag queried from player resources
   * @param targetName Destination operational surface being impacted
   */
  private void executeUseLogic(String itemName, String targetName) {
    Item heldItem = player.findItem(itemName);
    Room current = player.getCurrentRoom();

    if (heldItem == null) {
      System.out.println("You are not carrying that item.");
      return;
    }

    String currentRoomName = current.getName();

    // 1. Boolean Puzzle Verification
    if (itemName.equalsIgnoreCase("Truth Table") && targetName.equalsIgnoreCase("Whiteboard")) {
      if (currentRoomName.equalsIgnoreCase("Whiteboard Wall")) {
        if (!isBooleanFixed) {
          isBooleanFixed = true;
          System.out.println("You simplify the boolean expression correctly. Satija silently nods in approval.");
          incrementTurnCounter();
        } else {
          System.out.println("The whiteboard expression has already been fully calculated.");
        }
      } else {
        System.out.println("There is no corresponding whiteboard surface to apply that to here.");
      }
      return;
    }

    // 2. Debug Puzzle Verification
    if (itemName.equalsIgnoreCase("Rubber Duck") && targetName.equalsIgnoreCase("Computer")) {
      if (currentRoomName.equalsIgnoreCase("Computer Station")) {
        if (!isBooleanFixed) {
          System.out.println("The compilation error is too overwhelming. Resolve the Boolean Puzzle first!");
          return;
        }
        if (!isCodeDebugged) {
          isCodeDebugged = true;
          System.out.println("You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious.");
          incrementTurnCounter();
        } else {
          System.out.println("The console display already outputs a successful status code.");
        }
      } else {
        System.out.println("There is no computer configuration stationed here.");
      }
      return;
    }

    // 3. Algorithm Puzzle Verification
    if (itemName.equalsIgnoreCase("Index Cards") && targetName.equalsIgnoreCase("Table")) {
      if (currentRoomName.equalsIgnoreCase("Back Table")) {
        if (!isCodeDebugged) {
          System.out.println("You need to finish debugging the core workstation logic before sorting layout metrics.");
          return;
        }
        if (!isAlgorithmBuilt) {
          isAlgorithmBuilt = true;
          current.addItem(new KeyItem("Algorithm Card", "A specialized validation token.", "bookshelf"));
          System.out.println("You arrange the algorithm steps into the correct order. A hidden compartment opens.");
          incrementTurnCounter();
        } else {
          System.out.println("The index cards are already structured cleanly on the surface.");
        }
      } else {
        System.out.println("There is no appropriate workstation desk to place these items upon.");
      }
      return;
    }

    // 4. Unlock Bookshelf Verification
    if (itemName.equalsIgnoreCase("Algorithm Card") && targetName.equalsIgnoreCase("Bookshelf")) {
      if (currentRoomName.equalsIgnoreCase("Bookshelf")) {
        if (!isAlgorithmBuilt) return;
        if (!hasBookshelfUnlocked) {
          hasBookshelfUnlocked = true;
          current.addItem(new Document("Java Manual", "A comprehensive guide on syntax patterns.", "Core Syntax"));
          System.out.println("The locked shelf clicks open, revealing the sacred Java Manual.");
          player.removeItem(heldItem); // Drop spent operational token from inventory records
          incrementTurnCounter();
        } else {
          System.out.println("The targeted shelf compartment is already wide open.");
        }
      } else {
        System.out.println("No large reference shelves are mounted within this space.");
      }
      return;
    }

    // 5. Print Spec Verification
    if (itemName.equalsIgnoreCase("Java Manual") && targetName.equalsIgnoreCase("Printer")) {
      if (currentRoomName.equalsIgnoreCase("Printer Corner")) {
        if (!hasBookshelfUnlocked) return;
        if (!isSpecPrinted) {
          isSpecPrinted = true;
          current.addItem(new Document("Printed Spec", "The finalized functional program blueprint.", "Spec Content"));
          System.out.println("The printer groans dramatically before printing your completed spec.");
          incrementTurnCounter();
        } else {
          System.out.println("The industrial hardware rests quietly; its task has concluded.");
        }
      } else {
        System.out.println("There are no printing devices active in this section.");
      }
      return;
    }

    // 6. Approval Puzzle Verification
    if (itemName.equalsIgnoreCase("Printed Spec") && targetName.equalsIgnoreCase("Rubric")) {
      if (currentRoomName.equalsIgnoreCase("Rubric Board")) {
        if (!isSpecPrinted) return;
        if (!hasApprovalStamp) {
          hasApprovalStamp = true;
          current.addItem(new KeyItem("Final Approval Stamp", "The exit clearance token.", "door"));
          System.out.println("A giant green APPROVED stamp slams onto your spec.");
          player.removeItem(heldItem); // Consume matching items following completion
          incrementTurnCounter();
        } else {
          System.out.println("The wall chart rubric already glows with a validation approval mark.");
        }
      } else {
        System.out.println("The validation rubric layout is not displayed in this sector.");
      }
      return;
    }

    // 7. Escape Sequence Verification
    if (itemName.equalsIgnoreCase("Final Approval Stamp") && targetName.equalsIgnoreCase("Door")) {
      if (currentRoomName.equalsIgnoreCase("Classroom Door")) {
        if (!hasApprovalStamp) return;
        if (!hasEscaped) {
          hasEscaped = true;
          System.out.println("Satija unlocks the classroom door. You survived the Logic Audit.");
          player.removeItem(heldItem);
          incrementTurnCounter();
        }
      } else {
        System.out.println("There is no primary exit lock present in this corner.");
      }
      return;
    }

    System.out.println("That interaction yields no practical outcome or is locked by sequencing restrictions.");
  }

  /**
   * Tracks game steps smoothly and routes processing hooks.
   */
  private void incrementTurnCounter() {
    this.turnCount++;
    processTurn();
  }

  /**
   * Fallback structural processing block ensuring compliance with spec updates.
   */
  private void processTurn() {
    // Left purposefully open to handle expansion features per specification guidelines
  }

  /**
   * Scans room list properties manually to enforce code engine isolation protocols.
   *
   * @param roomName Looked up name keyword parameter string
   * @return The matched room configuration, or null if undiscovered
   */
  private Room findRoom(String roomName) {
    // Sequential loop mapping mandatory array checks safely
    for (Room r : rooms) {
      if (r.getName().equalsIgnoreCase(roomName)) {
        return r;
      }
    }
    return null;
  }

  /**
   * Monopolizes processing flows to intercept victory states or trap execution alerts.
   */
  private void checkGameStatus() {
    if (isCaught) {
      System.out.println("You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss.");
      isRunning = false;
    } else if (hasEscaped) {
      isRunning = false;
    }
  }

  // Getters provided exclusively to process dynamic content switching rules
  public boolean isBooleanFixed() { return isBooleanFixed; }
  public boolean isCodeDebugged() { return isCodeDebugged; }
  public boolean isAlgorithmBuilt() { return isAlgorithmBuilt; }
  public boolean hasBookshelfUnlocked() { return hasBookshelfUnlocked; }
  public boolean isSpecPrinted() { return isSpecPrinted; }
  public boolean hasApprovalStamp() { return hasApprovalStamp; }
  public boolean hasEscaped() { return hasEscaped; }
}