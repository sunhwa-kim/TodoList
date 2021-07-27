package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {

    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.order = todo.getOrder();
        this.completed = todo.getCompleted();

        // base url 변경 or port 변경시마다 별도 작업  -> config or property
        this.url = "http://localhost:8080/" + this.id;
    }
}
