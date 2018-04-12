package me.danielhoyos.idealo.robot.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static me.danielhoyos.idealo.robot.utils.Constant.X_UNITS;
import static me.danielhoyos.idealo.robot.utils.Constant.Y_UNITS;

public class Robot {

    @PositiveOrZero
    @Max(X_UNITS - 1)
    private int x = -1;

    @PositiveOrZero
    @Max(Y_UNITS - 1)
    private int y = -1;

    @NotNull
    private Direction f = Direction.NORTH;

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
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
