// --- FILE: Main.java ---
/**
 * Application entrance catalyst bootstrap initializing game engines.
 *
 * This standard main execution sequence constructs the Game system architecture
 * and signals operational status loops to initiate.
 */
public class Main {
  /**
   * Triggers the structural text adventure application engine.
   *
   * @param args Standard array arguments parameter configurations.
   */
  public static void main(String[] args) {
    Game targetGame = new Game();
    targetGame.start();
  }
}