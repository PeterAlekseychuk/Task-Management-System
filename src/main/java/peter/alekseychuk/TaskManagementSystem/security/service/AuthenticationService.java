package peter.alekseychuk.TaskManagementSystem.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peter.alekseychuk.TaskManagementSystem.model.Role;
import peter.alekseychuk.TaskManagementSystem.model.User;
import peter.alekseychuk.TaskManagementSystem.repository.UserRepository;
import peter.alekseychuk.TaskManagementSystem.security.AuthenticationRequest;
import peter.alekseychuk.TaskManagementSystem.security.AuthenticationResponse;
import peter.alekseychuk.TaskManagementSystem.security.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse signIn(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
//    public User getUserByUsername(String username) {
//        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
//        Root<User> userRequest = criteriaQuery.from(User.class);
//
//        Expression<String> exp = userRequest.get("username");
//        Predicate predicate = exp.in(username);
//
//        criteriaQuery.where(predicate);
//        try {
//            return em.createQuery(criteriaQuery).getSingleResult();
//        } catch (NoResultException e) {
//            return new User();
//        }
//    }
}
