package peter.alekseychuk.TaskManagementSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.repository.TaskRepository;
import peter.alekseychuk.TaskManagementSystem.service.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final AuthorServiceImpl authorService;
    private final ExecutorServiceImpl executorService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, AuthorServiceImpl authorService, ExecutorServiceImpl executorService) {
        this.taskRepository = taskRepository;
        this.authorService = authorService;
        this.executorService = executorService;
    }

    @Override
    @Transactional
    public void createTask(Task task) {

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(UUID id, Task newTask) {
        Optional<Task> task = taskRepository.findById(id);
            Task updatedTask = task.get();
            updatedTask.setDescription(newTask.getDescription());
            updatedTask.setCommentary(newTask.getCommentary());
            updatedTask.setPriority(newTask.getPriority());
            updatedTask.setStatus(newTask.getStatus());
            taskRepository.save(updatedTask);
            return updatedTask;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> getTask(UUID id) {
        Optional<Task> task = taskRepository.findById(id);
        return task;
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void changeTaskStatus(String status, UUID id) {
        taskRepository.setStatusForTask(status, id);
    }

    @Transactional
    public void assignAuthor(Task task, UUID id) {
        task.setAuthor(authorService.findAuthorById(id).get());
    }

    @Transactional
    public void assignExecutor(Task task, UUID id) {
        task.setExecutor(executorService.findExecutorById(id).get());
    }

}
