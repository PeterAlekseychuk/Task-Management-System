package peter.alekseychuk.TaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peter.alekseychuk.TaskManagementSystem.model.Author;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByLastname(String lastName);

    Optional<Author> findById(UUID id);
}
