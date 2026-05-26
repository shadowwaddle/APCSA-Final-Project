// --- FILE: Game.java ---
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages core game initialization, input processing loop, and puzzle constraints.
 *
 * This class coordinates all architectural pieces, handling commands, state mutations,
 * turn counts, and processing victory/failure criteria precisely from the game spec.
 */
public class Game {
  private ArrayList<Room> rooms;
  private Player player;
  private int turnCount;
  private boolean isRunning;

  // Spec-defined game progression flags
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
   * Constructs the main Game orchestrator and establishes initial states.
   */
  public Game() {
    this.rooms = new ArrayList<Room>();
    this.player = new Player();
    this.turnCount = 0;
    this.isRunning = true;

    this.isBooleanFixed = false;
    this.isCodeDebugged = false;
    this.isAlgorithmBuilt = false;
    this.hasBookshelfUnlocked = false;
    this.isSpecPrinted = false;
    this.hasApprovalStamp = false;
    this.isCaught = false;
    this.hasLeftFrontDesk = false;
    this.hasEscaped = false;

    initializeWorld();
  }

  /**
   * Generates rooms, maps connections, assigns metadata, and positions starter tools.
   */
  private void initializeWorld() {
    Room frontDesk = new Room("Front Desk", "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. The classroom door behind her is locked.");
    Room whiteboardWall = new Room("Whiteboard Wall", "");
    Room computerStation = new Room("Computer Station", "");
    Room backTable = new Room("Back Table", "");
    Room bookshelf = new Room("Bookshelf", "");
    Room printerCorner = new Room("Printer Corner", "");
    Room rubricBoard = new Room("Rubric Board", "");
    Room classroomDoor = new Room("Classroom Door", "");

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

    // Room item initialization
    frontDesk.addItem(new Tool("Truth Table", "A guide to resolving logic arguments.", "Whiteboard"));
    computerStation.addItem(new Tool("Rubber Duck", "A little yellow plastic duck helpful for debugging.", "Computer"));
    backTable.addItem(new Tool("Index Cards", "Cards containing scrambled instructions.", "Table"));

    rooms.add(frontDesk);
    rooms.add(whiteboardWall);
    rooms.add(computerStation);
    rooms.add(backTable);
    rooms.add(bookshelf);
    rooms.add(printerCorner);
    rooms.add(rubricBoard);
    rooms.add(classroomDoor);

    player.setCurrentRoom(frontDesk);
  }

  /**
   * Loops user query processing until termination bounds are detected.
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    System.out.println(player.getCurrentRoom().getDescription(this));

    while (isRunning) {
      System.out.print("\n> ");
      if (!scanner.hasNextLine()) {
        break;
      }
      String input = scanner.nextLine().trim();
      if (input.length() > 0) {
        processCommand(input);
      }

      // Check ultimate win/loss triggers immediately post-command
      if (isCaught) {
        System.out.println("You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss.");
        isRunning = false;
      } else if (hasEscaped) {
        System.out.println("Satija unlocks the classroom door. You survived the Logic Audit.");
        isRunning = false;
      }
    }
    scanner.close();
  }

  /**
   * Decodes textual action strings and routes them to appropriate game behaviors.
   *
   * @param input Raw command line payload submitted by user.
   */
  private void processCommand(String input) {
    String lowerInput = input.toLowerCase();

    if (lowerInput.equals("quit")) {
      isRunning = false;
      return;
    }

    if (lowerInput.equals("look")) {
      Room current = player.getCurrentRoom();
      System.out.println(current.getDescription(this));
      
      ArrayList<Item> roomItems = current.getItems();
      if (roomItems.size() > 0) {
        System.out.print("Items in the room: ");
        for (int i = 0; i < roomItems.size(); i++) {
          System.out.print(roomItems.get(i).getName() + (i < roomItems.size() - 1 ? ", " : ""));
        }
        System.out.println();
      }
      turnCount++;
      processTurn();
      return;
    }

    if (lowerInput.equals("inventory")) {
      ArrayList<Item> inv = player.getInventory();
      if (inv.size() == 0) {
        System.out.println("Your inventory is empty.");
      } else {
        System.out.print("Your inventory: ");
        for (int i = 0; i < inv.size(); i++) {
          System.out.print(inv.get(i).getName() + (i < inv.size() - 1 ? ", " : ""));
        }
        System.out.println();
      }
      turnCount++;
      processTurn();
      return;
    }

    if (lowerInput.startsWith("go ")) {
      String direction = input.substring(3).trim();
      handleMovement(direction);
      return;
    }

    if (lowerInput.startsWith("take ")) {
      String targetItemName = input.substring(5).trim();
      handleTake(targetItemName);
      return;
    }

    if (lowerInput.startsWith("use ")) {
      handleUseCommand(input);
      return;
    }

    System.out.println("Unknown command.");
  }

