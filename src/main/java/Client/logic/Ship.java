package Client.logic;

public class Ship {
    int x;
    int y;
    int locationX;
    int locationY;
    Position position;
    Ships ship;

    public Ship(int localX, int localY, Position pos, Ships ship) {
        locationX = localX;
        locationY = localY;
        position = pos;
        this.ship = ship;
        if (position.getPosition() == 0) {
            x = ship.getSize();
            y = 1;
        } else {
            x = 1;
            y = ship.getSize();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocation(int localX, int localY, Position pos) {
        locationX = localX;
        locationY = localY;
        position = pos;
        if (position.getPosition() == 0) {
            x = ship.getSize();
            y = 1;
        } else {
            x = 1;
            y = ship.getSize();
        }
    }

    @Override
    public String toString() {
        return "Ship{" +
                "x=" + x +
                ", y=" + y +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", ship=" + ship +
                '}';
    }
}
