package io.github.agamgk1.controller;

import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//lekki test integracyjny. Plus podanie kontrolera z ktorego korzystamy
@WebMvcTest(TaskController.class)

public class TaskControllerLightIntegrationTest {
    //dodadkowa klasa pomocnicza pozwala wykonywac ala zapytania i asercje np status odpowiedzie 200 404 itp
    @Autowired
    private MockMvc mockMvc;
    @MockBean //poniej dzieki temu juz mozna uzywac metod z mock na when()
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() {
        //given
        String description = "foo";
        when(repo.findById(anyInt()))
            .thenReturn(Optional.of(new Task(description, LocalDateTime.now())));

        //when
        try {
            mockMvc.perform(get("/tasks/123"))
                    .andDo(print())
                    .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString(description)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
