package io.github.agamgk1.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

//GeneratedValue - generuje automatycznie kolejnego taska czyli taks/0 , task/1 itp

@Entity
@Table(name = "task_groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task Group description must not be empty")
    private String description;
    private boolean done;
    //mappedBy - pole z klasy task gdzie jest mapowanie ManyToOne
    //Cascde - jak usuniemy grupe to usuną sie wszystkie taski z grupy
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public TaskGroup() {
    }

    public TaskGroup(@NotBlank(message = "Task Group description must not be empty") String description, Set<Task> tasks) {
        this.description = description;
        this.tasks = tasks;
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

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
