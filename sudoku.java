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

    public String to_string() {
        int width = this.board_size;
        StringBuilder builder = new StringBuilder();
        String div = this.divider();
        for (int i = 0; i < this.cell_size; i++) {
            builder.append(div);
            builder.append("\n");
            for (int j = 0; j < this.cell_size; j++) {
                builder.append(row(i * this.cell_size + j));
            }
        }
        builder.append(div);
        return builder.toString();
    }

    private String divider() {
        StringBuilder div_builder = new StringBuilder();
        div_builder.append("+");
        for(int i = 0; i < this.cell_size; i++) {
            for(int j = 0; j < this.cell_size * 2 + 1; j++) {
                div_builder.append("-");
            }
            div_builder.append("+");
        }
        // builder.append("\n");
        return div_builder.toString();
    }

    private String row(int row_index) {
        StringBuilder row_builder = new StringBuilder();
        // for (int i = 0; i < this.board_size; i++) {
            // row_builder.append(this.board_filled[row_index][i]);
            row_builder.append("| ");
            for(int i = 0; i < this.cell_size; i++) {
                for(int j = 0; j < this.cell_size; j++) {
                    row_builder.append(board_filled[row_index][j + (i * this.cell_size)]);
                    row_builder.append(" ");
                }
                row_builder.append("| ");
            }
            row_builder.append("\n");
        return row_builder.toString();
    }

    public static void main(String args[]) {
        int cell_size = 3;
        Sudoku sudoku = new Sudoku(3);
        sudoku.generate_filled();
        // System.out.println(sudoku.get_board_filled().toString());
        System.out.println(sudoku.to_string());

    }
}
