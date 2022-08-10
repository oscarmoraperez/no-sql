package org.oka.simpletaskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.oka.simpletaskmanager.model.Task;
import org.oka.simpletaskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestInstance(PER_CLASS)
public class TaskService_updateTask_IT {
    @Autowired
    TaskService service;
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void shouldUpdateTask() {
        // Given
        Task taxes = Task.builder().name("Taxes").description("Fill the documentation of the taxes").category("FINANCES").deadline(now().plusDays(4)).build();
        Task persisted = service.createTask(taxes);

        // When
        persisted.setName("Taxes 2");
        Task persisted2 = service.updateTask(persisted);

        // Then
        assertThat(persisted2.getName()).isEqualTo("Taxes 2");
        assertThat(taskRepository.findById(persisted.getId()).orElseThrow().getName()).isEqualTo("Taxes 2");
    }
}
