package peter.alekseychuk.TaskManagementSystem.controller.task;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peter.alekseychuk.TaskManagementSystem.controller.task.request.Request;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.service.impl.AuthorServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.ExecutorServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.TaskServiceImpl;
import peter.alekseychuk.TaskManagementSystem.util.TaskErrorResponse;
import peter.alekseychuk.TaskManagementSystem.util.TaskException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static peter.alekseychuk.TaskManagementSystem.util.ErrorUtil.returnErrorsToClient;

@RestController()
@RequestMapping("/task")
@Api(description = "Контроллер управления задачами")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final AuthorServiceImpl authorService;
    private final ExecutorServiceImpl executorService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskServiceImpl taskService, AuthorServiceImpl authorService, ExecutorServiceImpl executorService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.authorService = authorService;
        this.executorService = executorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Получение всех задач")
    public ResponseEntity<?> getTasks() {
        List<Request> taskList = new ArrayList<>();

        taskList = taskService.getAllTasks().stream().map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskList, HttpStatus.OK);

    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Создание задачи")
    public ResponseEntity<Task> createTask(@RequestBody @Valid Request request, BindingResult bindingResult) {
        Task task = convertToTask(request);
        taskService.createTask(task);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Получение конкретной задачи")
    public ResponseEntity<Request> getTask(@PathVariable UUID id) {
        Request taskDTO = convertToDTO(taskService.getTask(id));
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Обновление конкретной задачи")
    public ResponseEntity<Request> updateTaskById(@PathVariable UUID id , @RequestBody @Valid Request request, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        Task task = convertToTask(request);
        taskService.updateTask(id, task);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Удаление конкретной задачи")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/status/{status}/{id}")
    @PreAuthorize("hasRole('EXECUTOR')")
    @ApiOperation("Изменение статуса задачи")
    public ResponseEntity<HttpStatus> changeTaskStatus(@PathVariable String status, @PathVariable UUID id) {
        taskService.changeTaskStatus(status, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> handleException(TaskException e) {
        TaskErrorResponse response = new TaskErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private Request convertToDTO(Optional<Task> task) {
        return modelMapper.map(task, Request.class);
    }

    private Request convertToDTO(Task task){
        return modelMapper.map(task, Request.class);
    }

    private Task convertToTask(Request request){
        return modelMapper.map(request, Task.class);
    }


}
