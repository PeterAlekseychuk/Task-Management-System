package peter.alekseychuk.TaskManagementSystem.controller.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import peter.alekseychuk.TaskManagementSystem.dto.UserDto;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.model.TaskStatus;
import peter.alekseychuk.TaskManagementSystem.repository.TaskRepository;
import peter.alekseychuk.TaskManagementSystem.service.impl.CommentaryServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TaskControllerTestIt {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private TaskServiceImpl taskService;
    @Mock
    private CommentaryServiceImpl commentaryService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void getAllTasks() throws Exception {
        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(taskService, Mockito.times(1))
                .getAllTask(PageRequest.of(0,1000000));
    }

    @Test
    public void getTaskById() throws Exception {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .header("header")
                .build();
        UUID id = task.getId();
        Mockito.when(taskService.getTaskById(id))
                .thenReturn(task);
        mockMvc.perform(get("/task/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(""+id))
                .andExpect(jsonPath("$.header").value("header"));
        Mockito.verify(taskService, Mockito.times(1)).getTaskById(id);
    }

    @Test
    public void assignExecutorToTask() throws Exception {
        UserDto user = new UserDto();
        user.setEmail("user@mail.com");
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .build();
        UUID id = task.getId();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/task/assign/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
    }

}