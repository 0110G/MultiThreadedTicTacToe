public class Board {
    private CellState[][] state;
    private int dimension;

    /** This constructor takes the dimension as
    * input and returns a board consisting of 2d arrays
     * initialized with empty state
    */
    Board(int dimension) {
        if (dimension <= 0) { throw new IllegalArgumentException("Dimension must be positive"); }
        this.dimension = dimension;
        this.state = new CellState[dimension][dimension];
        for (int i=0 ; i<dimension ; i++) {
            for (int j=0 ; j<dimension ; j++) {
                state[i][j] = CellState.EMPTY;
            }
        }
    }

    public CellState getStateAtPosition(int row, int col) {
        if (row < 0 || row >= dimension) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= dimension) {
            throw new IllegalArgumentException("Invalid col");
        }
        return state[row][col];
    }

    public boolean setStateAtPosition(int row, int col, CellState state) {
        if (row < 0 || row >= dimension) { return false; }
        if (col < 0 || col >= dimension) { return false; }
        if (this.state[row][col] != CellState.EMPTY) { return false; }
        this.state[row][col] = state;
        return true;
    }

    public int getDimension() {
        return dimension;
    }
}
