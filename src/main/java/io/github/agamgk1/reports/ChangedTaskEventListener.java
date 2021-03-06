package io.github.agamgk1.reports;

import io.github.agamgk1.model.event.TaskDone;
import io.github.agamgk1.model.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ChangedTaskEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }
    //dzieki Async bedzie wykonywało sie w oddzielnym watku
    @Async
    @EventListener
    public void on(TaskDone event) {
        LOGGER.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }
    @Async
    @EventListener
    public void on(TaskUndone event) {
        LOGGER.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }
}
