package br.com.jdsb.todolistapi.service;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.dto.TaskDTO;
import br.com.jdsb.todolistapi.model.entity.Task;

import java.util.List;

public interface TaskService {

    public Task create(TaskDTO taskDTO);

    public List<Task> findAllTasksOrdened();

    public List<Task> findByStatus(TaskStatus status);

    public Task update(TaskDTO taskDTO);

    public void delete(Long id);
}
