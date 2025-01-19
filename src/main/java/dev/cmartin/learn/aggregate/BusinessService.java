package dev.cmartin.learn.aggregate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BusinessService {

    record Order(
            UUID id,
            UUID supplierId,
            String orderNumber
    ) {
    }

    record MailInfo(String from, String to, String subject, String body) {
    }

    Flux<MailInfo> findMailsBySupplier(final UUID supplierId);

    Mono<Order> findOrder(final UUID orderId);

}
