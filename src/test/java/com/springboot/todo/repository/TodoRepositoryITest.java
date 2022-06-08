package com.springboot.todo.repository;

import com.springboot.todo.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryITest {

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    void givenTodoObjectWhenSaveThenReturnSavedTodo() {
        //given - setup
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");

        //when - behaviour we are going to test
        Todo savedTodo = todoRepository.save(todo);

        //then - assert it
        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo.getId()).isGreaterThan(0);
    }

    @Test
    public void givenTodosListWhenFindAllThenReturnTodosList() {
        // given - setup
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        Todo todo1 = new Todo("Spring-Boot-1", "Learn Spring Boot");
        todoRepository.save(todo);
        todoRepository.save(todo1);

        // when -  behaviour we are going test
        List<Todo> todoList = todoRepository.findAll();

        // then - assert
        assertThat(todoList).isNotNull();
        assertThat(todoList.size()).isEqualTo(2);
    }

    @Test
    public void givenTodoObjectWhenFindByIdThenReturnTodoObject() {
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        todoRepository.save(todo);

        Todo todoBB = todoRepository.findById(todo.getId()).get();

        assertThat(todoBB).isNotNull();
        assertThat(todoBB).isEqualTo(todo);
    }

    @Test
    public void givenTodoObjectWhenUpdateTodoThenReturnUpdatedTodo() {
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        todoRepository.save(todo);

        Todo savedTodo = todoRepository.findById(todo.getId()).get();
        savedTodo.setDescription("Updated Description");
        Todo updatedTodo = todoRepository.save(savedTodo);

        assertThat(updatedTodo.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    public void givenTodoObjectWhenDeleteThenRemoveTodo() {
        Todo todo = new Todo("Spring-Boot", "Learn Spring Boot");
        todoRepository.save(todo);

        todoRepository.deleteById(todo.getId());
        Optional<Todo> todoOptional = todoRepository.findByName(todo.getName());

        assertThat(todoOptional).isEmpty();
    }

}
