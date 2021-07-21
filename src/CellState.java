public enum CellState {
    EMPTY(" "),
    FILLED_PLAYER_1("x"),
    FILLED_PLAYER_2("o"),
    INVALID("");

    private final String mark;

    private CellState(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
}
