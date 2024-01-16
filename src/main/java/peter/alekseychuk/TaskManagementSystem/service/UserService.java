package peter.alekseychuk.TaskManagementSystem.service;

import peter.alekseychuk.TaskManagementSystem.dto.UserDto;
import peter.alekseychuk.TaskManagementSystem.model.User;

import java.util.UUID;

public interface UserService {

    User findByEmail(String email);
    User findById(UUID id);
    String getCurrentUserName();

    User getCurrentUser();

    void setRoleAdmin();

    void setRoleExecutor(UserDto userDto);
}
