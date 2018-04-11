package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.persistance.RobotRepository;
import org.springframework.stereotype.Service;

@Service
public class RobotService {

    RobotRepository robotRepository = RobotRepository.getInstance();

    public Robot getReport() {
        return robotRepository.find();
    }
}
