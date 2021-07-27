package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Todo;
import org.example.model.TodoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.repository.TodoRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepository todoRepository;

    public Todo add(TodoRequest request) {
        Todo entity = new Todo();
        entity.setTitle(request.getTitle());
        entity.setOrder(request.getOrder());
        entity.setCompleted(request.getCompleted());
        return this.todoRepository.save(entity);
    }

    public Todo searchById(Long id) {
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 404
    }

    public List<Todo> searchAll() {
        return this.todoRepository.findAll();
    }

    public Todo updateById(Long id, TodoRequest request) {
        Todo todo = this.searchById(id);
        if(request.getTitle() != null) todo.setTitle(request.getTitle());
        if(request.getOrder() != null) todo.setOrder(request.getOrder());
        if(request.getCompleted() != null) todo.setCompleted(request.getCompleted());
        return this.todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }

    public void deleteAll() {
        this.todoRepository.deleteAll();
    }
}
