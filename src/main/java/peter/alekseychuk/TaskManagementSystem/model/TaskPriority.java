package peter.alekseychuk.TaskManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TaskPriority {
    LOW,
    MEDiUM,
    HIGH
}