  /**
   * Validates room layout mapping boundaries to transition location context.
   *
   * @param direction Targeted steering path component.
   */
  private void handleMovement(String direction) {
    Room current = player.getCurrentRoom();
    ArrayList<String> currentExits = current.getExits();
    String destinationName = null;

    // Manual parsing string loop extraction to skip forbidden built-in collections lookup
    for (String exitStr : currentExits) {
      int colonIndex = exitStr.indexOf(":");
      String dirPart = exitStr.substring(0, colonIndex);
      String destPart = exitStr.substring(colonIndex + 1);

      if (dirPart.equalsIgnoreCase(direction)) {
        destinationName = destPart;
        break;
      }
    }

    if (destinationName == null) {
      System.out.println("You can't go that way.");
      return;
    }

    Room nextRoom = findRoom(destinationName);
    if (nextRoom != null) {
      if (current.getName().equalsIgnoreCase("Front Desk")) {
        hasLeftFrontDesk = true;
      }

      player.setCurrentRoom(nextRoom);
      
      if (nextRoom.getName().equalsIgnoreCase("Front Desk") && hasLeftFrontDesk) {
        isCaught = true;
      } else {
        System.out.println(nextRoom.getDescription(this));
      }

      turnCount++;
      processTurn();
    }
  }

  /**
   * Transfers item scope ownership from current room to backpack.
   *
   * @param itemName Name designation string of structural item target.
   */
  private void handleTake(String itemName) {
    Room current = player.getCurrentRoom();
    Item item = current.findItem(itemName);

    if (item == null) {
      System.out.println("That item is not here.");
      return;
    }

    if (player.addItem(item)) {
      current.removeItem(item);
      System.out.println("You took the " + item.getName() + ".");
      turnCount++;
      processTurn();
    }
  }

  /**
   * Processes item-target parsing for interactions (e.g. "use [item] on [target]").
   *
   * @param rawInput Full input string submitted by user.
   */
  private void handleUseCommand(String rawInput) {
    String lower = rawInput.toLowerCase();
    int onIndex = lower.indexOf(" on ");

    if (onIndex == -1 || !lower.startsWith("use ")) {
      System.out.println("Use command format should be: use [item] on [target]");
      return;
    }

    String itemName = rawInput.substring(4, onIndex).trim();
    String targetName = rawInput.substring(onIndex + 4).trim();

    Item playerItem = player.findItem(itemName);
    if (playerItem == null) {
      System.out.println("You don't have that item.");
      return;
    }

    Room current = player.getCurrentRoom();
    executeInteraction(playerItem, targetName, current);
  }

