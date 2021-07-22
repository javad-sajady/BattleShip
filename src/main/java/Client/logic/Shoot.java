package Client.logic;

public class Shoot {
    public int x;
    public int y;
    public boolean result;

    public Shoot(int x, int y) {
        this.x = x;
        this.y = y;
        result =false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Shoot{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
