package io.github.agamgk1.controller;

import io.github.agamgk1.logic.TaskService;
import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

//oznaczenie kontrolera repozytorium TaskRepository
@RestController
@RequestMapping("/tasks")
class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final ApplicationEventPublisher eventPublisher;
    private final TaskRepository repository;
    private final TaskService service;

    public TaskController(ApplicationEventPublisher eventPublisher, TaskRepository repository, TaskService service) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
        this.service = service;
    }
    //response entity reprezentuje odpowiedz
    //request mapping wskazuje ze ta metoda jest uzywana zamiast metody (get) z repozytorium
    @GetMapping(params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        LOGGER.warn("Exposing all tasks");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping()
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        LOGGER.info("Custom pager");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }
    //requestrparam - parametr ządnia. Boolean bo done jest boolean. Trzeba ustawic default value
    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByDone(state));
    }

    @PostMapping()
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
       Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    //requestBody zeby pobrac taska
    //aktualizacja taska
    @PutMapping("/{id}")
    ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build(); //404
        }
        repository.findById(id)
                .ifPresent(task -> { task.updateFrom(toUpdate);
        repository.save(task);
    });
        return ResponseEntity.noContent().build(); //202
    }
    //transactional - jezeli ktos z zwenatrz wywoła tą metodę to ta zmiania zostanie zapisana i zacommitowana do bazy danych, metoda musi byc public
    //aktualizacja pola done (zamiana na wartość przeciwną)
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Task> toggleTask(@PathVariable int id) {
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build(); //404
        }
        repository.findById(id)
                .map(Task::toggle)
                .ifPresent(eventPublisher::publishEvent);
        return ResponseEntity.noContent().build(); //202
    }

}
