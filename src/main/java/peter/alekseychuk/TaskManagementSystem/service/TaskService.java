package peter.alekseychuk.TaskManagementSystem.service;

import peter.alekseychuk.TaskManagementSystem.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    void createTask(Task task);

    Task updateTask(UUID id, Task taskToBeUpdated);

    List<Task> getAllTasks();

    Optional<Task> getTask(UUID id);

    void deleteTask(UUID id);

    void changeTaskStatus(String status, UUID id);

    void assignAuthor(Task task, UUID id);


}
