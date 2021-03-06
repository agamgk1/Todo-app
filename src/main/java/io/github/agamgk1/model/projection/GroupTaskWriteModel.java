package io.github.agamgk1.model.projection;

import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
//DTO - obiekt sluzacy do przesyłania danych, nie udostapniajac wiecej niz potrzeba, zeby nie uszkodzic wynikowego Taska
//task w obrebie grupy ktory jest do zapisania
//korzystamy tylko z decscription i deadline. Done bedzie automatycznie ustawione na false
public class GroupTaskWriteModel {
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    //tworzenie rzeczywistego taska
    public Task toTask(TaskGroup group) {
        return new Task(description, deadline, group);
    }
}
