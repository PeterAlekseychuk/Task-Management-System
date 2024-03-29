package peter.alekseychuk.TaskManagementSystem.controller.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peter.alekseychuk.TaskManagementSystem.dto.CommentaryDto;
import peter.alekseychuk.TaskManagementSystem.dto.TaskDto;
import peter.alekseychuk.TaskManagementSystem.dto.UserDto;
import peter.alekseychuk.TaskManagementSystem.model.Commentary;
import peter.alekseychuk.TaskManagementSystem.model.Task;
import peter.alekseychuk.TaskManagementSystem.service.impl.CommentaryServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/task")
@Tag(name = "Task")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final CommentaryServiceImpl commentaryService;

    @Autowired
    public TaskController(TaskServiceImpl taskService, CommentaryServiceImpl commentaryService) {
        this.taskService = taskService;
        this.commentaryService = commentaryService;
    }

    @Operation(
            summary = "Post endpoint for creating a task",
            description = "Pass valid task json to create a task"
    )
    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDto taskDto) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.createTask(taskDto));
    }

    @Operation(
            summary = "Get endpoint for getting a task",
            description = "Pass an id of a task to get a task"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getTaskById(id));
    }

    @Operation(
            summary = "Get endpoint for getting all tasks",
            description = "This method is paginated, you can pass optional page and size parameters, to get all tasks"
    )
    @GetMapping()
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "1000000") int size) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAllTask(PageRequest.of(page, size)));
    }

    @Operation(
            summary = "Put endpoint for updating a task",
            description = "Pass an id of a task and a valid task json to update task fields"
    )
    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<Task> updateTaskById(@PathVariable UUID id, @RequestBody @Valid TaskDto taskDto) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.updateTaskById(id, taskDto));
    }

    @Operation(
            summary = "Patch endpoint to change task status",
            description = "This method updates task status," +
                    "you need to pass id of a task and a valid task json," +
                    " only executor has authorities to this method"
    )
    @PatchMapping("/{id}")
    @Secured("ROLE_EXECUTOR")
    public ResponseEntity<Task> changeTaskStatusById(@PathVariable UUID id, @RequestBody TaskDto taskDto) {

        return  ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.changeTaskStatusById(id, taskDto));
    }

    @Operation(
            summary = "Post endpoint to assign executor to task",
            description = "This method assigns executor to task, pass an id of a task" +
                    "and user json, only author has authorities to assign executor"
    )
    @PostMapping("/assign/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<Task> assignExecutorToTask(@PathVariable UUID id, @RequestBody UserDto userDto) {
        taskService.assignExecutorToTask(id, userDto);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.assignExecutorToTask(id, userDto));
    }

    @Operation(
            summary = "Get method for getting user tasks",
            description = "This method is paginated, pass an id of a user and optionally pass page and size " +
                    "parameters for paginated output"
    )
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Task>> getPagedUserTasks(@PathVariable UUID id,
                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getPaginatedUserTasks(id, PageRequest.of(page, size)));
    }

    @Operation(
            summary = "Post endpoint for adding a commentary to a task",
            description = "Pass task id and commentary json to create a commentary"
    )
    @PostMapping("/commentary/{taskId}")
    public ResponseEntity<Commentary> addCommentary(@PathVariable UUID taskId,
                                                    @RequestBody CommentaryDto commentaryDto) {

        Commentary commentary = commentaryService.addCommentary(taskId, commentaryDto);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentary);
    }

    @Operation(
            summary = "Delete endpoint for deleting task",
            description = "Pass task id to delete a task"
    )
    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable UUID id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
