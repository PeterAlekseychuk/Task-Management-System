package peter.alekseychuk.TaskManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String header;

    @NotBlank
    @Size(min = 3, max = 50)
    private String description;

    @NotBlank
    private String status;

    @NotBlank
    private String priority;

    @Size(min = 3, max = 50)
    private String commentary;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;


    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private Executor executor;





}
