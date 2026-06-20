# Java Research

## Status

![GitHub CI](https://github.com/butcherless/java-research/workflows/CI/badge.svg)

Java Research is a sandbox for investigating the Java language, reactive programming, and the wider Java ecosystem
through focused proofs of concept.

Current experiments cover Reactor-based parallel processing and aggregation, functional and infrastructure error
handling, distinct reactive streams, collection grouping, Java records and generics, and Manifold language extensions.

## Tech stack

- Spring Boot 4.1.0
- Java 25
- Maven 3.9.12 through the Maven Wrapper
- Spring WebFlux and Project Reactor
- Manifold 2026.1.6
- Lombok
- JUnit 5, AssertJ, and Mockito
- JaCoCo 0.8.15 for code coverage
- Apache Maven Build Cache Extension 1.2.3

## Prerequisites

- Java 25
- Git

Maven does not need to be installed separately. Check the Java and Maven versions selected by the wrapper:

```bash
./mvnw --version
```

## Build and test

Run the complete build:

```bash
./mvnw clean verify
```

This compiles the application, runs 20 tests, creates the executable JAR, and generates the JaCoCo report under
`target/site/jacoco/`.

## Run the application

Build and run the executable JAR:

```bash
./mvnw clean package
java -jar target/java-research-0.0.1-SNAPSHOT.jar
```

For development, run it directly through Spring Boot:

```bash
./mvnw spring-boot:run
```

## Research areas

- `aggregate`: concurrent Reactor lookups and result aggregation.
- `distinct`: removal of duplicate domain objects from a reactive stream.
- `generics`: mail-processing use cases with functional and infrastructure error handling.
- `parallel`: bounded-elastic parallel processing of blocking work.
- `selector`: grouping domain objects with Java streams and Reactor.
- `manifold`: experiments with Manifold tuples, extension methods, and named arguments.

The detailed parallel mail-processing requirements are documented in
[`docs/parallel-processing.md`](docs/parallel-processing.md).

## Useful commands

### List dependencies

```bash
./mvnw dependency:list
```

### Check dependency and plugin updates

```bash
./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
```

### Download dependency sources

```bash
./mvnw dependency:sources
```

### Force a full build while retaining cache writes

Bypass existing cache entries, run all tests, and save the new result:

```bash
./mvnw clean verify -Dmaven.build.cache.skipCache=true
```

### Force a full build with caching disabled

Disable both cache reads and writes:

```bash
./mvnw clean verify -Dmaven.build.cache.enabled=false
```

### Maven build cache

A normal `clean verify` restores matching build results from the local cache when possible:

```bash
./mvnw clean verify
```

Local cache entries are stored under `~/.m2/build-cache`. The project retains up to three cached builds per artifact.
CI persists both `~/.m2/repository` and `~/.m2/build-cache`.

## Continuous integration

GitHub Actions runs the project on Java 25. The CI job:

1. Restores Maven dependencies and build-cache entries.
2. Runs `./mvnw -B -V clean verify`.
3. Uploads the generated JaCoCo coverage report to Codecov.
