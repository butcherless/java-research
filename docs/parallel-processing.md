# Parallel processing

## Description

- Use Flux type to process data in parallel
- Handle logical errors
- Handle infrastructure errors

## Requirements

- Use functional style programming as much as possible
- Use java core library functions as much as possible
- Use immutable variables and collections
- Use Java 21
- Use Reactor project for parallel processing
- Use Java records
- Use JUnit 5
- Use AssertJ
- Don't use `null` values
- Don't use `null` for comparison
- Don't use try catch for handle exceptions

## Business requirements

- Implements the function signature execute define below. The function belongs to a use case.
- There is an infrastructure function, `sendMail`, that requests the send of an email for a list of order identifiers.
- The sendMail function returns if succeeded a response with an empty list of errors. Main case.
- The sendMail function returns if failed a response with a list of errors. This is a functional error that must be handled by the process. Alternative case.
- The sendMail function can throw a domain exception, `ServiceUnavailableException`. It represents an infrastructure error that must be handled by the process. Error case.
- The sendMail function is not reactive.
- The process must be in parallel to send the emails.
- The process must handle the functional and infrastructure errors.
- The process must return a `Results` object with two collections. One for successful sends and another for the failed sends either for functional or infrastructure errors.
- The process must not stop if a ServiceUnavailableException or another Domain exception is thrown.

## Code definitions

```java
public Results execute(final List<SendMailRequest> requests);

public record Results(
        List<SendMailResponse> succeeded,
        List<SendMailResponse> failed
) {}

SendMailResponse sendMail(SendMailRequest request);

record SendMailRequest(
        String from,
        String to,
        String subject,
        String body,
        List<UUID> orderIds
) {}

record SendMailResponse(
        UUID id,
        SendMailStatus status,
        List<String> errors
) {}

enum SendMailStatus {
  SUCCEEDED, FAILED
}
```




