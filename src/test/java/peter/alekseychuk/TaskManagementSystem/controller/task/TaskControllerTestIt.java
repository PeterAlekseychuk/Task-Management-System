package peter.alekseychuk.TaskManagementSystem.controller.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import peter.alekseychuk.TaskManagementSystem.repository.TaskRepository;

import static org.jboss.logging.NDC.get;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TaskControllerTestIt {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void handleGetAllTasks_ReturnsValidResponseEntity() throws Exception {
        //given


        //when

        //then
    }

}