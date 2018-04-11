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
	public void getReport_returnsRobotDetails() {

		ResponseEntity<Robot> response = restTemplate.getForEntity("/robot/report", Robot.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getX()).isEqualTo(0);
		assertThat(response.getBody().getY()).isEqualTo(0);
		assertThat(response.getBody().getF()).isEqualTo(Direction.NORTH);
	}

}
