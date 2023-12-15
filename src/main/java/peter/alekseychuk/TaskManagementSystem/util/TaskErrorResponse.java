package peter.alekseychuk.TaskManagementSystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskErrorResponse {
    private String message;
    private long timestamp;


}
