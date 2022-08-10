package org.oka.logmanager.repository;

import org.oka.logmanager.model.LogEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LogEntryRepository extends CrudRepository<LogEntry, UUID> {
}
