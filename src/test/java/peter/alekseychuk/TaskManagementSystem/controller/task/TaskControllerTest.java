package peter.alekseychuk.TaskManagementSystem.controller.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import peter.alekseychuk.TaskManagementSystem.dto.TaskDto;
import peter.alekseychuk.TaskManagementSystem.model.Commentary;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.model.TaskPriority;
import peter.alekseychuk.TaskManagementSystem.model.TaskStatus;
import peter.alekseychuk.TaskManagementSystem.repository.TaskRepository;
import peter.alekseychuk.TaskManagementSystem.service.impl.CommentaryServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.TaskServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private static final UUID ID = new UUID(1, 1);
    @Mock
    TaskServiceImpl taskService;
    @Mock
    CommentaryServiceImpl commentaryService;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskController controller;

    @Test
    public void handleGetAllTasks_ReturnsValidResponseEntity() {
        //given
        Task task1 = Task.builder()
                .header("header")
                .description("description")
                .status(TaskStatus.IN_PROCESS)
                .priority(TaskPriority.MEDiUM)
                .build();
        Task task2 = Task.builder()
                .header("header2")
                .description("description2")
                .status(TaskStatus.IN_PROCESS)
                .priority(TaskPriority.MEDiUM)
                .build();
        List<Task> tasks = List.of(task1, task2);
        Mockito.when(this.taskService.getAllTask(PageRequest.of(0, 2)))
                .thenReturn(tasks);
        //when
        ResponseEntity<List<Task>> responseEntity = this.controller.getAllTask(0, 2);
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(tasks, responseEntity.getBody());
    }

    @Test
    public void getTaskById_shouldFindTask_WhenExists() {
        //given
        Task task1 = Task.builder()
                .id(UUID.randomUUID())
                .header("header")
                .description("description")
                .status(TaskStatus.IN_PROCESS)
                .priority(TaskPriority.MEDiUM)
                .build();
        Mockito.when(this.taskService.getTaskById(task1.getId()))
                .thenReturn(task1);
        //when
        ResponseEntity<Task> responseEntity = this.controller.getTaskById(task1.getId());
        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(task1, responseEntity.getBody());
    }


}