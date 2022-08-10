package org.oka.simpletaskmanager;

import org.oka.simpletaskmanager.model.SubTask;
import org.oka.simpletaskmanager.model.Task;
import org.oka.simpletaskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;

@SpringBootApplication
@EnableMongoRepositories
@Profile("dev")
public class AppTaskManager implements CommandLineRunner {
    @Autowired
    TaskService taskService;

    public static void main(String[] args) {
        SpringApplication.run(AppTaskManager.class, args);
    }

    /**
     * In addition to the Unit and Integration tests, the following 'run' method creates, persists, updates and searches different Tasks.
     * <p>
     * Attention! remember to start MongoDB docker image, by moving to /docker directory and executing: docker-compose up -d
     * <p>
     * In any time, browse localhost:8081 for direct access to mongoDB-express instance
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        taskService.deleteAll();
        System.out.println("Starting set of use cases / demo");

        Task taxes = Task.builder().name("Taxes").description("Fill the documentation of the taxes").category("FINANCES").deadline(now().plusDays(4)).build();
        SubTask taxes1 = SubTask.builder().name("Fill").description("Fill the documents").build();
        SubTask taxes2 = SubTask.builder().name("Sending").description("Go to post to send the documents").build();
        taxes.setSubtasks(asList(taxes1, taxes2));
        Task garden = Task.builder().name("Garden").description("Trim the roses").category("HOUSE").deadline(now().minusDays(4)).build();
        SubTask garden1 = SubTask.builder().name("Tools").description("Prepare tools").build();
        SubTask garden2 = SubTask.builder().name("Trimming").description("Trim the roses").build();
        SubTask garden3 = SubTask.builder().name("Cleaning").description("Clean the rest").build();
        garden.setSubtasks(asList(garden1, garden2, garden3));
        Task pipes = Task.builder().name("Pipes").description("Fix WC pipes").category("HOUSE").deadline(now().minusDays(10)).build();
        SubTask pipes1 = SubTask.builder().name("Tools").description("Prepare tools").build();
        SubTask pipes2 = SubTask.builder().name("Trimming").description("Trim the roses").build();
        SubTask pipes3 = SubTask.builder().name("Cleaning").description("Clean the rest").build();
        pipes.setSubtasks(asList(pipes1, pipes2, pipes3));

        System.out.println("Persisting tasks");
        System.out.println("=====================");
        Task savedTaxes = taskService.createTask(taxes);
        Task savedGarden = taskService.createTask(garden);
        Task savedPipes = taskService.createTask(pipes);
        System.out.println();

        System.out.println("Displaying all tasks");
        System.out.println("=====================");
        taskService.getAllTasks().forEach(System.out::println);
        System.out.println();

        System.out.println("Displaying overdue tasks");
        System.out.println("========================");
        taskService.getOverdueTasks().forEach(System.out::println);
        System.out.println();

        System.out.println("Displaying 'HOUSE' tasks");
        System.out.println("========================");
        taskService.getTasksByCategory("HOUSE").forEach(System.out::println);
        System.out.println();

        System.out.println("Displaying 'HOUSE' subtasks");
        System.out.println("========================");
        taskService.getSubTasksByCategory("HOUSE").forEach(System.out::println);
        ;
        System.out.println();

        System.out.println("Updating task related to the taxes");
        System.out.println("==================================");
        savedTaxes.setName("Do the taxes");
        taskService.updateTask(savedTaxes);
        taskService.getAllTasks().forEach(System.out::println);
        System.out.println();

        System.out.println("Updating subtask related to the taxes");
        System.out.println("=====================================");
        taskService.addSubTask(savedTaxes, SubTask.builder().name("Check results").description("Check results in case we have to pay extra").build());
        taskService.getAllTasks().forEach(System.out::println);
        System.out.println();

        System.out.println("Full text search of 'tools' keyword on the description field");
        System.out.println("============================================================");
        taskService.findByDescription("roses").forEach(System.out::println);
        System.out.println();

        System.out.println("Full text search of 'Cleaning' keyword on the name field of the SubTask");
        System.out.println("=======================================================================");
        taskService.findBySubTaskName("Cleaning").forEach(System.out::println);
        System.out.println();

        System.out.println("Ending use cases / demo");
    }
}

