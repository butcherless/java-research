# Java Researching project

## Description

    The project aims to investigate the Java language and its ecosystem through proof of concepts.

Check _Java_ and _Maven_ versions:

```bash
./mvnw -v
```

Dependency updates

```bash
./mvnw versions:display-dependency-updates
```

Plugin updates

```bash
./mvnw versions:display-plugin-updates
```

Build the project

```bash
./mvnw -V clean install
```

Download library source code

```bash
./mvnw -V dependency:sources
```