package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.exceptions.ForbiddenMoveException;
import me.danielhoyos.idealo.robot.exceptions.RobotNotFoundException;
import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.persistance.RobotRepository;
import me.danielhoyos.idealo.robot.utils.Constant;
import org.springframework.stereotype.Service;

import static me.danielhoyos.idealo.robot.model.Robot.*;

/**
 * Implementation of interface to handle robot operations
 *
 * @author Daniel Hoyos
 */
@Service
public class RobotServiceImpl implements RobotService {

    RobotRepository robotRepository = RobotRepository.getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    public Robot getReportRobot() {
        Robot robot = robotRepository.find();
        verifyIsRobotMissing(robot);
        return robot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeRobot(Robot robot) {
        robotRepository.update(robot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void turnRight() {
        turnDirection(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void turnLeft() {
        turnDirection(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        Robot robot = robotRepository.find();
        verifyIsRobotMissing(robot);
        executeMove(robot);
        robotRepository.update(robot);
    }

    /***
     * Fetches the robot, turns it to the direction selected, and saves it back to the repo
     *
     * @param isTurnLeft true if turning left, false otherwise
     *
     * @Throws RobotNotFoundException if the robot is not on table
     */
    private void turnDirection(final boolean isTurnLeft) {
        Robot robot = robotRepository.find();
        verifyIsRobotMissing(robot);
        executeTurnDirection(robot, isTurnLeft);
        robotRepository.update(robot);
    }

    /***
     * Verifies if robot is missing
     *
     * @param robot
     *
     * @Throws RobotNotFoundException if the robot is not on table
     */
    private void verifyIsRobotMissing(final Robot robot) {
        if(!isRobotOnTable(robot)) {
            throw new RobotNotFoundException();
        }
    }

    /***
     * Using the robots coordinates checks if the robot is on the table
     * @param robot
     *
     * @return true if the robot is on the table
     */
    private boolean isRobotOnTable(final Robot robot) {
        return !(robot == null ||
                0 > robot.getX() ||
                Constant.X_UNITS <= robot.getX() ||
                0 > robot.getY() ||
                Constant.Y_UNITS <= robot.getY());
    }

    /***
     * It executes the direction turn, by moving the index of the Direction Enum
     * and assign it to the robot.
     *
     * @param robot
     * @param isTurnLeft
     */
    private void executeTurnDirection(final Robot robot, final boolean isTurnLeft) {
        final Direction directions[] = Direction.values();
        final int currentDirectionIndex = robot.getF().ordinal();
        int positionOffset = 0;
        int times = 1;
        if(isTurnLeft) {
            positionOffset = directions.length;
            times = -1;
        }
        final int newDirectionIndex = (currentDirectionIndex + times + positionOffset) % directions.length;
        robot.setF(directions[newDirectionIndex]);
    }

    /***
     * Executes the move according to the robots current position
     *
     * @param robot
     *
     * @Throws ForbiddenMoveException if the move would cause the robot to fall of the table
     */
    private void executeMove(final Robot robot) {
        switch (robot.getF()) {
            case NORTH:
                robot.setY(robot.getY() + 1);
                break;
            case SOUTH:
                robot.setY(robot.getY() - 1);
                break;
            case EAST:
                robot.setX(robot.getX() + 1);
                break;
            case WEST:
                robot.setX(robot.getX() - 1);
                break;
        }
        if(!isRobotOnTable(robot)){
            throw new ForbiddenMoveException();
        }
    }

}
