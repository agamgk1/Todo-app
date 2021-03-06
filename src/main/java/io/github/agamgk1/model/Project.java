package io.github.agamgk1.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> tasksGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectSteps> projectSteps;

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskGroup> getTasksGroups() {
        return tasksGroups;
    }

    public void setTasksGroups(Set<TaskGroup> tasksGroups) {
        this.tasksGroups = tasksGroups;
    }

    public Set<ProjectSteps> getProjectSteps() {
        return projectSteps;
    }

    public void setProjectSteps(Set<ProjectSteps> projectSteps) {
        this.projectSteps = projectSteps;
    }
}
