package sudoku.Game.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sudoku {

    int N; // number of columns/rows.
    int SRN; // square root of N
    int K; // No. Of missing digits
    int[] board[];
    int[][] solvedBoard;

    // Constructor
    public Sudoku(int N, int K) {
        this.N = N;
        this.K = K;
        this.solvedBoard = new int[N][N];

        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        board = new int[N][N];
    }

    // Sudoku Generator
    public void createBoard() {
        // Fill the diagonal of SRN x SRN boardrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                solvedBoard[i][j] = board[i][j];
            }
        }

        // Remove Randomly K digits to make game
        removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN boardrices
    void fillDiagonal() {

        for (int i = 0; i < N; i = i + SRN) // for diagonal box, start coordinates->i==j
        {
            fillBox(i, i);
        }
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < SRN; i++) {
            for (int j = 0; j < SRN; j++) {
                if (board[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Fill a 3 x 3 boardrix.
    void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < SRN; i++) {
            for (int j = 0; j < SRN; j++) {
                do {
                    num = randomGenerator(N);
                } while (!unUsedInBox(row, col, num));

                board[row + i][col + j] = num;
            }
        }
    }

    // Random generator
    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i, int j, int num) {
        return (unUsedInRow(i, num)
                && unUsedInCol(j, num)
                && unUsedInBox(i - i % SRN, j - j % SRN, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i, int num) {
        for (int j = 0; j < N; j++) {
            if (board[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j, int num) {
        for (int i = 0; i < N; i++) {
            if (board[i][j] == num) {
                return false;
            }
        }
        return true;
    }

    // A recursive function to fill remaining 
    // boardrix
    boolean fillRemaining(int i, int j) {
        //  System.out.println(i+" "+j);
        if (j >= N && i < N - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= N && j >= N) {
            return true;
        }

        if (i < SRN) {
            if (j < SRN) {
                j = SRN;
            }
        } else if (i < N - SRN) {
            if (j == (int) (i / SRN) * SRN) {
                j = j + SRN;
            }
        } else {
            if (j == N - SRN) {
                i = i + 1;
                j = 0;
                if (i >= N) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= N; num++) {
            if (CheckIfSafe(i, j, num)) {
                board[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }

                board[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the K no. of digits to
    // complete game
    public void removeKDigits() {
        int count = K;
        while (count != 0) {
            int cellId = randomGenerator(N * N) - 1;

            // System.out.println(cellId);
            // extract coordinates i  and j
            int i = (cellId / N);
            int j = cellId % N;

            // System.out.println(i+" "+j);
            if (board[i][j] != 0) {
                count--;
                board[i][j] = 0;
            }
        }
    }

    // Print sudoku
    public void printSudoku() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Driver code
    public static void main(String[] args) {
        int N = 9, K = 20;
        Sudoku sudoku = new Sudoku(N, K);
        sudoku.createBoard();
        sudoku.printSudoku();
    }
}
