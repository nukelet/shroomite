package com.javasegfault.shroomite.util;

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(Position pos) {
		this(pos.getX(), pos.getY());
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Position add(Position pos) {
        return new Position(x + pos.getX(), y + pos.getY());
    }

    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position sub(Position pos) {
        return new Position(x - pos.getX(), y - pos.getY());
    }

    public Position sub(int x, int y) {
        return new Position(this.x - x, this.y - y);
    }

    public Position up() {
        return new Position(x, y + 1);
    }

    public Position down() {
    	return new Position(x, y - 1);
    }

    public Position left() {
    	return new Position(x - 1, y);
    }

    public Position right() {
    	return new Position(x + 1, y);
    }
	
	@Override
	public String toString() {
		return String.format("Position(x=%d, y=%d)", x, y);
	}
}
