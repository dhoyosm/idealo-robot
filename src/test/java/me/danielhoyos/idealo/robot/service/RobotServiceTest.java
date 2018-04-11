package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.exceptions.RobotNotFoundException;
import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.persistance.RobotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static me.danielhoyos.idealo.robot.model.Robot.Direction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {RobotRepository.class} )
public class RobotServiceTest {

    @Mock
    private RobotRepository robotRepository;

    private RobotService robotService;

    @Before
    public void setUp() {
        mockStatic(RobotRepository.class);
        given(RobotRepository.getInstance()).willReturn(robotRepository);

        robotService = new RobotService();
    }

    @Test
    public void getReport_returnsRobot() {
        Robot robot = new Robot();
        robot.setY(0);
        robot.setX(0);
        given(robotRepository.find()).willReturn(robot);

        Robot response = robotService.getReport();

        assertThat(response.getX()).isEqualTo(0);
        assertThat(response.getY()).isEqualTo(0);
        assertThat(response.getF()).isEqualTo(Direction.NORTH);
    }

    @Test(expected = RobotNotFoundException.class)
    public void getReport_returnsRobotNotFoundException() {
        given(robotRepository.find()).willReturn(new Robot());

        Robot robot = robotService.getReport();

        assertThat(robot.getX()).isEqualTo(-1);
        assertThat(robot.getY()).isEqualTo(-1);
        assertThat(robot.getF()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void placeRobot_returnsRobot() {
        Robot robot = new Robot();
        robot.setY(2);
        robot.setX(2);

        robotService.placeRobot(robot);

        verify(robotRepository).update(robot);
    }
}
