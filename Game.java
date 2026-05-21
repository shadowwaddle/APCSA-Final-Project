import java.util.ArrayList;
import java.util.Scanner;

/**
 * Central system controller tracking entire logic execution rules.
 *
 * Implements global flags tracking state parameters, system inputs, processing loops, 
 * directional matching, map setup, and critical win/loss constraint monitoring.
 */
public class Game {
  private ArrayList<Room> rooms;
  private Player player;
  private int turnCount;
  private boolean isRunning;

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
   * Constructs state systems and generates game structural items.
   */
  public Game() {
    this.rooms = new ArrayList<Room>();
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

    this.initializeWorld();
  }

  /**
   * Assembles room environments, paths, and initial inventory placements.
   */
  private void initializeWorld() {
    Room frontDesk = new Room("Front Desk", "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. The classroom door behind her is locked.");
    Room whiteboardWall = new Room("Whiteboard Wall", "The whiteboard is covered with a giant unsolved boolean expression written in red marker.");
    Room computerStation = new Room("Computer Station", "A Code.org project is open on the monitor. The compiler window is filled with red error messages.");
    Room backTable = new Room("Back Table", "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order.");
    Room bookshelf = new Room("Bookshelf", "A dusty bookshelf is filled with old programming textbooks. One shelf is locked.");
    Room printerCorner = new Room("Printer Corner", "An old school printer hums loudly. Its screen flashes 'PAPER JAM'.");
    Room rubricBoard = new Room("Rubric Board", "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'.");
    Room classroomDoor = new Room("Classroom Door", "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'.");

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

    this.rooms.add(frontDesk);
    this.rooms.add(whiteboardWall);
    this.rooms.add(computerStation);
    this.rooms.add(backTable);
    this.rooms.add(bookshelf);
    this.rooms.add(printerCorner);
    this.rooms.add(rubricBoard);
    this.rooms.add(classroomDoor);

    frontDesk.addItem(new Tool("Truth Table", "An academic layout mapping inputs to logic evaluations.", "Whiteboard"));
    computerStation.addItem(new Tool("Rubber Duck", "A little yellow duck for debugging runtime faults.", "Computer"));
    backTable.addItem(new Tool("Index Cards", "Scattered index paper pieces outlining structural processes.", "Table"));

    this.player = new Player(frontDesk);
  }

  /**
   * Starts the user interaction scanner runtime loop.
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("--- Escape From Satija's Class ---");
    System.out.println(this.player.getCurrentRoom().getDescription(this));

    while (this.isRunning) {
      System.out.print("\n> ");
      if (!scanner.hasNextLine()) {
        break;
      }
      String rawInput = scanner.nextLine();
      this.processCommand(rawInput);
      this.checkEndConditions();
    }
    scanner.close();
  }

  /**
   * Decodes textual terminal instructions to perform mechanics routines.
   *
   * @param input the raw user command text from System.in
   */
  public void processCommand(String input) {
    String cleanInput = input.trim();
    if (cleanInput.isEmpty()) {
      return;
    }

    String lowerInput = cleanInput.toLowerCase();

    if (lowerInput.equals("quit")) {
      this.isRunning = false;
      System.out.println("Exiting puzzle playground.");
      return;
    }

    if (lowerInput.equals("look")) {
      Room current = this.player.getCurrentRoom();
      System.out.println(current.getDescription(this));
      System.out.print("Items here: ");
      if (current.getItems().isEmpty()) {
        System.out.println("None");
      } else {
        System.out.println(current.getItems());
      }
      this.turnCount++;
      this.processTurn();
      return;
    }

    if (lowerInput.equals("inventory") || lowerInput.equals("i")) {
      System.out.println("Your Inventory: " + this.player.getInventory());
      this.turnCount++;
      this.processTurn();
      return;
    }

    if (lowerInput.startsWith("go ")) {
      String direction = cleanInput.substring(3).trim();
      this.handleMovement(direction);
      return;
    }

    if (lowerInput.startsWith("take ")) {
      String targetItemName = cleanInput.substring(5).trim();
      this.handleTake(targetItemName);
      return;
    }

    if (lowerInput.startsWith("use ")) {
      this.handleUseInteraction(cleanInput);
      return;
    }

    System.out.println("Command not recognized. Try 'go [dir]', 'take [item]', 'use [item] on [obj]', 'look', or 'inventory'.");
  }

  private void handleMovement(String direction) {
    Room current = this.player.getCurrentRoom();
    String destinationRoomName = null;

    for (String exitPair : current.getExits()) {
      int colonIdx = exitPair.indexOf(":");
      if (colonIdx != -1) {
        String dirKey = exitPair.substring(0, colonIdx).trim();
        String destVal = exitPair.substring(colonIdx + 1).trim();
        if (dirKey.equalsIgnoreCase(direction)) {
          destinationRoomName = destVal;
          break;
        }
      }
    }

    if (destinationRoomName == null) {
      System.out.println("You cannot go that way.");
      return;
    }

    Room targetRoom = this.findRoom(destinationRoomName);
    if (targetRoom != null) {
      if (current.getName().equalsIgnoreCase("Front Desk")) {
        this.hasLeftFrontDesk = true;
      }

      this.player.setCurrentRoom(targetRoom);
      System.out.println(targetRoom.getDescription(this));

      if (targetRoom.getName().equalsIgnoreCase("Front Desk") && this.hasLeftFrontDesk && !this.hasEscaped) {
        this.isCaught = true;
      }

      this.turnCount++;
      this.processTurn();
    }
  }

