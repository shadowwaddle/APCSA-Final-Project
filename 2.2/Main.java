/**
 * Serves as the primary entry point for launching the application runtime layout.
 *
 * This class instantiates the main Game engine control framework and fires the initial 
 * startup sequential operational loops.
 */
public class Main {

  /**
   * Standard executable method baseline initializing core program parameters.
   *
   * @param args Standard terminal sequence execution string variables passed via command line
   */
  public static void main(String[] args) {
    Game engine = new Game();
    engine.start();
  }
}