package sequencium;

import java.util.*;
import java.lang.Math;

/**
 * An interface for a Sequencium player to be implemented in order to play the
 * game.
 *
 * @author Michael Albert
 */
public class PAWNs326c implements Player {
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
   *         that you wish to make board[r][c] = v.
   */


  public int[] makeMove(int[][] board) {
    ArrayList<int[]> moves = new ArrayList<>();
    ArrayList<int[]> moves2 = new ArrayList<>();

    int cols = board.length;
    int rows = board[0].length;
    boolean reversed = false;
    // Makes line through diagonal

    if (board[0][0] != 1) {
      reversed = true;
    }
    if (reversed) {
      int[][] new_board = new int[rows][cols];
      for(int r = 0; r < rows; r++) {
        for(int c = 0; c < cols; c++) {
          //rotate 180 degrees so the opponent has same view of board
          new_board[r][c] = board[rows - 1 - r][cols - 1 - c];
        }
      }
      board = new_board;
    }

    for (int i = 0; i < Math.min(rows,cols); i++) {
      if (board[i][i] == 0) { 
        int a_choice = board[i-1][i-1];
        if (reversed) {
          return new int[] { rows - 1 - i, cols - 1 - i, a_choice+1 };
        } else {
          return new int[] { i, i, a_choice+1 };
        }
      }
    }
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (board[r][c] > 0) {
          for (int[] n : Utilities.neighbours(r, c, rows, cols)) {
            if (board[n[0]][n[1]] == 0) { // is space clear
              moves.add(new int[] { n[0], n[1], board[r][c] + 1 }); // add move
            }
          }
        }
      }
    }
    if (reversed) {
      Collections.reverse(moves);
    }
  
    if (moves.size() > 0) { // are there any moves
      // find cell with biggest value
      int big_rn = 0;
      int big_cn = 0;
      int big_score = 0;
      int diff = Math.max(cols,rows);
      int small_diff = Math.max(cols,rows);

//      Collections.shuffle(moves); //put some unpredictability in there
      for (int[] n : moves) {
        int rn = n[0];
        int cn = n[1];
        int score = n[2];
        for (int[] o : Utilities.neighbours(rn, cn, rows, cols)) {
          if (board[o[0]][o[1]] >= big_score && board[n[0]][n[1]] == 0) { // is next to highest value square 
            if (board[o[0]][o[1]] > big_score) { // new max
              moves2.clear(); // empty
            }
            moves2.add(new int[] { rn, cn, score});
            big_score = score;
          }
        }
      }
      if (moves2.size() < 2) {
        int[] a_move = moves2.get(0);
        big_rn = a_move[0];
        big_cn = a_move[1];
        big_score = a_move[2];
      } else {  
        for (int[] n : moves2) {
          int rn = n[0];
          int cn = n[1];
          int score = n[2];
          diff = Math.abs(rn - cn);
          if (diff <= small_diff) {
            big_rn = rn;
            big_cn = cn;
            big_score = score;
          }
        }
      }

      if (reversed) {
        return new int[] { rows - 1 - big_rn, cols - 1 - big_cn, big_score };
      } else {
        return new int[] { big_rn, big_cn, big_score };
      }
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
    return "pawns326c";
  }

}
