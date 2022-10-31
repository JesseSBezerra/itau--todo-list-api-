package br.com.jdsb.todolistapi.resource.impl;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.dto.TaskDTO;
import br.com.jdsb.todolistapi.model.dto.UserDTO;
import br.com.jdsb.todolistapi.model.entity.Task;
import br.com.jdsb.todolistapi.model.entity.User;
import br.com.jdsb.todolistapi.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class TaskResourceImplTest {

    @InjectMocks
    private TaskResourceImpl resource;

    @Mock
    private TaskServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private static final Integer INDEX   = 0;
    public static final long ID = 1L;
    public static final String USER_NAME = "test";
    public static final String PASSWORD = "teste";
    public static final boolean IS_SUPER = true;
    public static final String DESCRIPTION = "Test of Task Using Mokito";
    public static final LocalDateTime NOW = LocalDateTime.of(2022,10,30,15,9,9);

    private Task task;
    private User user;
    private TaskDTO taskDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpData();
    }

    @Test
    void create() {
        when(service.create(any())).thenReturn(task);
        when(mapper.map(any(), any())).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> response = resource.create(taskDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void findAllTasks() {

        when(service.findAllTasksOrdened()).thenReturn(List.of(task));
        when(mapper.map(any(), any())).thenReturn(taskDTO);

        ResponseEntity<List<TaskDTO>> response = resource.findAllTasks();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(TaskDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(ID, response.getBody().get(INDEX).getUser().getId());
        assertEquals(USER_NAME, response.getBody().get(INDEX).getUser().getUserName());
        assertEquals(DESCRIPTION, response.getBody().get(INDEX).getDescription());
        assertEquals(DESCRIPTION, response.getBody().get(INDEX).getSummary());
        assertEquals(NOW, response.getBody().get(INDEX).getUpdatedAt());
        assertEquals(NOW, response.getBody().get(INDEX).getCreatedAt());
        assertEquals(TaskStatus.PENDING, response.getBody().get(INDEX).getStatus());


    }

    @Test
    void findAllTasksByStatus() {
        when(service.findByStatus(TaskStatus.PENDING)).thenReturn(List.of(task));
        when(mapper.map(any(), any())).thenReturn(taskDTO);

        ResponseEntity<List<TaskDTO>> response = resource.findAllTasksByStatus(TaskStatus.PENDING);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(TaskDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(ID, response.getBody().get(INDEX).getId());
    }

    @Test
    void update() {
        when(service.update(taskDTO)).thenReturn(task);
        when(mapper.map(any(), any())).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = resource.update(ID, taskDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TaskDTO.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }

    @Test
    void delete() {
        doNothing().when(service).delete(anyLong());

        ResponseEntity<TaskDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
    }

    private void setUpData(){
        user = new User(ID, USER_NAME, PASSWORD, false);
        task = new Task(ID,user, DESCRIPTION, DESCRIPTION, NOW, NOW, TaskStatus.PENDING);
        userDTO = new UserDTO(ID, USER_NAME);
        taskDTO = new TaskDTO(ID,userDTO, DESCRIPTION, DESCRIPTION, NOW, NOW,TaskStatus.PENDING);

    }
}