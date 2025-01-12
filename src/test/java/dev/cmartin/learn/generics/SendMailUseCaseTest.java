package dev.cmartin.learn.generics;

import dev.cmartin.learn.generics.DomainModel.SendMailRequest;
import dev.cmartin.learn.generics.DomainModel.SendMailResponse;
import dev.cmartin.learn.generics.DomainModel.SendMailStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class SendMailUseCaseTest {

    private static final UUID SEND_MAIL_RESPONSE_ID = UUID.randomUUID();
    private static final List<UUID> orderIds = List.of(UUID.randomUUID(), UUID.randomUUID());

    private final BusinessService service = Mockito.mock(BusinessService.class);
    private final SendMailUseCase useCase = new SendMailUseCase(service);

    @Test
    void shouldProcessSendMailRequestsSuccessfully() {
        // given
        final var request = new SendMailRequest("from@mail.com", "to@mail.com", "subject", "body", orderIds);
        final var expectedResponse = SendMailResponse.succeeded(SEND_MAIL_RESPONSE_ID);
        when(service.sendMail(request)).thenReturn(expectedResponse);

        // when
        final var results = useCase.execute(List.of(request));

        // then
        assertThat(results.succeeded()).containsExactly(expectedResponse);
        assertThat(results.failed()).isEmpty();
    }

    @Test
    void shouldHandleServiceUnavailableException() {
        // given
        final var request = new SendMailRequest("from@mail.com", "to@mail.com", "subject", "body", orderIds);
        when(service.sendMail(request)).thenThrow(new ServiceUnavailableException("Service is unavailable"));

        // when
        final var results = useCase.execute(List.of(request));

        // then
        assertThat(results.succeeded()).isEmpty(); // No successful responses
        assertThat(results.failed())
                .hasSize(1) // One failed response
                .allSatisfy(response -> {
                    assertThat(response.status()).isEqualTo(SendMailStatus.FAILED);
                    assertThat(response.errors()).containsExactly("Service is unavailable");
                });
    }

    @Test
    void shouldThrowServiceUnavailableExceptionWhenNoResponsesAreGenerated() {
        // given
        final List<SendMailRequest> requests = List.of();

        // when & then
        assertThatThrownBy(() -> useCase.execute(requests))
                .isInstanceOf(ServiceUnavailableException.class)
                .hasMessage("Error processing requests");
    }
}