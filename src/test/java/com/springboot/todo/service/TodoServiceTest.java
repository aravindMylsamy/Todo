package com.springboot.todo.service;

import com.springboot.todo.exception.DuplicateEntryException;
import com.springboot.todo.model.Todo;
import com.springboot.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    public void setup(){
       todo = new Todo("Todo1", "Todo 1 description");
    }

    @Test
    public void givenTodoObjectWhenSaveTodoThenReturnTodoObject() throws DuplicateEntryException {
        given(todoRepository.findByName(todo.getName()))
                .willReturn(Optional.empty());
        given(todoRepository.save(todo)).willReturn(todo);

        Todo savedTodo = todoService.addTodo(todo);

        assertThat(savedTodo).isNotNull();
    }

    @Test
    public void givenExistingEmailWhenSaveTodoThenThrowsException(){
        given(todoRepository.findByName(todo.getName()))
                .willReturn(Optional.of(todo));


        assertThrows(DuplicateEntryException.class, () ->
            todoService.addTodo(todo)
        );

        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    public void givenTodosListWhenGetAllTodosThenReturnTodosList(){
       Todo todo1 = new Todo("Todo1", "Todo 1 description");
        given(todoRepository.findAll()).willReturn(List.of(todo,todo1));

        List<Todo> TodoList = todoService.getAllTodos();

        assertThat(TodoList).isNotNull();
        assertThat(TodoList.size()).isEqualTo(2);
    }

    @Test
    public void givenTodoIdWhenGetTodoByIdThenReturnTodoObject(){
        given(todoRepository.findById(0)).willReturn(Optional.of(todo));

        Todo todo = todoService.getTodoById(0);
        Todo savedTodo = todoService.getTodoById(todo.getId());

        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo).isEqualTo(todo);
    }


    @Test
    public void givenTodoObjectWhenUpdateTodoThenReturnUpdatedTodo()  {
        given(todoRepository.save(todo)).willReturn(todo);
        given(todoRepository.findById(0)).willReturn(Optional.of(todo));
        todo.setDescription("Updated description");

        Todo updatedTodo = todoService.updateTodo(0,todo);

        assertThat(updatedTodo.getDescription()).isEqualTo("Updated description");
    }

    @Test
    public void givenTodoIdWhenDeleteTodoThenReturnNothing(){
        int todoId = 1;

        willDoNothing().given(todoRepository).deleteById(todoId);

        todoService.deleteTodo(todoId);

        verify(todoRepository, times(1)).deleteById(todoId);
    }

}
