package me.danielhoyos.idealo.robot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to handle error response when the system
 * can not find robot
 *
 * @author Daniel Hoyos
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RobotNotFoundException extends RuntimeException {

    public RobotNotFoundException() {
        super("ROBOT MISSING");
    }
}
