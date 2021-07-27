package org.example.service;

import org.example.model.Todo;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    @DisplayName("todo 입력")
    void add() {
        when(this.todoRepository.save(any(Todo.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test Title");

        Todo actual = this.todoService.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    @DisplayName("todo id 조회")
    void searchById() {
        Long testId = 1234L;
        Todo entity = new Todo();
        entity.setId(testId);
        entity.setTitle("TestTitle");
        entity.setOrder(1L);
        entity.setCompleted(false);

        Optional<Todo> newEntity = Optional.of(entity);

        given(this.todoRepository.findById(anyLong()))
                .willReturn(newEntity);

        Todo expected = newEntity.get();
        Todo actual = this.todoService.searchById(testId);

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getTitle(), actual.getTitle()),
                () -> assertEquals(expected.getCompleted(), actual.getCompleted()),
                () -> assertEquals(expected.getOrder(), actual.getOrder())
        );
    }

    @Test
    @DisplayName("todo id 조회 실패")
    void searchByIdFailed() {
        given(this.todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            this.todoService.searchById(1234L);
        });
    }


}