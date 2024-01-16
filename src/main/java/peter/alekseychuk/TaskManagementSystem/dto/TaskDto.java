package peter.alekseychuk.TaskManagementSystem.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import peter.alekseychuk.TaskManagementSystem.model.TaskPriority;
import peter.alekseychuk.TaskManagementSystem.model.TaskStatus;
import peter.alekseychuk.TaskManagementSystem.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    @Size(min = 3, max = 50)
    private String header;

    @Size(min = 3, max = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

}
