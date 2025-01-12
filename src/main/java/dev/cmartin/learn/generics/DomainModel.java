package dev.cmartin.learn.generics;

import java.util.List;
import java.util.UUID;

public interface DomainModel {
    enum SendMailStatus {
        SUCCEEDED, FAILED
    }

    record Order(
            UUID id,
            String name,
            Negotiation negotiation
    ) {
    }

    record Negotiation(
            UUID id,
            String name
    ) {
    }

    record SendMailRequest(
            String from,
            String to,
            String subject,
            String body,
            List<UUID> orderIds
    ) {
    }

    record SendMailResponse(
            UUID id,
            SendMailStatus status,
            List<String> errors
    ) {
        static SendMailResponse succeeded(final UUID id) {
            return new SendMailResponse(id, SendMailStatus.SUCCEEDED, List.of());
        }

        static SendMailResponse failed(
                final UUID id,
                final List<String> errors
        ) {
            return new SendMailResponse(id, SendMailStatus.FAILED, errors);
        }
    }

    record Results(
            List<SendMailResponse> succeeded,
            List<SendMailResponse> failed
    ) {
        static Results from(
                final List<SendMailResponse> succeeded,
                final List<SendMailResponse> failed) {

            return new Results(succeeded, failed);
        }
    }
}
