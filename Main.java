/**
 * Main entry point for Escape From Satija's Class.
 *
 * This class serves as the bootstrap component of the game architecture. It 
 * instantiates the core Game object and fires off the central execution loop.
 */
public class Main {
  /**
   * Main method to execute the program.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }
}