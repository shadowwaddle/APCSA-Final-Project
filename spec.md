Add your changes to spec.md
# Escape From Satija’s Class

**Group Members:**
- Preston
- Ryan
- Ryley
- Kamal

---

# I. Global Game State

- `isBooleanFixed`
  - Default (`false`)
  - Becomes `true` when the player successfully uses the Truth Table on the Whiteboard in the Whiteboard Wall room.

- `isCodeDebugged`
  - Default (`false`)
  - Becomes `true` when the player successfully uses the Rubber Duck on the Computer in the Computer Station room.

- `isAlgorithmBuilt`
  - Default (`false`)
  - Becomes `true` when the player successfully uses the Index Cards on the Table in the Back Table room.

- `hasBookshelfUnlocked`
  - Default (`false`)
  - Becomes `true` when the player successfully uses the Algorithm Card on the Bookshelf in the Bookshelf room.

- `isSpecPrinted`
  - Default (`false`)
  - Becomes `true` when the player successfully uses the Java Manual on the Printer in the Printer Corner room.

- `hasApprovalStamp`
  - Default (`false`)
  - Becomes `true` when the player successfully uses the Printed Spec on the Rubric in the Rubric Board room.

- `isCaught`
  - Default (`false`)
  - Loss Condition.
  - Becomes `true` when the player re-enters the Front Desk room after leaving it once.

- `hasLeftFrontDesk`
  - Default (`false`)
  - Becomes `true` the first time the player leaves the Front Desk room.

- `hasEscaped`
  - Default (`false`)
  - Win Condition.
  - Becomes `true` when the player successfully uses the Final Approval Stamp on the Door in the Classroom Door room.

---

# II. Class Hierarchy

- **Base Class:** `Item`
  - Fields:
    - `String name`
    - `String description`

- **Subclass:** `KeyItem`
  - Additional Field:
    - `String unlockTarget`
  - Purpose:
    - Used to unlock objects or complete major progression puzzles.

- **Subclass:** `Tool`
  - Additional Field:
    - `String useTarget`
  - Purpose:
    - Used to solve classroom tasks or interact with room objects.

- **Subclass:** `Document`
  - Additional Field:
    - `String contents`
  - Purpose:
    - Represents readable or printable classroom documents.

---

# III. Room Definitions

### Room 1: Front Desk

- **Description:** "The AP CSA classroom is quiet after school. Satija stands near the front desk holding a clipboard. The classroom door behind her is locked."

- **Items:** Truth Table (Tool)

- **Room Objects:** Satija Desk, Classroom Door

- **Exits:**
  - East to Whiteboard Wall | **Condition:** None
  - South to Classroom Door | **Condition:** None

---

### Room 2: Whiteboard Wall

- **Description (isBooleanFixed = false):** "The whiteboard is covered with a giant unsolved boolean expression written in red marker."

- **Description (isBooleanFixed = true):** "The boolean expression has been simplified correctly. Someone wrote 'DeMorgan was here' in the corner."

- **Items:** None

- **Room Objects:** Whiteboard

- **Exits:**
  - West to Front Desk | **Condition:** None
  - East to Computer Station | **Condition:** None
  - South to Rubric Board | **Condition:** None


---

### Room 3: Computer Station

- **Description (isCodeDebugged = false):** "A Code.org project is open on the monitor. The compiler window is filled with red error messages."

- **Description (isCodeDebugged = true):** "The Java program compiles successfully. The monitor proudly displays '0 ERRORS'."

- **Items:** Rubber Duck (Tool)

- **Room Objects:** Computer

- **Exits:**
  - West to Whiteboard Wall | **Condition:** None
  - South to Back Table | **Condition:** None

---

### Room 4: Back Table

- **Description (isAlgorithmBuilt = false):** "A pile of shuffled index cards sits on the table. The steps of the algorithm are completely out of order."

- **Description (isAlgorithmBuilt = true):** "The algorithm cards are neatly organized. A hidden compartment inside the table has opened."

