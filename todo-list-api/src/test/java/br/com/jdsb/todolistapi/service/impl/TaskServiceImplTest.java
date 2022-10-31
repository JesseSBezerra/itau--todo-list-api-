package br.com.jdsb.todolistapi.service.impl;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.dto.TaskDTO;
import br.com.jdsb.todolistapi.model.dto.UserDTO;
import br.com.jdsb.todolistapi.model.entity.Task;
import br.com.jdsb.todolistapi.model.entity.User;
import br.com.jdsb.todolistapi.repository.TaskRepository;
import br.com.jdsb.todolistapi.service.exception.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class TaskServiceImplTest {

    private static final Integer INDEX   = 0;
    public static final long ID = 1L;
    public static final String USER_NAME = "test";
    public static final String PASSWORD = "teste";
    public static final boolean IS_SUPER = true;
    public static final String DESCRIPTION = "Test of Task Using Mokito";
    public static final LocalDateTime NOW = LocalDateTime.of(2022,10,30,15,9,9);
    public static final String TASK_NOT_FOUND = "Task Not Found";
    @InjectMocks
    private TaskServiceImpl service;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private TaskRepository repository;
    @Mock
    private ModelMapper mapper;

    private Task task;

    private TaskDTO taskDTO;

    private User superUser;
    private User user;

    private Optional<Task> optionalTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpData();
    }

    @Test
    void create() {
        when(repository.save(any())).thenReturn(task);
        when(mapper.map(any(), any())).thenReturn(task);
        when(userService.getSessionUser()).thenReturn(user);
        var response = service.create(taskDTO);
        assertNotNull(response);
        assertEquals(ID, task.getId());
        assertEquals(user,task.getUser());
        assertEquals(DESCRIPTION,task.getDescription());
        assertEquals(NOW,task.getUpdatedAt());
        assertEquals(TaskStatus.PENDING,task.getStatus());
    }

    @Test
    void findAllTasksOrdened() {
        when(userService.getSessionUser()).thenReturn(superUser);
        when(repository.findByOrderByStatusDescCreatedAtDesc()).thenReturn(List.of(task));
        var tasks = service.findAllTasksOrdened();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(Task.class, tasks.get(INDEX).getClass());

        assertEquals(ID, tasks.get(INDEX).getId());
        assertEquals(user, tasks.get(INDEX).getUser());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getDescription());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getSummary());
        assertEquals(NOW, tasks.get(INDEX).getCreatedAt());
        assertEquals(NOW, tasks.get(INDEX).getUpdatedAt());
        assertEquals(TaskStatus.PENDING, tasks.get(INDEX).getStatus());
    }

    @Test
    void findAllTasksOrdenedByNotSuperUser() {
        when(userService.getSessionUser()).thenReturn(user);
        when(repository.findByUserOrderByStatusDesc(any())).thenReturn(List.of(task));
        var tasks = service.findAllTasksOrdened();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(Task.class, tasks.get(INDEX).getClass());

        assertEquals(ID, tasks.get(INDEX).getId());
        assertEquals(user, tasks.get(INDEX).getUser());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getDescription());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getSummary());
        assertEquals(NOW, tasks.get(INDEX).getCreatedAt());
        assertEquals(NOW, tasks.get(INDEX).getUpdatedAt());
        assertEquals(TaskStatus.PENDING, tasks.get(INDEX).getStatus());
    }

    @Test
    void findByStatus() {
        when(userService.getSessionUser()).thenReturn(superUser);
        when(repository.findByStatusOrderByUpdatedAtDescCreatedAtDesc(any())).thenReturn(List.of(task));
        var tasks = service.findByStatus(TaskStatus.PENDING);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(Task.class, tasks.get(INDEX).getClass());

        assertEquals(ID, tasks.get(INDEX).getId());
        assertEquals(user, tasks.get(INDEX).getUser());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getDescription());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getSummary());
        assertEquals(NOW, tasks.get(INDEX).getCreatedAt());
        assertEquals(NOW, tasks.get(INDEX).getUpdatedAt());
        assertEquals(TaskStatus.PENDING, tasks.get(INDEX).getStatus());
    }

    @Test
    void findByStatusByNotSuperUser() {
        when(userService.getSessionUser()).thenReturn(user);
        when(repository.findByUserAndStatusOrderByCreatedAtDesc(any(),any())).thenReturn(List.of(task));
        var tasks = service.findByStatus(TaskStatus.PENDING);
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(Task.class, tasks.get(INDEX).getClass());

        assertEquals(ID, tasks.get(INDEX).getId());
        assertEquals(user, tasks.get(INDEX).getUser());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getDescription());
        assertEquals(DESCRIPTION, tasks.get(INDEX).getSummary());
        assertEquals(NOW, tasks.get(INDEX).getCreatedAt());
        assertEquals(NOW, tasks.get(INDEX).getUpdatedAt());
        assertEquals(TaskStatus.PENDING, tasks.get(INDEX).getStatus());
    }

    @Test
    void update() {
        when(userService.getSessionUser()).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(task);
        when(repository.findByIdAndUser(anyLong(),any())).thenReturn(optionalTask);
        when(repository.save(any())).thenReturn(task);
        /*
        var response = service.update(taskDTO);
        assertNotNull(response);
        assertEquals(ID, task.getId());
        assertEquals(user,task.getUser());
        assertEquals(ID,task.getUser().getId());
        assertEquals(DESCRIPTION,task.getDescription());
        assertEquals(NOW,task.getUpdatedAt());
        assertEquals(TaskStatus.PENDING,task.getStatus());

         */

    }

    @Test
    void delete() {
        when(userService.getSessionUser()).thenReturn(user);
        when(repository.findByIdAndUser(anyLong(),any())).thenReturn(optionalTask);
        doNothing().when(repository).delete(any());
        service.delete(ID);
        verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteBySuperUser() {
        when(userService.getSessionUser()).thenReturn(superUser);
        when(repository.findById(anyLong())).thenReturn(optionalTask);
        doNothing().when(repository).delete(any());
        service.delete(ID);
        verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteThenReturnTaskNotFoundException() {
        when(userService.getSessionUser()).thenReturn(user);
        when(repository.findByIdAndUser(anyLong(),any())).thenThrow(new TaskNotFoundException(TASK_NOT_FOUND));
        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(TaskNotFoundException.class, ex.getClass());
            assertEquals(TASK_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void deleteBySuperUserThenReturnTaskNotFoundException() {
        when(userService.getSessionUser()).thenReturn(superUser);
        when(repository.findById(anyLong())).thenThrow(new TaskNotFoundException(TASK_NOT_FOUND));
        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(TaskNotFoundException.class, ex.getClass());
            assertEquals(TASK_NOT_FOUND, ex.getMessage());
        }
    }

    private void setUpData(){
       superUser = new User(ID, USER_NAME, PASSWORD, IS_SUPER);
       user = new User(ID, USER_NAME, PASSWORD, false);
       task = new Task(ID,user, DESCRIPTION, DESCRIPTION, NOW, NOW,TaskStatus.PENDING);
       UserDTO userDTO = new UserDTO(ID, USER_NAME);
       taskDTO = new TaskDTO(ID,userDTO, DESCRIPTION, DESCRIPTION, NOW, NOW,TaskStatus.PENDING);
       optionalTask = Optional.of(task);
    }
}