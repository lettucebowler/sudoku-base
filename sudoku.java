import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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
    }

    public int[][] get_board_filled() {
        return this.board_filled;
    }

    public void generate_filled() {
        // System.out.println(this.cell_size);
        this.board_size = this.cell_size * this.cell_size;
        this.board_filled = new int[this.board_size][this.board_size];
        int k = 1,n = 1;
        for(int i = 0; i < this.board_size; i++) {
            k = n;
            for (int j = 0; j < this.board_size; j++) {
                if (k <= this.board_size) {
                    this.board_filled[i][j] = k;
                    k++;
                }
                else {
                    k = 1;
                    this.board_filled[i][j] = k;
                    k++;
                }
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
        this.permute_rows(random.nextInt(max_iterations));
        this.permute_cols(random.nextInt(max_iterations));
        this.swap_rows(random.nextInt(max_iterations));
        this.swap_cols(random.nextInt(max_iterations));
    }

    private void permute_rows(int iterations) {
        Random random = new Random();
        int[] temp;
        for (int i = 0; i < iterations; i++) {
            // Do something
        }
    }

    private void permute_cols(int iterations) {
        for (int i = 0; i < iterations; i++) {
            // Do something
        }
    }

    private void swap_rows(int iterations) {
        for (int i = 0; i < iterations; i++) {
            // Do something
        }
    }

    private void swap_cols(int iterations) {
        for (int i = 0; i < iterations; i++) {
            // Do something
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
                builder.append(this.row(i * this.cell_size + j));
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
                int num = this.board_filled[row_index][i + j];
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

    public static void main(String args[]) {
        int cell_size = 3;
        Sudoku sudoku = new Sudoku(cell_size);
        System.out.println(sudoku.to_string());

    }
}