- **Items:** Index Cards (Tool)

- **Conditional Items:** Algorithm Card (KeyItem, unlocks "Bookshelf")

- **Room Objects:** Table

- **Exits:**
  - North to Computer Station | **Condition:** None
  - West to Rubric Board | **Condition:** None
  - East to Bookshelf | **Condition:** None

---

### Room 5: Bookshelf

- **Description (hasBookshelfUnlocked = false):** "A dusty bookshelf is filled with old programming textbooks. One shelf is locked."

- **Description (hasBookshelfUnlocked = true):** "The locked shelf is now open."

- **Items:** None

- **Conditional Items:** Java Manual (Document)

- **Room Objects:** Bookshelf

- **Exits:**
  - North to Back Table | **Condition:** None
  - West to Printer Corner | **Condition:** None

---

### Room 6: Printer Corner

- **Description (isSpecPrinted = false):** "An old school printer hums loudly. Its screen flashes 'PAPER JAM'."

- **Description (isSpecPrinted = true):** "The printer finally rests quietly after printing a perfectly formatted spec."

- **Items:** None

- **Conditional Items:** Printed Spec (Document)

- **Room Objects:** Printer

- **Exits:**
  - East to Bookshelf | **Condition:** None
  - North to Rubric Board | **Condition:** None

---

### Room 7: Rubric Board

- **Description (hasApprovalStamp = false):** "A giant grading rubric is pinned to the wall. Every category says 'Needs More Detail'."

- **Description (hasApprovalStamp = true):** "A bright green APPROVED stamp now covers the rubric."

- **Items:** None

- **Conditional Items:** Final Approval Stamp (KeyItem, unlocks "Door")

- **Room Objects:** Rubric

- **Exits:**
  - West to Classroom Door | **Condition:** None
  - North to Whiteboard Wall | **Condition:** None
  - East to Back Table | **Condition:** None
  - South to Printer Corner | **Condition:** None


---

### Room 8: Classroom Door

- **Description (hasEscaped = false):** "The classroom door is locked. A scanner next to the handle reads 'FINAL APPROVAL REQUIRED'."

- **Description (hasEscaped = true):** "The classroom door swings open. Freedom at last."

- **Items:** None

- **Room Objects:** Door

- **Exits:**
  - North to Front Desk | **Condition:** None
  - East to Rubric Board | **Condition:** None


---

# IV. Interaction Logic

### Boolean Puzzle

- **Action:** "use truth table on whiteboard"

- **Location:** Whiteboard Wall

- **Prerequisites:**
  - Player possesses Truth Table
  - `isBooleanFixed == false`

- **Effects:**
  - Set `isBooleanFixed = true`
  - Print: "You simplify the boolean expression correctly. Satija silently nods in approval."

---

### Debug Puzzle

- **Action:** "use rubber duck on computer"

- **Location:** Computer Station

- **Prerequisites:**
  - Player possesses Rubber Duck
  - `isBooleanFixed == true`
  - `isCodeDebugged == false`

- **Effects:**
  - Set `isCodeDebugged = true`
  - Print: "You explain the code out loud to the rubber duck. Suddenly, the bug becomes obvious."

---

### Algorithm Puzzle

- **Action:** "use index cards on table"

- **Location:** Back Table

- **Prerequisites:**
  - Player possesses Index Cards
  - `isCodeDebugged == true`
  - `isAlgorithmBuilt == false`

- **Effects:**
  - Set `isAlgorithmBuilt = true`
  - Algorithm Card becomes available in the room
  - Print: "You arrange the algorithm steps into the correct order. A hidden compartment opens."

---

### Unlock Bookshelf

- **Action:** "use algorithm card on bookshelf"

- **Location:** Bookshelf

- **Prerequisites:**
  - Player possesses Algorithm Card
  - `isAlgorithmBuilt == true`
  - `hasBookshelfUnlocked == false`

