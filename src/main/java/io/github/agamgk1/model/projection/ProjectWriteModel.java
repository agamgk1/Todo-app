package io.github.agamgk1.model.projection;

import io.github.agamgk1.model.Project;
import io.github.agamgk1.model.ProjectSteps;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    private String description;
    //w przypadku odczytow nalezy dodawać adnotacje valid
    @Valid
    private List<ProjectSteps> steps = new ArrayList<>();

    public ProjectWriteModel() {
        steps.add(new ProjectSteps());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectSteps> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectSteps> steps) {
        this.steps = steps;
    }

    public Project toProject() {
        var result = new Project();
        result.setDescription(description);
        //zeby zapisał się w bazie
        steps.forEach(projectSteps -> projectSteps.setProject(result));
        result.setProjectSteps(new HashSet<>(steps));
        return result;
    }
}
