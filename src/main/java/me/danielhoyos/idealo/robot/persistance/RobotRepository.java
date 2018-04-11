package me.danielhoyos.idealo.robot.persistance;

import me.danielhoyos.idealo.robot.model.Robot;

public class RobotRepository {

    private final Robot robot;

    private static RobotRepository  robotRepositoryInstance = null;

    private RobotRepository(){
        robot = new Robot();
    }

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

    public Robot find() {
        return robot;
    }

    public void update(Robot robot) {
        synchronized (robot) {
            this.robot.setX(robot.getX());
            this.robot.setY(robot.getY());
            this.robot.setF(robot.getF());
        }
    }
}
