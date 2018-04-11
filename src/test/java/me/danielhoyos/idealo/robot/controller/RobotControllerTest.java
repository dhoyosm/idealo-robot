package me.danielhoyos.idealo.robot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.verify;
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

        given(robotService.getReport()).willReturn(robot);

        mockMvc.perform(MockMvcRequestBuilders.get("/robot/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("x").value(0))
                .andExpect(jsonPath("y").value(0))
                .andExpect(jsonPath("f").value("NORTH"));

    }

    @Test
    public void getReport_ShouldReturnRobotNotFound() throws Exception {

        given(robotService.getReport()).willThrow(new RobotNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/robot/report"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void placeRobot_ShouldReturnsOk() throws Exception {
        Robot robot = new Robot();
        robot.setX(2);
        robot.setY(2);

        String json = mapper.writeValueAsString(robot);

        mockMvc.perform(MockMvcRequestBuilders.post("/robot/place")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());

        verify(robotService).placeRobot(any(Robot.class));
    }
}
