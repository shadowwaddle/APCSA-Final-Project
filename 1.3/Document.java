// --- FILE: Document.java ---
/**
 * Represents a readable text document or printed spec within the classroom.
 *
 * This class extends the base Item class and introduces a contents field.
 * It is used to track readable artifacts that the player can print, inspect,
 * or submit for validation during puzzles.
 */
public class Document extends Item {
  private String contents;

  /**
   * Constructs a Document with specific internal contents.
   *
   * @param name The name of the document.
   * @param description The detailed description of the document.
   * @param contents The readable text content of the document.
   */
  public Document(String name, String description, String contents) {
    super(name, description);
    this.contents = contents;
  }

  /**
   * Retrieves the raw textual contents of the document.
   *
   * @return The contents string.
   */
  public String getContents() {
    return contents;
  }
}