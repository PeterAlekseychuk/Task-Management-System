package peter.alekseychuk.TaskManagementSystem.controller.task.response;

import jakarta.validation.constraints.Size;
import lombok.*;
import peter.alekseychuk.TaskManagementSystem.model.Author;
import peter.alekseychuk.TaskManagementSystem.model.Executor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {

    @Size(min = 3, max = 300)
    private String header;

    @Size(min = 3, max = 300)
    private String description;

    private String status;

    private String priority;

    @Size(min = 3, max = 50)
    private String commentary;

    private Author author;


    private Executor executor;
}
