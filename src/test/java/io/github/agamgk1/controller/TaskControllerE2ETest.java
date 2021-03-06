package io.github.agamgk1.controller;

import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;

//W testach E2E wstaje cała aplikacja @SpringBootTest
//kozysta z drugiego repozytorium
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;

    //słuzy odpytywania innych usług
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks() {
         repo.save(new Task("foo", LocalDateTime.now()));
         repo.save(new Task("bar", LocalDateTime.now()));
         
         //when
        Task[] result = restTemplate.getForObject("http://localhost:"+port +"/tasks",Task[].class);
        //then
        assertThat(result).hasSize(2);
    }
    @Test
    void httpGet_returnsGivenTask() {
        //given
        Task task = new Task("foo", LocalDateTime.of(10,10,10,10,10,10));
        repo.save(task);
        int id = task.getId();
        //when
        Task result = restTemplate.getForObject("http://localhost:"+port +"/tasks/"+id,Task.class);
        assertThat(result.getDescription()).isEqualTo(task.getDescription());
        assertThat(result.getDeadline()).isEqualTo(task.getDeadline());

    }

}
