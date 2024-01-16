package peter.alekseychuk.TaskManagementSystem.controller.auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peter.alekseychuk.TaskManagementSystem.security.AuthenticationRequest;
import peter.alekseychuk.TaskManagementSystem.security.AuthenticationResponse;
import peter.alekseychuk.TaskManagementSystem.security.RegisterRequest;
import peter.alekseychuk.TaskManagementSystem.security.service.AuthenticationService;

import static peter.alekseychuk.TaskManagementSystem.util.ErrorUtil.returnErrorsToClient;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Post endpoint for user registration",
            description = "Pass user json to register a user"
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request, BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
            summary = "Post endpoint for user authentication",
            description = "Pass auth json to authenticate"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody  AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
