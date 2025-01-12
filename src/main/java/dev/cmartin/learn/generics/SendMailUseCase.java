package dev.cmartin.learn.generics;

import dev.cmartin.learn.generics.DomainModel.Results;
import dev.cmartin.learn.generics.DomainModel.SendMailRequest;
import dev.cmartin.learn.generics.DomainModel.SendMailResponse;
import dev.cmartin.learn.generics.DomainModel.SendMailStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SendMailUseCase {
    private final BusinessService businessService;

    public SendMailUseCase(final BusinessService businessService) {
        this.businessService = businessService;
    }

    public Results execute(final List<SendMailRequest> requests) {
        final var responses = Flux.fromIterable(requests)
                .flatMap(this::processRequest)
                .collectList()
                .block();

        return Optional.ofNullable(responses)
                .filter(rs -> !rs.isEmpty())
                .map(rs -> Results.from(
                        getByStatus(rs, SendMailStatus.SUCCEEDED),
                        getByStatus(rs, SendMailStatus.FAILED)
                ))
                .orElseThrow(() -> new ServiceUnavailableException("Error processing requests"));
    }

    private List<SendMailResponse> getByStatus(final List<SendMailResponse> responses, final SendMailStatus status) {
        return responses
                .stream()
                .filter(response -> response.status().equals(status))
                .toList();
    }

    private Mono<SendMailResponse> processRequest(final SendMailRequest request) {
        return Mono.fromCallable(() -> this.businessService.sendMail(request))
                .onErrorResume(ServiceUnavailableException.class,
                        ex -> Mono.just(handleInfrastructureError(request, ex)))
                .map(response -> {
                    if (response.errors().isEmpty()) {
                        return response;
                    } else {
                        return this.handleFunctionalError(response);
                    }
                });
    }

    private SendMailResponse handleFunctionalError(
            final SendMailResponse response
    ) {
        return SendMailResponse.failed(
                UUID.randomUUID(),
                response.errors()
        );
    }

    private SendMailResponse handleInfrastructureError(
            final SendMailRequest request,
            final Throwable ex
    ) {
        return SendMailResponse.failed(
                UUID.randomUUID(),
                List.of(ex.getMessage())
        );
    }


}
