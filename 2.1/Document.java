/**
 * Document represents a text-heavy, readable or printable item subclass.
 *
 * It models classroom materials containing textual content, facilitating the creation, 
 * modification, or transfer of specifications and reference materials throughout the game.
 */
public class Document extends Item {
  private String contents;

  /**
   * Constructs a Document item with specified metadata and internal text.
   *
   * @param name the distinct identifying name of the document
   * @param description a detailed description of the document
   * @param contents the raw text contents embedded within the document
   */
  public Document(String name, String description, String contents) {
    super(name, description);
    this.contents = contents;
  }

  /**
   * Retrieves the internal text data stored within the document.
   *
   * @return the literal text contents string
   */
  public String getContents() {
    return this.contents;
  }
}
