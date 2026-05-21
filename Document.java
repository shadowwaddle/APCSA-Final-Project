/**
 * A readable item containing custom documentation text.
 *
 * This subclass expands Item by storing readable structural metadata, allowing 
 * academic resources or printed artifacts to hold text files or parameters.
 */
public class Document extends Item {
  private String contents;

  /**
   * Constructs a readable Document instance.
   *
   * @param name the precise textual title of the document
   * @param description the aesthetic summary text
   * @param contents the hidden internal text values of the document
   */
  public Document(String name, String description, String contents) {
    super(name, description);
    this.contents = contents;
  }

  /**
   * Exposes the text contents written within this document object.
   *
   * @return the comprehensive string context data
   */
  public String getContents() {
    return this.contents;
  }
}