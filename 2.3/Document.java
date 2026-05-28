/**
 * Represents written text materials or files within the environment.
 * Inherits from Item and encapsulates inner context messages.
 * Used for storing printable materials or instructional logs.
 */
public class Document extends Item {
  private String contents;

  /**
   * Constructs a Document with custom text payload features.
   *
   * @param name The name of the document
   * @param description The environmental description of the document
   * @param contents The internal data text stored inside the document
   */
  public Document(String name, String description, String contents) {
    super(name, description);
    this.contents = contents;
  }

  /**
   * Retrieves the raw internal reading material text.
   *
   * @return The underlying content message string
   */
  public String getContents() {
    return this.contents;
  }
}