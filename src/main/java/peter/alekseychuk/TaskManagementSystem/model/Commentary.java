package peter.alekseychuk.TaskManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "commentaries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String commentary;

    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private User authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task taskId;



}
