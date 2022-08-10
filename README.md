# Java Mentoring Program: No SQL

This maven-based project contains the exercises defined in the 'No SQL' module of Java Mentoring program.

There are 2 modules in this maven project with the exercises related to MongoDB and Cassandra

### MongoDB

The module is named: 'simpleTaskManager'

It is a basic application to manage Tasks and SubTasks. The technological stack is MongoDB + Spring Boot + Spring Data
JPA (MongoDB).

For dev purposes the MongoDB can be started via docker-compose. Move to the folder /docker

```
$ docker-compose up -d
```

It starts a new MongoDB instance, ready to be used by the AppTaskManager.java

The application follows the classical approach model / service / repository.

Notice that repository and service packages are fully tested at Unit level and Integration level. At Integration level
an embedded mongoDB database is started using the `@AutoConfigureDataMongo`

The main application entry point is `AppTaskManager`. It contains a set of demo / use cases where multiple tasks are
created, managed, removed. All using mongoDB started via docker-compose.

### Cassandra

The module is named: 'logManager'.

It is a basic application to insert and edit massive log entries. The technological stack is Cassandra + Spring Boot +
Spring Data JPA (Cassandra).

For dev purposes the Cassandra can be started via docker-compose. Move to the folder /docker

```
$ docker-compose up -d
```

It starts a 3 node Cassandra cluster (with the keyspaces and key distribution), ready to be used by the AppLogManager.java

It is very interesting to see there result:

List the nodes:

```
$docker-compose ps
NAME                   COMMAND                  SERVICE             STATUS              PORTS
docker-cassandra-1     "/opt/bitnami/script…"   cassandra           running             0.0.0.0:7000->7000/tcp, 0.0.0.0:9042->9042/tcp
docker-cassandra2-1    "/opt/bitnami/script…"   cassandra2          running             0.0.0.0:7001->7000/tcp, 0.0.0.0:9043->9042/tcp
docker-cassandra3-1    "/opt/bitnami/script…"   cassandra3          running             0.0.0.0:7002->7000/tcp, 0.0.0.0:9044->9042/tcp
docker-sample-data-1   "/sample_data.sh"        sample-data         exited (0)
PS C:\Users\Oscar_Mora_Perez\Documents\git\noSql\logManager\docker>
```

Select one node and connect:

```
$docker exec -it docker-cassandra-1 /bin/bash
$ nodetool status
Datacenter: datacenter1
=======================
Status=Up/Down
|/ State=Normal/Leaving/Joining/Moving
--  Address       Load       Tokens  Owns (effective)  Host ID                               Rack
UN  192.168.32.2  20.97 MiB  256     100.0%            25cbec88-c21e-444b-a0f7-662a685b30c4  rack1
UN  192.168.32.4  20.97 MiB  256     100.0%            14973bef-97e2-45d0-9571-4caae54460d8  rack1
UN  192.168.32.3  20.92 MiB  256     100.0%            490cd043-60fb-433f-a37c-0381d7ef3d2e  rack1

```

Notice the replication factor of 100% because the replication factor = 3

Execute the `AppLogManager`. It will insert 5000 log entries and update them. The throughput stats are displayed:

Write stats (5000 insertions on a 3 nodes cluster)
```
Write total time: 11719 ms.
Write throughput: 0.42665756463862103 operations/ms
```

Update stats (5000 insertions on a 3 nodes cluster)
```
Update time: 25746 ms.
Update throughput: 0.19420492503689893 operations/ms
```

The resulting stats proves that the write operations are faster than read operations:

<em>Cassandra has benefits being +HA (no SPOF) + having tuneable Consistency. Cassandra is very fast writing bulk data in sequence and reading them sequentially. Cassandra is very fast in throughput and from operations perspective too. Cassandra is very easy to maintain as it is very reliable and a robust systems architecture.</em>

[Reference](https://www.zymr.com/cassandra-good-read-operations/#:~:text=You%20can%20vary%20the%20consistency,Cassandra%20does%20write%20operation%20faster)