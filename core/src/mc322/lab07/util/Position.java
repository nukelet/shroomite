package mc322.lab07.util;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public Position add(Position pos) {
        return new Position(x + pos.x, y + pos.y);
    }

    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position sub(Position pos) {
        return new Position(x - pos.x, y - pos.y);
    }

    public Position sub(int x, int y) {
        return new Position(this.x - x, this.y - y);
    }

    public Position up() {
        return add(0, 1);
    }

    public Position down() {
        return add(0, -1);
    }

    public Position left() {
        return add(-1, 0);
    }

    public Position right() {
        return add(1, 0);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
