package io.github.agamgk1.controller;

import io.github.agamgk1.logic.TaskGroupService;
import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskRepository;
import io.github.agamgk1.model.projection.GroupReadModel;
import io.github.agamgk1.model.projection.GroupTaskWriteModel;
import io.github.agamgk1.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@IllegalExceptionProcessing
@Controller
@RequestMapping("/groups")
class TaskGroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskGroupController.class);
    private TaskRepository taskRepository;
    private TaskGroupService taskGroupService;

    public TaskGroupController(TaskRepository taskRepository, TaskGroupService taskGroupService) {
        this.taskRepository = taskRepository;
        this.taskGroupService = taskGroupService;
    }

    @GetMapping(produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group",new GroupWriteModel());
        return "groups";
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @ResponseBody
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTaskFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }
    //dzieki temu te motody odpala sie tylko przez thymeleafa (HTML) - produces
    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model) {
        if(bindingResult.hasErrors()) {
            return "groups";
        }
        taskGroupService.createGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupę");
        return "groups";
    }

    @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @ResponseBody
    @Transactional
    @PatchMapping("/{id}")
    ResponseEntity<GroupReadModel> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build(); //202
    }
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
       var result = taskGroupService.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    @ModelAttribute("groups")
    public List<GroupReadModel> getGroups() {
        return taskGroupService.readAll();
    }
}