- **Effects:**
  - Set `hasBookshelfUnlocked = true`
  - Java Manual becomes available in the room
  - Print: "The locked shelf clicks open, revealing the sacred Java Manual."

---

### Print Spec

- **Action:** "use java manual on printer"

- **Location:** Printer Corner

- **Prerequisites:**
  - Player possesses Java Manual
  - `hasBookshelfUnlocked == true`
  - `isSpecPrinted == false`

- **Effects:**
  - Set `isSpecPrinted = true`
  - Printed Spec becomes available in the room
  - Print: "The printer groans dramatically before printing your completed spec."

---

### Approval Puzzle

- **Action:** "use printed spec on rubric"

- **Location:** Rubric Board

- **Prerequisites:**
  - Player possesses Printed Spec
  - `isSpecPrinted == true`
  - `hasApprovalStamp == false`

- **Effects:**
  - Set `hasApprovalStamp = true`
  - Final Approval Stamp becomes available in the room
  - Print: "A giant green APPROVED stamp slams onto your spec."

---

### Satija Desk Trap

- **Trigger:** Player enters Front Desk after leaving it once

- **Location:** Front Desk

- **Prerequisites:**
  - `hasLeftFrontDesk == true`
  - `hasEscaped == false`

- **Effects:**
  - Set `isCaught = true`
  - Print: "You return to Satija's desk and interrupt her lecture. Satija slowly looks up from her clipboard. Instant loss."
  - End the game with a loss

---

### Escape Sequence

- **Action:** "use final approval stamp on door"

- **Location:** Classroom Door

- **Prerequisites:**
  - Player possesses Final Approval Stamp
  - `hasApprovalStamp == true`
  - `hasEscaped == false`

- **Effects:**
  - Set `hasEscaped = true`
  - Print: "Satija unlocks the classroom door. You survived the Logic Audit."

---

# V. Critical Path

1. Start in Front Desk
2. Take Truth Table
3. Move to Whiteboard Wall
4. Use Truth Table on Whiteboard
5. Move to Computer Station
6. Take Rubber Duck
7. Use Rubber Duck on Computer
8. Move to Back Table
9. Take Index Cards
10. Use Index Cards on Table
11. Take Algorithm Card
12. Move to Bookshelf
13. Use Algorithm Card on Bookshelf
14. Take Java Manual
15. Move to Printer Corner
16. Use Java Manual on Printer
17. Take Printed Spec
18. Move to Rubric Board
19. Use Printed Spec on Rubric
20. Take Final Approval Stamp
21. Move to Classroom Door
22. Use Final Approval Stamp on Door
23. Escape the classroom

---

# VI. Item Placement

| Item | Type | Initial Availability | Room | Used On |
|---|---|---|---|---|
| Truth Table | Tool | Available at start | Front Desk | Whiteboard |
| Rubber Duck | Tool | Available at start | Computer Station | Computer |
| Index Cards | Tool | Available at start | Back Table | Table |
| Algorithm Card | KeyItem | Appears after `isAlgorithmBuilt == true` | Back Table | Bookshelf |
| Java Manual | Document | Appears after `hasBookshelfUnlocked == true` | Bookshelf | Printer |
| Printed Spec | Document | Appears after `isSpecPrinted == true` | Printer Corner | Rubric |
| Final Approval Stamp | KeyItem | Appears after `hasApprovalStamp == true` | Rubric Board | Door |

---

# VII. Turn Mechanics

### NPCs

- Satija remains at the Front Desk for the entire game.

---

### Room Re-entry Trap

- When the player leaves Front Desk for the first time, set `hasLeftFrontDesk = true`.
- If the player later enters Front Desk again while `hasLeftFrontDesk == true`, set `isCaught = true`.
- Immediately end the game with a loss.
- Starting in Front Desk does not trigger the trap.

---

### Inventory Limit

- Maximum inventory size: 6 items.

- If the player attempts to pick up an item while inventory is full, print:
  - "Your backpack is full. Drop something first."
