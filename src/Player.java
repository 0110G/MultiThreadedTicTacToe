/**
 * Player class is intended to be used separately for each
 * game instance (thread) and not intended to be shared.
 * The NULL_PLAYER is clearly shared between threads.
 */
public class Player {
    private String name;
    private String mark;
    private int wins;
    private int loses;
    private int draws;

    public static final Player NULL_PLAYER = new Player("", "");

    Player(String name, String mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return mark.equals(player.getMark());
    }
}
