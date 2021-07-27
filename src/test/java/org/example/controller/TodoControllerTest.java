package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Todo;
import org.example.model.TodoRequest;
import org.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private Todo expected;
    private long testId = 1234L;

    @BeforeEach
    void setUp() {
        expected = new Todo();
        expected.setId(testId);
        expected.setTitle("TEST title");
        expected.setOrder(2L);
        expected.setCompleted(false);
    }

    @Test
    @DisplayName("todo 저장 요청")
    void create() throws Exception {
        when(this.todoService.add(any(TodoRequest.class)))
                .then((i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new Todo(this.expected.getId(),
                            this.expected.getTitle(),
                            this.expected.getOrder(),
                            this.expected.getCompleted());
                });
        TodoRequest request = new TodoRequest();
        String testTitle = "TEST change TITLE";
        request.setTitle(testTitle);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(request);

        this.mvc.perform(post("/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testTitle));
    }

    @Test
    @DisplayName("todo 1개 요청")
    void readOne() throws Exception {
        when(this.todoService.searchById(testId))
                .thenReturn(expected);

        this.mvc.perform(get("/{id}",testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.title").value(expected.getTitle()))
                .andExpect(jsonPath("$.order").value(expected.getOrder()))
                .andExpect(jsonPath("$.completed").value(expected.getCompleted()));
    }
}