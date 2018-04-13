package me.danielhoyos.idealo.robot.persistance;

import me.danielhoyos.idealo.robot.model.Robot;

/**
 * Singleton class in charge of managing the
 * persistence of the Robot
 *
 * @author Daniel Hoyos
 */
public class RobotRepository {

    private final Robot robot;

    private static RobotRepository  robotRepositoryInstance = null;

    private RobotRepository(){
        robot = new Robot();
    }

    /**
     * Returns the instance of the repository.
     *
     * @return robotRepositoryInstance
     */
    public static RobotRepository getInstance() {
        if(robotRepositoryInstance == null) {
            synchronized (RobotRepository.class) {
                if(robotRepositoryInstance == null) {
                    robotRepositoryInstance = new RobotRepository();
                }
            }
        }
        return robotRepositoryInstance;
    }

    /**
     * It will return the current state of the Robot
     *
     * @return Robot
     */
    public Robot find() {
        Robot robot = new Robot();
        robot.setX(this.robot.getX());
        robot.setY(this.robot.getY());
        robot.setF(this.robot.getF());
        return robot;
    }

    /**
     * It will update and persist the state of the Robot
     *
     * @param robot
     */
    public void update(Robot robot) {
        synchronized (robot) {
            this.robot.setX(robot.getX());
            this.robot.setY(robot.getY());
            this.robot.setF(robot.getF());
        }
    }
}
