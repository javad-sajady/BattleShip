package Client.logic;

public enum Ships {
    Battleship(4),
    Cruiser(3),
    Destroyer(2),
    Frigate(1);

    public int size;

    Ships(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
