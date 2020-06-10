import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

class Sudoku {
    private int board_size;
    private int cell_size;
    private int[][] board_filled;
    private int[][] board_emptied;

    Sudoku(int cell_size) {
        this.cell_size = cell_size;
        this.board_size = cell_size * cell_size;
        this.board_filled = new int[board_size][board_size];
        this.board_emptied = new int[board_size][board_size];
        this.generate_filled();
        this.scramble_board();
        this.strike_board();
    }

    public int[][] get_board_filled() {
        return this.board_filled;
    }

    public int[][] get_board_emptied() {
        return this.board_emptied;
    }

    public int get_board_size() {
        return this.board_size;
    }

    public void generate_filled() {
        this.board_size = this.cell_size * this.cell_size;
        this.board_filled = new int[this.board_size][this.board_size];
        int k = 1,n = 1;

        for(int i = 0; i < this.board_size; i++) {
            k = n;

            for (int j = 0; j < this.board_size; j++) {
                if (k > this.board_size) {
                    k = 1;
                }

                this.board_filled[i][j] = k;
                k++;
            }

            n = k + this.cell_size;
            if (k == this.board_size + 1) {
                n = 1 + cell_size;
            }

            if ( n > this.board_size) {
                n = (n % this.board_size) + 1;
            }
        }
    }

    private void scramble_board() {
        Random random = new Random();
        int max_iterations = 10;
        this.swap_rows(get_random(max_iterations));
        this.swap_cols(get_random(max_iterations));
    }

    // Swap two rows from a block of cells and repeat iterations times.
    private void swap_rows(int iterations) {
        Random random = new Random();
        int k1, k2;
        for (int j = 0; j < iterations; j++) {
            int max = this.cell_size - 1;
            int min = 0;

            for(int i = 0; i < this.cell_size; i++) {
                k1 = get_random(max - min + 1) + min;

                do {
                    k2 = get_random(max - min + 1) + min;
                } while(k1 == k2);

                max += this.cell_size;
                min += this.cell_size;
                int temp;

                for (int k = 0; k < this.board_size; k++) {
                    temp = board_filled[k1][k];
                    board_filled[k1][k] = board_filled[k2][k];
                    board_filled[k2][k] = temp;
                }
            }
        }
    }

    private void swap_cols(int iterations) {
        int k1, k2;

        for (int j = 0; j < iterations; j++) {
            int max = this.cell_size - 1;
            int min = 0;

            for(int i = 0; i < this.cell_size; i++) {
                k1 = get_random(max - min + 1) + min;
                do {
                    k2 = get_random(max - min + 1) + min;
                } while(k1 == k2);

                max += this.cell_size;
                min += this.cell_size;
                int temp; //swap location

                for (int k = 0; k < this.board_size; k++) {
                    temp = board_filled[k][k1];
                    board_filled[k][k1] = board_filled[k][k2];
                    board_filled[k][k2] = temp;
                }
            }
        }
    }

    public String to_string() {
        int width = this.board_size;
        StringBuilder builder = new StringBuilder();
        String bar = divider();
        for (int i = 0; i < this.cell_size; i++) {
            builder.append(bar);
            builder.append("\n");
            for (int j = 0; j < this.cell_size; j++) {
                builder.append(this.row(i *this.cell_size + j));
            }
        }
        builder.append(bar);
        return builder.toString();
    }

    private String divider() {
        StringBuilder bar_builder = new StringBuilder();
        int number_width = (int) Math.ceil(Math.log10(this.board_size));

        bar_builder.append("+");
        for(int i = 0; i < this.cell_size; i++) {
            for(int j = 0; j < this.cell_size * (number_width + 1) + 1; j++) {
                bar_builder.append("-");
            }
            bar_builder.append("+");
        }
        return bar_builder.toString();
    }

    private String row(int row_index) {
        StringBuilder row_builder = new StringBuilder();
        int max_digits = (this.board_size * this.board_size);
        String bound = Integer.toString(max_digits);
        max_digits = bound.length();

        row_builder.append("| ");
        for(int i = 0; i < this.cell_size; i++) {
            for(int j = 0; j < this.cell_size; j++) {
                int num = this.board_filled[row_index][i * this.cell_size + j];
                int num_digits = Integer.toString(num).length();
                int needed_digits = max_digits - num_digits;
                for (int k = 1; k < needed_digits; k++) {
                    row_builder.append(" ");
                }
                row_builder.append(num);
                row_builder.append(" ");
            }
            row_builder.append("| ");
        }
        row_builder.append("\n");
        return row_builder.toString();
    }

    private void strike_board() {
        for(int i = 0; i < this.board_size; i++) {
            for(int j = 0; j < this.board_size; j++) {
                this.board_emptied[i][j] = this.board_filled[i][j];
            }
        }

    }

    public static boolean safety_check(int[][] board, int size) {
        for (int i = 1; i <= size; i++) {
            // Check that each row contains each number
            for (int j = 0; j < size; j++) {
                if (!Arrays.asList(board[j]).contains(i)) {
                    return false;
                }
            }

            // Check that each col contains each numbers
            for (int j = 0; j < size; j++) {
                int[] cur_col = new int[size];
                for (int k = 0; k < size; k++) {
                    cur_col[k] = board[j][k];
                }
                if (!Arrays.asList(cur_col).contains(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // https://www.geeksforgeeks.org/sudoku-backtracking-7/
    public static boolean is_safe(int[][] board, int row, int col, int num) {
        // Check row
        for (int d = 0; d < board.length; d++) {
            if (board[row][d] == num) {
                return false;
            }
        }
        // column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }
        // Check box
        int sqrt = (int)Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;
        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++) {
                if (board[r][d] == num) {
                    return false;
                }
            }
        }
        // Passed all tests
        return true;
    }

    public static boolean solve(int[][] board, int n) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    // we still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // no empty space left
        if (isEmpty) {
            return true;
        }

        // else for each-row backtrack
        for (int num = 1; num <= n; num++) {
            if (is_safe(board, row, col, num)) {
                board[row][col] = num;
                if (solve(board, n)) {
                    // print(board, n);
                    return true;
                }
                else {
                    // replace it
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    private int get_random(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public static void main(String args[]) {
        int cell_size = 3;
        boolean safe = true;
        Sudoku sudoku = new Sudoku(cell_size);
        if (Sudoku.safety_check(sudoku.get_board_filled(), sudoku.get_board_size())) {
            safe = false;
        }
        System.out.println(sudoku.to_string());
    }
}
