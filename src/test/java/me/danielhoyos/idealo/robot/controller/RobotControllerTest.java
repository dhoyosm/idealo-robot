package me.danielhoyos.idealo.robot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.danielhoyos.idealo.robot.exceptions.ForbiddenMoveException;
import me.danielhoyos.idealo.robot.exceptions.RobotNotFoundException;
import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.service.RobotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static me.danielhoyos.idealo.robot.model.Robot.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RobotController.class)
public class RobotControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RobotService robotService;

    @Test
    public void getReport_ShouldReturnRobot() throws Exception {
        Robot robot = new Robot();
        robot.setX(0);
        robot.setY(0);

        given(robotService.getReportRobot()).willReturn(robot);

        mockMvc.perform(MockMvcRequestBuilders.get("/robot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("x").value(0))
                .andExpect(jsonPath("y").value(0))
                .andExpect(jsonPath("f").value("NORTH"));
    }

    @Test
    public void getReport_ShouldReturnRobotNotFound() throws Exception {

        given(robotService.getReportRobot()).willThrow(new RobotNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/robot"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void placeRobot_ShouldReturnsOk() throws Exception {
        Robot robot = new Robot();
        robot.setX(2);
        robot.setY(2);

        String json = mapper.writeValueAsString(robot);

        mockMvc.perform(MockMvcRequestBuilders.post("/robot")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        verify(robotService).placeRobot(any(Robot.class));
    }

    @Test
    public void turnRight_ShouldReturnsOk() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.put("/robot/right"))
                .andExpect(status().isOk());

         verify(robotService).turnRight();
    }

    @Test
    public void turnRight_ShouldReturnRobotNotFound() throws Exception {
        doThrow(RobotNotFoundException.class).when(robotService).turnRight();

        mockMvc.perform(MockMvcRequestBuilders.put("/robot/right"))
                .andExpect(status().isNotFound());

        verify(robotService).turnRight();
    }

    @Test
    public void turnLeft_ShouldReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/robot/left"))
                .andExpect(status().isOk());

        verify(robotService).turnLeft();
    }

    @Test
    public void turnLeft_ShouldReturnRobotNotFound() throws Exception {
        doThrow(RobotNotFoundException.class).when(robotService).turnLeft();

        mockMvc.perform(MockMvcRequestBuilders.put("/robot/left"))
                .andExpect(status().isNotFound());

        verify(robotService).turnLeft();
    }

    @Test
    public void move_ShouldReturnsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/robot/move"))
                .andExpect(status().isOk());

        verify(robotService).move();
    }

    @Test
    public void move_ShouldReturnRobotNotFound() throws Exception {
        doThrow(RobotNotFoundException.class).when(robotService).move();

        mockMvc.perform(MockMvcRequestBuilders.put("/robot/move"))
                .andExpect(status().isNotFound());

        verify(robotService).move();
    }

    @Test
    public void move_ShouldReturnForbiddenMove() throws Exception {
        doThrow(ForbiddenMoveException.class).when(robotService).move();

        mockMvc.perform(MockMvcRequestBuilders.put("/robot/move"))
                .andExpect(status().isForbidden());

        verify(robotService).move();
    }
}
