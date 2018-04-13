package me.danielhoyos.idealo.robot;

import me.danielhoyos.idealo.robot.model.Robot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static me.danielhoyos.idealo.robot.model.Robot.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RobotApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

    @Test
    public void getReport_returnsRobotNotFoundException() {

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void placeRobot_returnsOk() {
        Robot robotRequest = new Robot();
        robotRequest.setX(2);
        robotRequest.setY(2);
        robotRequest.setF(Direction.SOUTH);
        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(2);
        assertThat(response.getBody().getY()).isEqualTo(2);
        assertThat(response.getBody().getF()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void placeRobot_returnsInvalidCoordinates() {
        Robot robotRequest = new Robot();

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void turnRight_returnsOk() {
        Robot robotRequest = new Robot();
        robotRequest.setX(2);
        robotRequest.setY(2);

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> putResponse = restTemplate.exchange
                ("/robot/right", HttpMethod.PUT, null, String.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(2);
        assertThat(response.getBody().getY()).isEqualTo(2);
        assertThat(response.getBody().getF()).isEqualTo(Direction.EAST);

        restTemplate.exchange("/robot/right", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getBody().getF()).isEqualTo(Direction.SOUTH);

        restTemplate.exchange("/robot/right", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getBody().getF()).isEqualTo(Direction.WEST);

        restTemplate.exchange("/robot/right", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void turnLeft_returnsOk() {
        Robot robotRequest = new Robot();
        robotRequest.setX(2);
        robotRequest.setY(2);

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> putResponse = restTemplate.exchange
                ("/robot/left", HttpMethod.PUT, null, String.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(2);
        assertThat(response.getBody().getY()).isEqualTo(2);
        assertThat(response.getBody().getF()).isEqualTo(Direction.WEST);

        restTemplate.exchange("/robot/left", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getBody().getF()).isEqualTo(Direction.SOUTH);

        restTemplate.exchange("/robot/left", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getBody().getF()).isEqualTo(Direction.EAST);

        restTemplate.exchange("/robot/left", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void move_returnsOk() {
        Robot robotRequest = new Robot();
        robotRequest.setX(0);
        robotRequest.setY(0);

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> putResponse = restTemplate.exchange
                ("/robot/move", HttpMethod.PUT, null, String.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(0);
        assertThat(response.getBody().getY()).isEqualTo(1);
        assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);

        restTemplate.exchange("/robot/move", HttpMethod.PUT, null, String.class);
        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(0);
        assertThat(response.getBody().getY()).isEqualTo(2);
        assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void move_returnsForbidden() {
        Robot robotRequest = new Robot();
        robotRequest.setX(0);
        robotRequest.setY(3);

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> putResponse = restTemplate.exchange
                ("/robot/move", HttpMethod.PUT, null, String.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(0);
        assertThat(response.getBody().getY()).isEqualTo(4);
        assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);

        putResponse = restTemplate.exchange
                ("/robot/move", HttpMethod.PUT, null, String.class);
        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        response = restTemplate.getForEntity("/robot", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(0);
        assertThat(response.getBody().getY()).isEqualTo(4);
        assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);
    }

}
