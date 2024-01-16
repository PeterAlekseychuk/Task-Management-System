package peter.alekseychuk.TaskManagementSystem.util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler
    private ResponseEntity<TaskExceptionResponse> handleException(TaskNotFoundException e) {
        TaskExceptionResponse response = new TaskExceptionResponse("Task with this id was not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}