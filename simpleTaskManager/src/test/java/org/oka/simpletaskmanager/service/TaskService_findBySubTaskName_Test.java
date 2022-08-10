package org.oka.simpletaskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.simpletaskmanager.repository.TaskRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskService_findBySubTaskName_Test {
    @InjectMocks
    TaskService taskService;
    @Mock
    TaskRepository taskRepository;

    @Test
    public void shouldCallTaskRepository() {
        // Given

        // When
        taskService.findBySubTaskName("example");

        // Then
        verify(taskRepository).findTaskBySubTaskName("example");
    }
}
