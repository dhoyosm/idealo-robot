package me.danielhoyos.idealo.robot.controller;

import me.danielhoyos.idealo.robot.model.Robot;
import me.danielhoyos.idealo.robot.service.RobotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static me.danielhoyos.idealo.robot.model.Robot.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RobotController.class)
public class RobotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RobotService robotService;

    @Test
    public void getReport_ShouldReturnRobot() throws Exception {

        given(robotService.getReport()).willReturn(new Robot());

        mockMvc.perform(MockMvcRequestBuilders.get("/robot/report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("x").value(0))
                .andExpect(jsonPath("y").value(0))
                .andExpect(jsonPath("f").value("NORTH"));

    }
}
