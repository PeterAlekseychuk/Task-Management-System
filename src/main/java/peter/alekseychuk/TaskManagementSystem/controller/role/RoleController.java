package peter.alekseychuk.TaskManagementSystem.controller.role;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peter.alekseychuk.TaskManagementSystem.dto.UserDto;
import peter.alekseychuk.TaskManagementSystem.model.User;
import peter.alekseychuk.TaskManagementSystem.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/role")
@Tag(name = "Role")
public class RoleController {

    private final UserServiceImpl userService;

    @Autowired
    public RoleController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Post endpoint to set admin role to user",
            description = "This method should be hidden from users, it gives current user admin role"
    )
    @PostMapping("/admin")
    public ResponseEntity<HttpStatus> setRoleAdmin() {

        userService.setRoleAdmin();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Post endpoint to set executor role to user",
            description = "Admin users can set role executor to users by passing email in json"
    )
    @PostMapping("/executor")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> setRoleExecutorWithEmail(@RequestBody UserDto userDto) {
        userService.setRoleExecutor(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
