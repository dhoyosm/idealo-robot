package me.danielhoyos.idealo.robot;

import me.danielhoyos.idealo.robot.model.Robot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot/report", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
/*
	@Test
	public void getReport_returnsRobotDetails() {
		ResponseEntity<Robot> response = restTemplate.getForEntity("/robot/report", Robot.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getX()).isEqualTo(0);
		assertThat(response.getBody().getY()).isEqualTo(0);
		assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);
	}
	*/

    @Test
    public void placeRobot_returnsOk() {
        Robot robotRequest = new Robot();
        robotRequest.setX(2);
        robotRequest.setY(2);
        robotRequest.setF(Direction.SOUTH);
        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot/place", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Robot> response = restTemplate.getForEntity("/robot/report", Robot.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getX()).isEqualTo(2);
        assertThat(response.getBody().getY()).isEqualTo(2);
        assertThat(response.getBody().getF()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void placeRobot_returnsInvalidCoordinates() {
        Robot robotRequest = new Robot();

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot/place", robotRequest, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
/*
    @Test
    public void placeRobot_returnsInvalidDirection() {
        Robot robotRequest = new Robot();

        String requestBody = "{\"x\":1, \"y\":1, \"f\":\"wrong\"}";

        ResponseEntity<String> postResponse = restTemplate.postForEntity("/robot/place", requestBody, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
*/
}
