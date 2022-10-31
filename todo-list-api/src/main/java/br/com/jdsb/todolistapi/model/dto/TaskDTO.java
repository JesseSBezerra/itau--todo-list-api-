package br.com.jdsb.todolistapi.model.dto;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private UserDTO user;
    private String description;
    private String summary;
    private LocalDateTime updatedAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    private TaskStatus status;

}
