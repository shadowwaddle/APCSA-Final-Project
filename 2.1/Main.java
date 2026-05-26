// --- FILE: Main.java ---
/**
 * Serves as the programmatic entry point initializing the interactive runtime system.
 *
 * This class establishes execution sequences required to start the text adventure simulation.
 * Relates directly to Game by instantiating it and calling its start loop context.
 */
public class Main {
  /**
   * Main driver method running standard system runtime setup initialization.
   *
   * @param args runtime string inputs passed from the command line interface terminal execution
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }
}