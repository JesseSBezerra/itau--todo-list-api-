package br.com.jdsb.todolistapi.resource.impl;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.dto.TaskDTO;
import br.com.jdsb.todolistapi.resource.TaskResource;
import br.com.jdsb.todolistapi.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskResourceImpl implements TaskResource {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TaskService service;

    @Override
    public ResponseEntity<TaskDTO> create(TaskDTO dto) {
        var task = service.create(dto);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequestUri().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.map(task,TaskDTO.class));
    }

    @Override
    public ResponseEntity<List<TaskDTO>> findAllTasks() {
         return ResponseEntity.ok().body(service.findAllTasksOrdened()
                .stream().map(x -> mapper.map(x,TaskDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<TaskDTO>> findAllTasksByStatus(TaskStatus status) {
        return ResponseEntity.ok().body(service.findByStatus(status)
                .stream().map(x -> mapper.map(x,TaskDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<TaskDTO> update(Long id, TaskDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(dto),TaskDTO.class));
    }

    @Override
    public ResponseEntity<TaskDTO> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
