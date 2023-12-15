package peter.alekseychuk.TaskManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Executor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Executor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //@Column(name = "first_name")
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;

    //@Column(name = "last_name")
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;

    @Enumerated(EnumType.STRING)
    private CommunityRoleType roleType;

    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Task> task;



}
