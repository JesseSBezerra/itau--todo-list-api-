package br.com.jdsb.todolistapi.repository;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.entity.Task;
import br.com.jdsb.todolistapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByUserOrderByStatusDesc(User user);

    Optional<Task> findByIdAndUser(Long id,User user);

    List<Task> findByOrderByStatusDescCreatedAtDesc();

    List<Task> findByUserAndStatusOrderByCreatedAtDesc(User user, TaskStatus status);

    List<Task> findByStatusOrderByUpdatedAtDescCreatedAtDesc(TaskStatus status);

}
