package dev.cmartin.learn.aggregate;

import dev.cmartin.learn.aggregate.BusinessService.MailInfo;
import dev.cmartin.learn.aggregate.BusinessService.Order;
import dev.cmartin.learn.aggregate.FindAggregateInfo.SupplierInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FindAggregateInfoTest {
    private BusinessService businessService;
    private FindAggregateInfo findAggregateInfo;

    @BeforeEach
    void setUp() {
        businessService = Mockito.mock(BusinessService.class);
        findAggregateInfo = new FindAggregateInfo(businessService);
    }


    @Test
    void findSupplierMails_withValidOrderIds_returnsSupplierInfos() {
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();
        UUID supplierId1 = UUID.randomUUID();
        UUID supplierId2 = UUID.randomUUID();

        Order order1 = new Order(orderId1, supplierId1, "orderNumber1");
        Order order2 = new Order(orderId2, supplierId2, "orderNumber2");

        MailInfo mailInfo1 = new MailInfo("from1", "to1", "subject1", "body1");
        MailInfo mailInfo2 = new MailInfo("from2", "to2", "subject2", "body2");
        MailInfo mailInfo3 = new MailInfo("from3", "to3", "subject3", "body3"); // Additional MailInfo for orderId1

        when(businessService.findOrder(orderId1)).thenReturn(Mono.just(order1));
        when(businessService.findOrder(orderId2)).thenReturn(Mono.just(order2));
        when(businessService.findMailsBySupplier(supplierId1)).thenReturn(Flux.just(mailInfo1, mailInfo3));
        when(businessService.findMailsBySupplier(supplierId2)).thenReturn(Flux.just(mailInfo2));

        List<SupplierInfo> result = findAggregateInfo.findSupplierMails(List.of(orderId1, orderId2));

        assertThat(result).hasSize(2);
        assertThat(result).extracting("supplierId").containsExactlyInAnyOrder(supplierId1, supplierId2);
        assertThat(result).filteredOn(supplierInfo -> supplierInfo.supplierId().equals(supplierId1))
                .flatExtracting(SupplierInfo::mailInfos).containsExactlyInAnyOrder(mailInfo1,mailInfo3);
        assertThat(result).filteredOn(supplierInfo -> supplierInfo.supplierId().equals(supplierId2))
                .flatExtracting(SupplierInfo::mailInfos).containsExactly(mailInfo2);    }


    @Test
    void findSupplierMails_withNoMailsForSupplier_returnsEmptyMailInfos() {
        UUID orderId = UUID.randomUUID();
        UUID supplierId = UUID.randomUUID();

        Order order = new Order(orderId, supplierId, "orderNumber");

        when(businessService.findOrder(orderId)).thenReturn(Mono.just(order));
        when(businessService.findMailsBySupplier(supplierId)).thenReturn(Flux.empty());

        List<FindAggregateInfo.SupplierInfo> result = findAggregateInfo.findSupplierMails(List.of(orderId));

        assertThat(result).hasSize(1);
        assertThat(result).extracting("supplierId").containsExactly(supplierId);
        assertThat(result.getFirst().mailInfos()).isEmpty();
    }

    @Test
    void findSupplierMails_withEmptyOrderIds_returnsEmptyList() {
        List<SupplierInfo> result = findAggregateInfo.findSupplierMails(List.of());

        assertThat(result).isEmpty();
    }
}

