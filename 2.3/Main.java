/**
 * Serves as the primary entry checkpoint initialization file.
 * Allocates execution parameters over runtime assets.
 * Sets the text adventure environment fully into motion.
 */
public class Main {
  /**
   * Initializes the engine runtime properties.
   *
   * @param args Standard command-line configuration parameter arrays
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }
}