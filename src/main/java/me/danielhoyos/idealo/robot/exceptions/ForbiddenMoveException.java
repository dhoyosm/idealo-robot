package me.danielhoyos.idealo.robot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to handle error response when a move can cause the
 * robot to go missing
 *
 * @author Daniel Hoyos
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenMoveException  extends RuntimeException {

    public ForbiddenMoveException() {
        super("FORBIDDEN MOVE. IT WILL CAUSE ROBOT TO GO MISSING");
    }
}
