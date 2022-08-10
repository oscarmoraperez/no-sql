package org.oka.logmanager;

import org.oka.logmanager.model.LogEntry;
import org.oka.logmanager.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@SpringBootApplication
@EnableCassandraRepositories
public class AppLogManager implements CommandLineRunner {
    private static double ITERATIONS = 5000;

    @Autowired
    LogEntryRepository logEntryRepository;

    public static void main(String[] args) {
        SpringApplication.run(AppLogManager.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting set of use cases / demo");
        System.out.println("================================");

        List<UUID> ids = new ArrayList<>();

        long tick = System.currentTimeMillis();
        for (int i = 0; i < ITERATIONS; i++) {
            LogEntry entry = new LogEntry();
            entry.setData(randomAlphabetic(1024));
            logEntryRepository.save(entry);
            ids.add(entry.getId());
        }
        long tack = System.currentTimeMillis();
        System.out.println("Write operations: " + ITERATIONS);
        System.out.println("Write total time: " + (tack - tick) + " ms.");
        System.out.println("Write throughput: " + ITERATIONS / (tack - tick) + " operations/ms");

        long tick2 = System.currentTimeMillis();
        for (UUID id : ids) {
            Optional<LogEntry> entry = logEntryRepository.findById(id);
            entry.orElseThrow().setData(randomAlphabetic(1024));
            logEntryRepository.save(entry.orElseThrow());
        }
        long tack2 = System.currentTimeMillis();
        System.out.println("Update operations: " + ITERATIONS);
        System.out.println("Update time: " + (tack2 - tick2) + " ms.");
        System.out.println("Update throughput: " + ITERATIONS / (tack2 - tick2) + " operations/ms");
        System.exit(0);
    }
}