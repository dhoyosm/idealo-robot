package me.danielhoyos.idealo.robot.model;

public class Robot {

    private int x = 0;
    private int y = 0;
    private Direction f = Direction.NORTH;

    public enum Direction {
        NORTH, SOUTH, EAST, WEST;
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

    public Direction getF() {
        return f;
    }

    public void setF(Direction f) {
        this.f = f;
    }
}
