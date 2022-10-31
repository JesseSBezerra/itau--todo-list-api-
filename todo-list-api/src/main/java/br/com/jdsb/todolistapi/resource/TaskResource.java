package br.com.jdsb.todolistapi.resource;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.dto.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/task")
public interface TaskResource {

    @PostMapping
    ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto);

    @GetMapping()
    ResponseEntity<List<TaskDTO>> findAllTasks();

    @GetMapping(value = "/status")
    ResponseEntity<List<TaskDTO>> findAllTasksByStatus(@RequestParam(defaultValue = "PENDING") TaskStatus status);

    @PutMapping(value = "/{id}")
    ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO dto);

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TaskDTO> delete(@PathVariable Long id);
}
