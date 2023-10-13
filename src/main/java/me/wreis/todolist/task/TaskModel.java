package me.wreis.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 48)
    private String title;
    private String description;
    private Boolean done;
    private LocalDateTime startAt;
    private LocalDateTime finishAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String priority;

    private UUID userId;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50) {
            throw new Exception("Title must be less than 50 characters");
        }
        this.title = title;
    }
}
