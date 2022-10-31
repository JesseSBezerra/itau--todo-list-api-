package br.com.jdsb.todolistapi.service.impl;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import br.com.jdsb.todolistapi.model.dto.TaskDTO;
import br.com.jdsb.todolistapi.model.dto.UserDTO;
import br.com.jdsb.todolistapi.model.entity.Task;
import br.com.jdsb.todolistapi.repository.TaskRepository;
import br.com.jdsb.todolistapi.service.TaskService;
import br.com.jdsb.todolistapi.service.exception.TaskNotFoundException;
import br.com.jdsb.todolistapi.service.exception.UserNotInformedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Task create(TaskDTO taskDTO) {
        var task = mapper.map(taskDTO,Task.class);
        task.setCreatedAt(LocalDateTime.now());
        var user = userService.getSessionUser();
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasksOrdened() {
        var user = userService.getSessionUser();
        if(user.getIsSuper()){
            return taskRepository.findByOrderByStatusDescCreatedAtDesc();
        }
        return taskRepository.findByUserOrderByStatusDesc(user);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        var user = userService.getSessionUser();
        if(user.getIsSuper()){
          return taskRepository.findByStatusOrderByUpdatedAtDescCreatedAtDesc(status);
        }else{
            return taskRepository.findByUserAndStatusOrderByCreatedAtDesc(user,status);
        }
    }

    @Override
    public Task update(TaskDTO taskDTO) {
        if(Objects.isNull(taskDTO.getUser())){
            throw new UserNotInformedException("Not Informed User!");
        }
        taskDTO.setUpdatedAt(LocalDateTime.now());
        Task task = getTask(taskDTO.getId());
        taskDTO.setUser(mapper.map(task.getUser(), UserDTO.class));
        return taskRepository.save(mapper.map(taskDTO,Task.class));
    }

    @Override
    public void delete(Long id) {
        var task = getTask(id);
        taskRepository.delete(task);
    }

    private Task getTask(Long id){
        Task task = null;
        var user = userService.getSessionUser();
        if(user.getIsSuper()){
           task = taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("Task Not Found"));
        }else{
            task = taskRepository.findByIdAndUser(id,user).orElseThrow(()-> new TaskNotFoundException("Task Not Found"));
        }
        return task;
    }


}
