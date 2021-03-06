package io.github.agamgk1.controller;

import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//test integracyjny odpyta warstwe webowa bez dopalania serwera itp
//wstaaje caly spring
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerIntegrationTest {
    //dodadkowa klasa pomocnicza pozwala wykonywac ala zapytania i asercje np status odpowiedzie 200 404 itp
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() {
        //given
        int id = repo.save(new Task("foo", LocalDateTime.now())).getId();
        //when
        try {
            mockMvc.perform(get("/tasks/"+id))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void httpGet_returnsAllTask() {
        //given
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));
        //when
        try {
            mockMvc.perform(get("/tasks/"))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
