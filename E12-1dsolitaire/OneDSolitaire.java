import java.util.HashSet;

public class OneDSolitaire {
    public static void main(String[] args) {
        int numPeg = Integer.parseInt(args[0]);
        // initial starting string with one peg
        String startString = "x";
        // set up the initial generation
        HashSet<String> currentGeneration = new HashSet<>();
        currentGeneration.add(startString);
        for (int i = 1; i < numPeg; i++) {
            HashSet<String> nextGeneration = new HashSet<>();
            for (String s : currentGeneration) {
                nextGeneration.addAll(children(s));
            }
            currentGeneration = nextGeneration;
        }
        for (String s : currentGeneration) {
            System.out.println(s);
        }
    }

    public static HashSet<String> children(String s) {
        HashSet<String> result = new HashSet<>();
        // Create a stringbuilder copy of s with oo added at the front
        StringBuilder board = new StringBuilder(s);
        board.insert(0, "oo");
        board.append("oo");

        // and end
        // Scan it for oox or xoo
        int fromIndex = 0;
        while (board.indexOf("oox", fromIndex) != -1) {
            // Each time you find one build a new Stringbuilder where that's replaced
            // by xxo or oxx respectively
            fromIndex = board.indexOf("oox", fromIndex);
            StringBuilder nextBoard = new StringBuilder(board);
            nextBoard.replace(fromIndex, fromIndex + 3, "xxo");

            // Trim leading and trailing o's off that
            int firstXPos = nextBoard.indexOf("x");
            int lastXPos = nextBoard.lastIndexOf("x");
            nextBoard.delete(lastXPos + 1, nextBoard.length() + 1);
            nextBoard.delete(0, firstXPos);

            // Add that to 'result'
            // (actually, compare 'that' and its reverse and add the smaller)
            StringBuilder rnextBoard = new StringBuilder(nextBoard);
            rnextBoard.reverse();
            if (nextBoard.compareTo(rnextBoard) < 0) {
                result.add(nextBoard.toString());
            } else {
                result.add(rnextBoard.toString());
            }
            fromIndex++;
        }
        fromIndex = 0;
        while (board.indexOf("xoo", fromIndex) != -1) {
            // Each time you find one build a new Stringbuilder where that's replaced
            // by xxo or oxx respectively
            fromIndex = board.indexOf("xoo", fromIndex);
            StringBuilder nextBoard = new StringBuilder(board);
            nextBoard.replace(fromIndex, fromIndex + 3, "oxx");

            // Trim leading and trailing o's off that
            int firstXPos = nextBoard.indexOf("x");
            int lastXPos = nextBoard.lastIndexOf("x");
            nextBoard.delete(lastXPos + 1, nextBoard.length() + 1);
            nextBoard.delete(0, firstXPos);

            // Add that to 'result'
            // (actually, compare 'that' and its reverse and add the smaller)
            StringBuilder rnextBoard = new StringBuilder(nextBoard);
            rnextBoard.reverse();
            if (nextBoard.compareTo(rnextBoard) < 0) {
                result.add(nextBoard.toString());
            } else {
                result.add(rnextBoard.toString());
            }
            fromIndex++;
        }

        return result;
    }
}
