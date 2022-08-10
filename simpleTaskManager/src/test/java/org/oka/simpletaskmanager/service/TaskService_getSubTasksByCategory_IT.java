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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.oka.simpletaskmanager.model.Task.builder;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestInstance(PER_CLASS)
public class TaskService_getSubTasksByCategory_IT {
    @Autowired
    TaskService service;
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void shouldReturnAllTasks() {
        // Given
        Task taxes = builder().name("Taxes").description("Fill the documentation of the taxes").category("FINANCES").deadline(now().plusDays(4)).build();
        taxes.setSubtasks(List.of(SubTask.builder().name("Write").description("Fill up the documents").build(), SubTask.builder().name("Send").description("Send documentation by post").build()));
        Task garden = builder().name("Garden").description("Clean and replant garden").category("DIY").deadline(now().minusDays(4)).build();
        garden.setSubtasks(List.of(SubTask.builder().name("Trim").description("Trim the bushes").build(), SubTask.builder().name("Repot").description("Repot roses").build()));
        Task fixPipes = builder().name("Pipes").description("Fix the pipes").category("DIY").deadline(now().minusDays(4)).build();
        fixPipes.setSubtasks(List.of(SubTask.builder().name("Buy").description("Buy new pipes").build(), SubTask.builder().name("Install").description("Install the pipes").build()));
        taskRepository.saveAll(List.of(taxes, garden, fixPipes));

        // When
        Collection<SubTask> allTasks = service.getSubTasksByCategory("DIY");

        // Then
        List<SubTask> listSubTasks = new ArrayList<>(allTasks);
        assertThat(listSubTasks).hasSize(4);
        assertThat(listSubTasks).containsExactlyInAnyOrder(
                SubTask.builder().name("Trim").description("Trim the bushes").build(),
                SubTask.builder().name("Repot").description("Repot roses").build(),
                SubTask.builder().name("Buy").description("Buy new pipes").build(),
                SubTask.builder().name("Install").description("Install the pipes").build());
    }
}
