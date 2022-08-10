package org.oka.simpletaskmanager.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.oka.simpletaskmanager.model.SubTask;
import org.oka.simpletaskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestInstance(PER_CLASS)
public class TaskRepository_findTaskBySubTaskName_IT {
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void shouldReturnTasksMatchingName() {
        // Given
        Task taxes = Task.builder().name("Taxes").description("Fill the documentation of the taxes").category("FINANCES").deadline(now().plusDays(4)).build();
        taxes.setSubtasks(List.of(SubTask.builder().name("name 1").build()));
        Task garden = Task.builder().name("Garden").description("Clean and replant garden").category("DIY").deadline(now().minusDays(4)).build();
        garden.setSubtasks(List.of(SubTask.builder().name("test 1").build()));
        Task pipes = Task.builder().name("Pipes").description("Fix pipes").category("DIY").deadline(now().minusDays(4)).build();
        pipes.setSubtasks(List.of(SubTask.builder().name("name 1").build()));
        Task garden2 = Task.builder().name("Garden").description("Need to replant roses").category("DIY").deadline(now().minusDays(4)).build();
        garden2.setSubtasks(List.of(SubTask.builder().name("test 1").build()));
        taskRepository.saveAll(List.of(taxes, garden, pipes, garden2));

        // When
        Collection<Task> matching = taskRepository.findTaskBySubTaskName("test");

        // Then
        assertThat(matching).hasSizeGreaterThanOrEqualTo(2);
    }
}
