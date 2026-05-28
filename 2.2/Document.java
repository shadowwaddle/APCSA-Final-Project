/**
 * Represents a printable or readable textual asset in the classroom.
 *
 * This class extends the base Item class to add storage for custom content. It 
 * represents spec documents and manuals that can be combined with printer machinery.
 */
public class Document extends Item {
  private String contents;

  /**
   * Constructs a Document containing specialized textual information.
   *
   * @param name The unique identifier name of the document
   * @param description The descriptive text displayed to the player
   * @param contents The literal context contents of this physical page
   */
  public Document(String name, String description, String contents) {
    super(name, description);
    this.contents = contents;
  }

  /**
   * Retrieves the internal text data of the document asset.
   *
   * @return The literal text content payload
   */
  public String getContents() {
    return this.contents;
  }
}