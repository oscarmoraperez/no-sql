package org.oka.simpletaskmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.oka.simpletaskmanager.model.SubTask;
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
import static org.oka.simpletaskmanager.model.Task.builder;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestInstance(PER_CLASS)
public class TaskService_addSubTask_IT {
    @Autowired
    TaskService service;
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void shouldAddSubTask() {
        // Given
        Task taxes = builder().name("Taxes").description("Fill the documentation of the taxes").category("FINANCES").deadline(now().plusDays(4)).build();
        SubTask subTask = SubTask.builder().name("subtask name").description("subtask description").build();
        taxes.getSubtasks().add(subTask);
        Task persisted = service.createTask(taxes);

        // When
        Task task = service.addSubTask(persisted, SubTask.builder().name("subtask name 2").description("subtask description 2").build());

        // Then
        assertThat(task.getSubtasks()).contains(SubTask.builder().name("subtask name 2").description("subtask description 2").build());
        Task persistedWSubTask = taskRepository.findById(persisted.getId()).orElseThrow();
        assertThat(persistedWSubTask).isNotNull();
        assertThat(persistedWSubTask.getSubtasks()).hasSize(2);
    }
}
