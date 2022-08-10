#!/usr/bin/env bash

until printf "" 2>>/dev/null >>/dev/tcp/cassandra/9042; do
    sleep 5;
    echo "Waiting for cassandra ...";
done

echo "Creating keyspace and table..."
cqlsh cassandra -u cassandra -pcassandra -e "CREATE KEYSPACE IF NOT EXISTS logs_space WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'};"
cqlsh cassandra -u cassandra -pcassandra -e "CREATE TABLE logs_space.logentry(id uuid, data text, primary key(id));"
echo "Cassandra configuration done!"