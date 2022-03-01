package sequencium;
import java.util.*;

/**
 * An interface for a Sequencium player to be implemented in order to play the
 * game.
 *
 * @author Michael Albert
 */
public class PAWNs326 implements Player {

  /**
   * Make a move as requested given a board. Your numbers are positive integers
   * on the board, your opponent's numbers are negative integers and vacant
   * places are 0's.
   * <p>
   * If the move returned is invalid, you will be deemed to have passed (i.e.,
   * done nothing).
   * <p>
   * In practice, the board you receive will only be a copy of the actual board,
   * so changing it directly is safe but has no effect on the game.
   *
   * @param board the current game board
   * @return your move as a three element array {r, c, v} intended to indicate
   * that you wish to make board[r][c] = v.
   */
 public int[] makeMove(int[][] board) {
    ArrayList<int[]> moves = new ArrayList<>();
    int rows = board.length;
    int cols = board[0].length;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (board[r][c] > 0) {
          for (int[] n : Utilities.neighbours(r, c, rows, cols)) {
            if (board[n[0]][n[1]] == 0) { // is space clear
              moves.add(new int[]{n[0], n[1], board[r][c] + 1}); // add move
            }
          }
        }
      }
    }
    if (moves.size() > 0) { // are there any moves
      // find cell with biggest value
      int big_rn = 0;
      int big_cn = 0;
      int big_score = 0;

      for (int[] n : moves) {
        int rn = n[0];
        int cn = n[1];
        int score = n[2];
        for (int[] o : Utilities.neighbours(rn, cn, rows, cols)) {
          if (board[o[0]][o[1]] >= big_score && board[n[0]][n[1]] == 0) { // is next to highest value square
            big_rn = rn;
            big_cn = cn;
            big_score = score;      
          }
        }
      }
      return new int[]{big_rn, big_cn, big_score};
    } else {
      return new int[0];
    }

  }

    /**
   * What is your name?
   *
   * @return Your name.
   */
  public String getName() {
    return "pawns326";
  }

}
