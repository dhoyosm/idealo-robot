package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.exceptions.RobotNotFoundException;
import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.persistance.RobotRepository;
import me.danielhoyos.idealo.robot.utils.Constant;
import org.springframework.stereotype.Service;

import static me.danielhoyos.idealo.robot.model.Robot.*;

@Service
public class RobotService {

    RobotRepository robotRepository = RobotRepository.getInstance();

    public Robot getReport() {
        Robot robot = robotRepository.find();
        verifyIsRobotMissing(robot);
        return robot;
    }

    public void placeRobot(Robot robot) {
        robotRepository.update(robot);
    }

    public void turnRight() {
        turnDirection(false);
    }

    public void turnLeft() {
        turnDirection(true);
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

    private void verifyIsRobotMissing(final Robot robot) {
        if(robot == null ||
                0 > robot.getX() ||
                Constant.X_UNITS <= robot.getX() ||
                0 > robot.getY() ||
                Constant.Y_UNITS <= robot.getY()) {
            throw new RobotNotFoundException();
        }
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

}
