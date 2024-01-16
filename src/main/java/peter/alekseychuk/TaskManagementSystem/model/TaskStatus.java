package peter.alekseychuk.TaskManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;

//@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TaskStatus {
    WAITING,
    IN_PROCESS,
    DONE
}
