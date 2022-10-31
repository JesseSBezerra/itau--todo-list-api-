package br.com.jdsb.todolistapi.model.entity;

import br.com.jdsb.todolistapi.enumeration.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SUMMARY")
    private String summary;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "TASK_STATUS")
    private TaskStatus status;
}
