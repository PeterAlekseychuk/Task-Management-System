package peter.alekseychuk.TaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peter.alekseychuk.TaskManagementSystem.model.Task;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Modifying
    @Query("update Task task set task.status = :status where task.id = :id")
    void setStatusForTask(@Param("status") String status, @Param("id") UUID id);

//    void updateTaskByExecutor(Executor executor);
}
