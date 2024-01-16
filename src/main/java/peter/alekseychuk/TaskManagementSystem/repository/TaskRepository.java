package peter.alekseychuk.TaskManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peter.alekseychuk.TaskManagementSystem.model.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(
            nativeQuery = true,
            value = "select task.id,header,description,status,priority, author_id, executor_id from task" +
                    " join _user on author_id=_user.id where author_id=:userID"
    )
    Page<Task> findAllAuthorTasksByUserId(@Param("userID")UUID userID, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select task.id,header,description,status,priority, author_id, executor_id  from task" +
                    " join _user on executor_id=_user.id where executor_id=:userID"
    )
    Page<Task> findAllExecutorTasksByUserId(@Param("userID")UUID userID, Pageable pageable);

}
