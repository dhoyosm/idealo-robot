package me.danielhoyos.idealo.robot.controller;

import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @GetMapping("/report")
    private final ResponseEntity<Robot> getReport() {
        return new ResponseEntity<>(robotService.getReport(), HttpStatus.OK);
    }

    @PostMapping("/place")
    private final ResponseEntity<String> placeRobot(@Valid @RequestBody Robot robotRequest) {
        robotService.placeRobot(robotRequest);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
