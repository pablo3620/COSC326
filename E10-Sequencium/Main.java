import sequencium.*;

public class Main {
  public static void main(String[] args) {
    Game g = new Game(new PAWNs326c(), new PAWNs326d());
    g.makeVerbose();
    g.run();
    System.out.println(g.getLog());
  }
}
