class Sudoku {
    private int board_size;
    private int cell_size;
    private int[][] board;

    Sudoku(int cell_size) {
        this.cell_size = cell_size;
        this.board_size = cell_size * cell_size;
        this.board = new int[board_size][board_size];
    }

    public void generate_filled() {
        this.board_size = this.cell_size * this.cell_size;
        this.board = new int[this.board_size][this.board_size];
        int k = 1,n = 1;
        for(int i = 0; i < this.board_size; i++) {
            k = n;
            for (int j = 0; j < this.board_size; j++) {
                if (k <= this.board_size) {
                    this.board[i][j] = k;
                    k++;
                }
                else {
                    k = 1;
                    this.board[i][j] = k;
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
        for (int i = 0; i < cell_size; i++) {
            builder.append(div);
            builder.append("\n");
            for (int j = 0; j < cell_size; j++) {
                builder.append(row(i + j));
            }
        }
        builder.append(div);
        return builder.toString();
    }

    private String divider() {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for(int i = 0; i < this.cell_size; i++) {
            for(int j = 0; j < this.cell_size * 2 + 1; j++) {
                builder.append("-");
            }
            builder.append("+");
        }
        // builder.append("\n");
        return builder.toString();
    }

    private String row(int row_index) {
        StringBuilder builder = new StringBuilder();
        builder.append("| ");
        for(int i = 0; i < this.cell_size; i++) {
            for(int j = 0; j < this.cell_size; j++) {
                builder.append(board[row_index][j + i]);
                builder.append(" ");
            }
            builder.append("| ");
        }
        builder.append("\n");
        return builder.toString();
    }

    public static void main(String args[]) {
        int cell_size = 3;
        Sudoku sudoku = new Sudoku(3);
        sudoku.generate_filled();
        System.out.println(sudoku.to_string());

    }
}
