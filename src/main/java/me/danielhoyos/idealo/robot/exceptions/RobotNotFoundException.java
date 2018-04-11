package me.danielhoyos.idealo.robot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RobotNotFoundException extends RuntimeException {

    public RobotNotFoundException() {
        super("ROBOT MISSING");
    }
}
