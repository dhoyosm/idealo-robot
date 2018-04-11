package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.exceptions.RobotNotFoundException;
import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.persistance.RobotRepository;
import me.danielhoyos.idealo.robot.utils.Constant;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    RobotRepository robotRepository = RobotRepository.getInstance();

    public Robot getReport() {
        Robot robot = robotRepository.find();
        if(isMissing(robot)) {
            throw new RobotNotFoundException();
        }
        return robot;
    }

    public void placeRobot(Robot robot) {
        robotRepository.update(robot);
    }

    private boolean isMissing(Robot robot) {
        if(robot == null) {
            return true;
        }
        return (0 > robot.getX() ||
                Constant.X_UNITS <= robot.getX() ||
                0 > robot.getY() ||
                Constant.Y_UNITS <= robot.getY());
    }


}
