package io.github.agamgk1.model;

import io.github.agamgk1.model.event.TaskEvent;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

//GeneratedValue - generuje automatycznie kolejnego taska czyli taks/0 , task/1 itp

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task description must not be empty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    //wiele tasków może trafic do jednej grupy
    // Join Column - po tej kollumnie dołączamy dane
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

   public Task() {
    }
    public Task(String description, LocalDateTime deadline) {
       this(description,deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        this.group = group;
    }


    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public TaskEvent toggle() {
        this.done = !this.done;
        return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(TaskGroup group) {
        this.group = group;
    }

    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }

    //PrePresist metoda wykona sie przed zapisaem do bazy danych
    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }
    //wykona sie przed aktualizacja w bazie danych
    @PreUpdate
    void preMarge() {
        updatedOn = LocalDateTime.now();
    }
}
