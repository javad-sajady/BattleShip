package Client.logic;

public enum Position {
    horizontal(0),
    vertical(1);

    private int position;

    Position(int i) {
        this.position = i;
    }

    public int getPosition() {
        return position;
    }

}
