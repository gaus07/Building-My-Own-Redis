# Building Your Own Redis in Java

> **Status:** Work in progress — this repository is a learning project for implementing a small Redis-compatible server in Java.

This project explores how an in-memory key-value store works internally and over the network. The goal is to build a Redis-like server from scratch, starting with a lightweight TCP server and the Redis Serialization Protocol (RESP), and gradually add commands and persistence.

Redis-compatible does not mean this project will be a drop-in replacement for Redis. It is intended for education and experimentation.

## Features

The implementation may include:

- TCP server for Redis client connections
- RESP parsing and response encoding
- Basic commands such as `PING`, `ECHO`, `SET`, `GET`, and `DEL`
- Optional support for key expiration
- Concurrent client handling
- Persistence and an append-only file
- Pub/Sub and additional Redis commands

The feature checklist should be updated as commands are implemented.

## Prerequisites

- Java 17 or newer
- A Java build tool such as Maven or Gradle
- Git
- Redis CLI or `netcat` for manual testing

## Getting Started

```bash
git clone https://github.com/gaus07/Building-Your-Own-Redis.git
cd Building-Your-Own-Redis
```

Once Java source and build configuration are added, use your selected build tool to compile and run the server. Example placeholders:

```bash
# Maven
mvn package
java -jar target/redis-from-scratch.jar

# Gradle
./gradlew build
java -jar build/libs/redis-from-scratch.jar
```

By default, the server should listen on `localhost:6379` unless configured otherwise.

## Try It

Use Redis CLI while the server is running:

```bash
redis-cli -h 127.0.0.1 -p 6379
127.0.0.1:6379> PING
PONG
127.0.0.1:6379> SET name redis
OK
127.0.0.1:6379> GET name
"redis"
```

You can also send a raw RESP command:

```bash
printf '*1\r\n$4\r\nPING\r\n' | nc 127.0.0.1 6379
```

## Project Structure

```text
.
├── src/
│   ├── main/java/       # Server, protocol, commands, and storage implementation
│   └── test/java/       # Unit and integration tests
├── .gitignore
└── README.md
```

The structure above is the recommended layout and may be added as implementation work proceeds.

## Architecture Overview

A typical request flow is:

1. Accept a client connection through a TCP socket.
2. Read bytes and decode a RESP array.
3. Look up the command and validate its arguments.
4. Execute the command against the in-memory store.
5. Encode the result as a RESP response and send it to the client.

## Contributing

Contributions are welcome. Before submitting a change:

1. Fork the repository and create a focused feature branch.
2. Keep changes small and add or update tests.
3. Document new commands and configuration options.
4. Run the relevant build and tests before opening a pull request.

Please keep implementation-specific details and public behavior clearly separated.