  private void handleTake(String itemName) {
    Room current = this.player.getCurrentRoom();
    Item found = null;

    for (Item item : current.getItems()) {
      if (item.getName().equalsIgnoreCase(itemName)) {
        found = item;
        break;
      }
    }

    if (found == null) {
      System.out.println("That item is not here.");
      return;
    }

    if (this.player.addItem(found)) {
      current.removeItem(found);
      System.out.println("You pick up the " + found.getName() + ".");
      this.turnCount++;
      this.processTurn();
    }
  }

  private void handleUseInteraction(String fullCommand) {
    String content = fullCommand.substring(4).trim();
    String contentLower = content.toLowerCase();
    int onIndex = contentLower.indexOf(" on ");

    if (onIndex == -1) {
      System.out.println("Grammar syntax error. Use: 'use [item] on [object]'");
      return;
    }

    String itemName = content.substring(0, onIndex).trim();
    String objectName = content.substring(onIndex + 4).trim();

    Item item = this.player.findItem(itemName);
    if (item == null) {
      System.out.println("You do not possess that item.");
      return;
    }

    Room room = this.player.getCurrentRoom();

    if (itemName.equalsIgnoreCase("Truth Table") && objectName.equalsIgnoreCase("Whiteboard")) {
      if (room.getName().equalsIgnoreCase("Whiteboard Wall") && !this.isBooleanFixed) {
        this.isBooleanFixed = true;
        System.out.println("You simplify the boolean expression correctly. Satija silently nods in approval.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    if (itemName.equalsIgnoreCase("Rubber Duck") && objectName.equalsIgnoreCase("Computer")) {
      if (room.getName().equalsIgnoreCase("Computer Station") && this.isBooleanFixed && !this.isCodeDebugged) {
        this.isCodeDebugged = true;
        System.out.println("You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    if (itemName.equalsIgnoreCase("Index Cards") && objectName.equalsIgnoreCase("Table")) {
      if (room.getName().equalsIgnoreCase("Back Table") && this.isCodeDebugged && !this.isAlgorithmBuilt) {
        this.isAlgorithmBuilt = true;
        room.addItem(new KeyItem("Algorithm Card", "A thick cardboard slip tracking logic patterns.", "Bookshelf"));
        System.out.println("You arrange the algorithm steps into the correct order. A hidden compartment opens.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    if (itemName.equalsIgnoreCase("Algorithm Card") && objectName.equalsIgnoreCase("Bookshelf")) {
      if (room.getName().equalsIgnoreCase("Bookshelf") && this.isAlgorithmBuilt && !this.hasBookshelfUnlocked) {
        this.hasBookshelfUnlocked = true;
        this.player.removeItem(item);
        room.addItem(new Document("Java Manual", "The ultimate technical standard textbook layout.", "A complete specification guide."));
        System.out.println("The locked shelf clicks open, revealing the sacred Java Manual.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    if (itemName.equalsIgnoreCase("Java Manual") && objectName.equalsIgnoreCase("Printer")) {
      if (room.getName().equalsIgnoreCase("Printer Corner") && this.hasBookshelfUnlocked && !this.isSpecPrinted) {
        this.isSpecPrinted = true;
        this.player.removeItem(item);
        room.addItem(new Document("Printed Spec", "A crisp, freshly printed software architecture overview.", "Spec file documentation details."));
        System.out.println("The printer groans dramatically before printing your completed spec.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    if (itemName.equalsIgnoreCase("Printed Spec") && objectName.equalsIgnoreCase("Rubric")) {
      if (room.getName().equalsIgnoreCase("Rubric Board") && this.isSpecPrinted && !this.hasApprovalStamp) {
        this.hasApprovalStamp = true;
        this.player.removeItem(item);
        room.addItem(new KeyItem("Final Approval Stamp", "A heavy ink block with a shining green verification matrix.", "Door"));
        System.out.println("A giant green APPROVED stamp slams onto your spec.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    if (itemName.equalsIgnoreCase("Final Approval Stamp") && objectName.equalsIgnoreCase("Door")) {
      if (room.getName().equalsIgnoreCase("Classroom Door") && this.hasApprovalStamp && !this.hasEscaped) {
        this.hasEscaped = true;
        this.player.removeItem(item);
        System.out.println("Satija unlocks the classroom door. You survived the Logic Audit.");
        this.turnCount++;
        this.processTurn();
        return;
      }
    }

    System.out.println("Nothing meaningful happens. Prerequisites might be unfulfilled or target invalid.");
  }

  private void processTurn() {}

  private void checkEndConditions() {
    if (this.isCaught) {
      System.out.println("You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss.");
      this.isRunning = false;
    } else if (this.hasEscaped) {
      this.isRunning = false;
    }
  }

  public Room findRoom(String roomName) {
    for (Room r : this.rooms) {
      if (r.getName().equalsIgnoreCase(roomName)) {
        return r;
      }
    }
    return null;
  }

  public boolean isBooleanFixed() { return this.isBooleanFixed; }
  public boolean isCodeDebugged() { return this.isCodeDebugged; }
  public boolean isAlgorithmBuilt() { return this.isAlgorithmBuilt; }
  public boolean isHasBookshelfUnlocked() { return this.hasBookshelfUnlocked; }
  public boolean isSpecPrinted() { return this.isSpecPrinted; }
  public boolean isHasApprovalStamp() { return this.hasApprovalStamp; }
  public boolean isHasEscaped() { return this.hasEscaped; }
}