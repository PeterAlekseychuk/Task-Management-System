package peter.alekseychuk.TaskManagementSystem.service;

import org.springframework.data.domain.PageRequest;
import peter.alekseychuk.TaskManagementSystem.dto.CommentaryDto;
import peter.alekseychuk.TaskManagementSystem.dto.TaskDto;
import peter.alekseychuk.TaskManagementSystem.dto.UserDto;
import peter.alekseychuk.TaskManagementSystem.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task createTask(TaskDto taskDto);

    Task getTaskById(UUID id);

    Task updateTaskById(UUID id, TaskDto taskToBeUpdated);

    List<Task> getAllTask(PageRequest of);

    void deleteTaskById(UUID id);

    void changeTaskStatusById(UUID id, TaskDto taskDto);

    Task assignExecutorToTask(UUID id, UserDto userDto);

    List<Task> getPaginatedUserTasks(UUID id, PageRequest pageRequest);
}
