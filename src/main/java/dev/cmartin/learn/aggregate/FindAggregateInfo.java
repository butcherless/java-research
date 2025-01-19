package dev.cmartin.learn.aggregate;

import dev.cmartin.learn.aggregate.BusinessService.MailInfo;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

public class FindAggregateInfo {

    private final BusinessService businessService;

    public FindAggregateInfo(BusinessService businessService) {
        this.businessService = businessService;
    }

    public record SupplierInfo(UUID supplierId, List<MailInfo> mailInfos) {
    }

    public List<SupplierInfo> findSupplierMails(final List<UUID> orderIds) {
        return Flux.fromIterable(orderIds)
                // Parallel processing of orders
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(this.businessService::findOrder)
                .flatMap(order -> this.businessService.findMailsBySupplier(order.supplierId())
                        .collectList()
                        .map(mails -> new SupplierInfo(order.supplierId(), mails)))
                .sequential()
                .collectList()
                .block();
    }
}
