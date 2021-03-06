package io.github.agamgk1.logic;

import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskRepository;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    private final TaskRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
    //metoda pokazowa do asynchronicznego pobierania taksow (asynch - w innym wątku). Trzeba uzyc CompFuture
    //dodatkowo trzeba uruchomic asynchronicznosc w klasie konfigurujacej @EnableAsync
    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        LOGGER.info("supply async");
        return CompletableFuture.supplyAsync(repository::findAll);
    }
}
