package peter.alekseychuk.TaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peter.alekseychuk.TaskManagementSystem.model.Executor;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExecutorRepository extends JpaRepository<Executor, UUID> {
    Optional<Executor> findByLastname(String lastName);
    Optional<Executor> findById(UUID id);
}
