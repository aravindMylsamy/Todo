package com.springboot.todo;

import com.springboot.todo.model.Todo;
import com.springboot.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplication {

	@Autowired
	private TodoRepository todoRepository;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
		Todo todo = new Todo("Todo 1", "This is todo 1");
	}

}
