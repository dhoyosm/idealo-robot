package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.model.Robot;

/**
 * Interface to handle robot operations
 *
 * @author Daniel Hoyos
 */
public interface RobotService {

    /***
     * Returns the current status of the robot.
     *
     * @return robot
     */
    Robot getReportRobot();

    /***
     * Place the robot on the grid.
     *
     * @param robot
     */
    void placeRobot(Robot robot);

    /***
     * Rotates robot 90 degrees to the right.
     */
    void turnRight();

    /***
     * Rotates robot 90 degrees to the left.
     */
    void turnLeft();

    /***
     * Mpves robot one unit forward in the direction it is facing.
     */
    void move();
}
