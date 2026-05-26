/**
 * Main serves as the entry system bootstrap vector execution module.
 *
 * This wrapper class triggers execution pipelines by instantiating the core Game engine 
 * component layout architecture.
 */
public class Main {

  /**
   * Launches structural game parsing execution runs.
   *
   * @param args standard execution telemetry properties arguments array
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }
}