  /**
   * Coordinates interactions, validates milestones, and alters dependencies.
   *
   * @param item The Item object intended for use.
   * @param target The name of the object to use the item on.
   * @param current The room location context where invocation occurs.
   */
  private void executeInteraction(Item item, String target, Room current) {
    boolean triggered = false;

    // Boolean Puzzle
    if (item.getName().equalsIgnoreCase("Truth Table") && target.equalsIgnoreCase("Whiteboard")) {
      if (current.getName().equalsIgnoreCase("Whiteboard Wall") && !isBooleanFixed) {
        isBooleanFixed = true;
        System.out.println("You simplify the boolean expression correctly. Satija silently nods in approval.");
        triggered = true;
      }
    }

    // Debug Puzzle
    else if (item.getName().equalsIgnoreCase("Rubber Duck") && target.equalsIgnoreCase("Computer")) {
      if (current.getName().equalsIgnoreCase("Computer Station") && isBooleanFixed && !isCodeDebugged) {
        isCodeDebugged = true;
        System.out.println("You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious.");
        triggered = true;
      }
    }

    // Algorithm Puzzle
    else if (item.getName().equalsIgnoreCase("Index Cards") && target.equalsIgnoreCase("Table")) {
      if (current.getName().equalsIgnoreCase("Back Table") && isCodeDebugged && !isAlgorithmBuilt) {
        isAlgorithmBuilt = true;
        current.addItem(new KeyItem("Algorithm Card", "A card proving your algorithm design skills.", "Bookshelf"));
        System.out.println("You arrange the algorithm steps into the correct order. A hidden compartment opens.");
        triggered = true;
      }
    }

    // Unlock Bookshelf
    else if (item.getName().equalsIgnoreCase("Algorithm Card") && target.equalsIgnoreCase("Bookshelf")) {
      if (current.getName().equalsIgnoreCase("Bookshelf") && isAlgorithmBuilt && !hasBookshelfUnlocked) {
        hasBookshelfUnlocked = true;
        current.addItem(new Document("Java Manual", "The complete specification reference guide.", "Java Reference Manual Data"));
        System.out.println("The locked shelf clicks open, revealing the sacred Java Manual.");
        triggered = true;
      }
    }

    // Print Spec
    else if (item.getName().equalsIgnoreCase("Java Manual") && target.equalsIgnoreCase("Printer")) {
      if (current.getName().equalsIgnoreCase("Printer Corner") && hasBookshelfUnlocked && !isSpecPrinted) {
        isSpecPrinted = true;
        current.addItem(new Document("Printed Spec", "The printed program requirement documentation.", "Formatted System Specification"));
        System.out.println("The printer groans dramatically before printing your completed spec.");
        triggered = true;
      }
    }

    // Approval Puzzle
    else if (item.getName().equalsIgnoreCase("Printed Spec") && target.equalsIgnoreCase("Rubric")) {
      if (current.getName().equalsIgnoreCase("Rubric Board") && isSpecPrinted && !hasApprovalStamp) {
        hasApprovalStamp = true;
        current.addItem(new KeyItem("Final Approval Stamp", "Gives the authorization required to leave.", "Door"));
        System.out.println("A giant green APPROVED stamp slams onto your spec.");
        triggered = true;
      }
    }

    // Escape Sequence
    else if (item.getName().equalsIgnoreCase("Final Approval Stamp") && target.equalsIgnoreCase("Door")) {
      if (current.getName().equalsIgnoreCase("Classroom Door") && hasApprovalStamp && !hasEscaped) {
        hasEscaped = true;
        triggered = true;
      }
    }

    if (triggered) {
      turnCount++;
      processTurn();
    } else {
      System.out.println("Nothing happens.");
    }
  }

  /**
   * Tracks structural turn loop increments.
   */
  private void processTurn() {
    // Intentionally left blank as no custom turn logic exists in the specification.
  }

  /**
   * Looks up internal room representations manually iterating matching keys.
   *
   * @param roomName Targeted textual room label descriptor.
   * @return Found internal Room object, or null if missing.
   */
  public Room findRoom(String roomName) {
    for (Room room : rooms) {
      if (room.getName().equalsIgnoreCase(roomName)) {
        return room;
      }
    }
    return null;
  }

  public boolean isBooleanFixed() { return isBooleanFixed; }
  public boolean isCodeDebugged() { return isCodeDebugged; }
  public boolean isAlgorithmBuilt() { return isAlgorithmBuilt; }
  public boolean hasBookshelfUnlocked() { return hasBookshelfUnlocked; }
  public boolean isSpecPrinted() { return isSpecPrinted; }
  public boolean hasApprovalStamp() { return hasApprovalStamp; }
  public boolean hasEscaped() { return hasEscaped; }
}