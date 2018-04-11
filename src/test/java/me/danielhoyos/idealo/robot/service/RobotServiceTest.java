package me.danielhoyos.idealo.robot.service;

import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.persistance.RobotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static me.danielhoyos.idealo.robot.model.Robot.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
        given(robotRepository.find()).willReturn(new Robot());

        Robot robot = robotService.getReport();

        assertThat(robot.getX()).isEqualTo(0);
        assertThat(robot.getY()).isEqualTo(0);
        assertThat(robot.getF()).isEqualTo(Direction.NORTH);
    }
}
