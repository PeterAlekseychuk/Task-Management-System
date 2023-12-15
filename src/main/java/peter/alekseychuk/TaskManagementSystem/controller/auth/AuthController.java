package peter.alekseychuk.TaskManagementSystem.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "Контроллер для регистрации и аутентификации пользователей")
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ApiOperation("Регистрация пользователя")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request, BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    @ApiOperation("Аутентификация пользователя")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody  AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.signIn(request));
    }
}
