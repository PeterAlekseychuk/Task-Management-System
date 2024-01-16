package peter.alekseychuk.TaskManagementSystem.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peter.alekseychuk.TaskManagementSystem.dto.CommentaryDto;
import peter.alekseychuk.TaskManagementSystem.dto.TaskDto;
import peter.alekseychuk.TaskManagementSystem.dto.UserDto;
import peter.alekseychuk.TaskManagementSystem.model.Role;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.model.User;
import peter.alekseychuk.TaskManagementSystem.repository.CommentaryRepository;
import peter.alekseychuk.TaskManagementSystem.repository.TaskRepository;
import peter.alekseychuk.TaskManagementSystem.service.TaskService;
import peter.alekseychuk.TaskManagementSystem.util.ErrorUtil;
import peter.alekseychuk.TaskManagementSystem.util.TaskException;
import peter.alekseychuk.TaskManagementSystem.util.TaskNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;
    private final CommentaryRepository commentaryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserServiceImpl userService, CommentaryRepository commentaryRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.commentaryRepository = commentaryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Task createTask(TaskDto taskDto) {
        final User author = userService.getCurrentUser();
        Task task = fromTaskDto(taskDto);
        task.setAuthor(author);
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
    }

    @Override
    @Transactional
    public Task updateTaskById(UUID id, TaskDto taskToBeUpdated) {
        Task task = getTaskById(id);

        task.setDescription(taskToBeUpdated.getDescription());
        task.setHeader(taskToBeUpdated.getHeader());
        task.setStatus(taskToBeUpdated.getStatus());
        task.setPriority(taskToBeUpdated.getPriority());

        taskRepository.save(task);

        return task;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTask(PageRequest of) {
        Page<Task> page = taskRepository.findAll(of);
        return page.getContent();
    }

    @Override
    @Transactional
    public void deleteTaskById(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void changeTaskStatusById(UUID id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        task.setStatus(taskDto.getStatus());
        taskRepository.save(task);
    }


    //Назначение конкретным автором исполнителя задачи
    @Override
    @Transactional
    public void assignExecutorToTask(UUID id, UserDto userDto) {
        User executor = userService.findByEmail(userDto.getEmail());
        User author = userService.getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        if (executor.getRole() != Role.EXECUTOR) {
            throw new TaskException("The user is not executor");
        } else if (author.getId() != task.getAuthor().getId()) {
            throw new TaskException("The user is not the author of the task");
        } else {
            task.setExecutor(executor);
            taskRepository.save(task);
        }
    }

    //Пагинация нативного sql запроса по получению задач конкретного автора/испонителя по его Id
    @Override
    @Transactional(readOnly = true)
    public List<Task> getPaginatedUserTasks(UUID id, PageRequest pageRequest) {
        User user = userService.findById(id);

        if (user.getRole() == Role.EXECUTOR) {
            Page<Task> page = taskRepository.findAllExecutorTasksByUserId(id, pageRequest);
            return page.getContent();
        } else if (user.getRole() == Role.USER) {
            Page<Task> page = taskRepository.findAllAuthorTasksByUserId(id, pageRequest);
            return page.getContent();
        }
        throw new TaskException("User has no tasks");
    }

    private TaskDto toTaskDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    private Task fromTaskDto(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }

}
