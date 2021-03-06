package io.github.agamgk1.logic;

import io.github.agamgk1.TaskConfigurationProperties;
import io.github.agamgk1.model.*;
import io.github.agamgk1.model.projection.GroupReadModel;
import io.github.agamgk1.model.projection.GroupWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// serwisy aranzują akcje na kilku roznych repozytoriach
//klasy pomocnicze pozwalajace zachowac walidacje encji
//Serwis to warstwa pośrednia miedzy repozytorium a kontrolerem lub dwoma repozytoriami
@Service
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;

    }

    //metoda tworząca grupe z write modelu
    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source,null);
    }

    public GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

        public List<GroupReadModel> readAll() {
       return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
//nie mozna zamknąć grupy (done = true) jezeli wszystkie taski w obrebie grupy nie sa done
    public void toggleGroup(int groupId) {
       if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
           throw new IllegalStateException("Group has undone tasks, done all the tasks first");
       }
       TaskGroup result = repository.findById(groupId)
               .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }

}

