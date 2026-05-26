// --- FILE: Document.java ---
/**
 * Represents text-heavy written records or printable materials in the classroom.
 *
 * This subclass adds a contents field to support printable data logs or 
 * regulatory specs required for academic evaluation in the narrative path.
 * Interacts with the programmatic mechanics inside Game to handle manual verification.
 */
public class Document extends Item {
  private String contents;

  /**
   * Constructs a Document item with embedded text records.
   *
   * @param name the name of the document
   * @param description the description of the document
   * @param contents the full text or specification contents of the document
   */
  public Document(String name, String description, String contents) {
    super(name, description);
    this.contents = contents;
  }

  /**
   * Retrieves the inner textual data or specifications of the document.
   *
   * @return the contents string
   */
  public String getContents() {
    return contents;
  }
}