package sudoku;

public class Sudoku {

  static class RowCol {
    int row;
    int col;

    RowCol(int r, int c) {
      row = r;
      col = c;
    }
  }

  static int numSquaresFilled;
  static int[][] board = new int[9][9];

  static void printBoard() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        System.out.print(" " + (board[i][j] == 0 ? " " : board[i][j]) + " ");
        if (j % 3 == 2 && j < 8)
          System.out.print("|");
      }
      System.out.println();
      if (i % 3 == 2 && i < 8)
        System.out.println("---------|---------|---------");
    }
    System.out.println();
  }

  static boolean isEntireBoardValid() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (!isBoardValid(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  static boolean isRowValid(int row) {
    int[] count = new int[9];
    for (int col = 0; col < 9; col++) {
      int n = board[row][col] - 1;
      if (n == -1)
        continue;
      count[n]++;
      if (count[n] > 1)
        return false;
    }
    return true;
  }

  static boolean isColValid(int col) {
    int[] count = new int[9];
    for (int row = 0; row < 9; row++) {
      int n = board[row][col] - 1;
      if (n == -1)
        continue;
      count[n]++;
      if (count[n] > 1)
        return false;
    }
    return true;
  }

  static boolean isSquareValid(int row, int col) {
    int r = (row / 3) * 3;
    int c = (col / 3) * 3;
    int[] count = new int[9];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int n = board[r + i][c + j] - 1;
        if (n == -1)
          continue;
        count[n]++;
        if (count[n] > 1)
          return false;
      }
    }
    return true;
  }

  static boolean isBoardValid(int row, int col) {
    return (isRowValid(row) && isColValid(col) && isSquareValid(row, col));
  }

  static RowCol getOpenSpaceFirstFound() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (board[i][j] == 0) {
          return new RowCol(i, j);
        }
      }
    }
    return new RowCol(0, 0);
  }

  static RowCol getOpenSpaceMostConstrained() {
    int r = 0, c = 0, max = 0;
    int[] rowCounts = new int[9];
    int[] colCounts = new int[9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (board[i][j] != 0)
          rowCounts[i]++;
        if (board[j][i] != 0)
          colCounts[i]++;
      }
    }

    int[][] squareCounts = new int[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int count = 0;
        for (int m = 0; m < 3; m++) {
          for (int n = 0; n < 3; n++) {
            if (board[(i * 3) + m][(j * 3) + n] != 0)
              count++;
          }
        }
        squareCounts[i][j] = count;
      }
    }

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (board[i][j] == 0) {
          if (rowCounts[i] > max) {
            max = rowCounts[i];
            r = i;
            c = j;
          }
          if (colCounts[j] > max) {
            max = rowCounts[j];
            r = i;
            c = j;
          }
        }
      }
    }
    return new RowCol(r, c);
  }

  static boolean solve() {
    if (81 == numSquaresFilled) {
      return true;
    }

    boolean solveSmart = true;
    RowCol rc = solveSmart ? getOpenSpaceMostConstrained() : getOpenSpaceFirstFound();
    int r = rc.row;
    int c = rc.col;
    for (int i = 1; i <= 9; i++) {
      numSquaresFilled++;
      board[r][c] = i;
      if (isBoardValid(r, c)) {
        if (solve()) {
          return true;
        }
      }
      board[r][c] = 0;
      numSquaresFilled--;
    }
    return false;
  }

  public static void main(String[] args) {

    // initialize board to a HARD puzzle
    board[1][4] = 3;
    board[1][6] = 6;
    board[2][2] = 9;
    board[2][4] = 7;
    board[2][5] = 8;
    board[2][7] = 3;
    board[3][0] = 3;
    board[3][2] = 5;
    board[3][5] = 4;
    board[4][2] = 2;
    board[4][5] = 6;
    board[5][0] = 6;
    board[5][1] = 8;
    board[5][2] = 7;
    board[5][7] = 2;
    board[6][6] = 3;
    board[6][7] = 5;
    board[6][8] = 4;
    board[7][7] = 6;
    board[8][2] = 4;
    numSquaresFilled = 20;

    printBoard();
    solve();
    printBoard();
  }
}