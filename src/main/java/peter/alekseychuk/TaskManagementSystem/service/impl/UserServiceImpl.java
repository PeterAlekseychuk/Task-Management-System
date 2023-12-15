package peter.alekseychuk.TaskManagementSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peter.alekseychuk.TaskManagementSystem.model.Role;
import peter.alekseychuk.TaskManagementSystem.model.User;
import peter.alekseychuk.TaskManagementSystem.repository.UserRepository;
import peter.alekseychuk.TaskManagementSystem.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }
    public void setAuthorRole(User user) {
        user.setRole(Role.AUTHOR);
    }

    public void setExecutorRole(User user) {
        user.setRole(Role.EXECUTOR);
    }
}
