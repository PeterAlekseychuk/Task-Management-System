package peter.alekseychuk.TaskManagementSystem.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Size(min = 3, max = 50)
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;

    @NotBlank
    @Size(min = 3, max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 61)
    private String password;
}
