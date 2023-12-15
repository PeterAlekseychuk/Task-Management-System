package peter.alekseychuk.TaskManagementSystem.controller.registration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peter.alekseychuk.TaskManagementSystem.model.Role;
import peter.alekseychuk.TaskManagementSystem.model.User;
import peter.alekseychuk.TaskManagementSystem.service.impl.AuthorServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.ExecutorServiceImpl;
import peter.alekseychuk.TaskManagementSystem.service.impl.UserServiceImpl;

import java.util.Optional;

import static peter.alekseychuk.TaskManagementSystem.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/registration")
@Api(description = "Контроллер для назначения прав доступа пользователям")
public class RegistrationController {
    private final AuthorServiceImpl authorService;
    private final ExecutorServiceImpl executorService;
    private final UserServiceImpl userService;

    @Autowired
    public RegistrationController(AuthorServiceImpl authorService, ExecutorServiceImpl executorService, UserServiceImpl userService) {
        this.authorService = authorService;
        this.executorService = executorService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/author")
    @ApiOperation("Назначение роли автор")
    public ResponseEntity<HttpStatus> registerAuthor(@RequestBody String email,
                                                     BindingResult bindingResult) {
        User user = userService.findByEmail(email);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        userService.setAuthorRole(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/executor")
    @ApiOperation("Назначение роли исполнитель")
    public ResponseEntity<HttpStatus> registerExecutor(@RequestBody String email,
                                                       BindingResult bindingResult) {
        User user = userService.findByEmail(email);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
        userService.setExecutorRole(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
