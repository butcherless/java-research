package dev.cmartin.learn.generics;

import dev.cmartin.learn.generics.DomainModel.SendMailRequest;
import dev.cmartin.learn.generics.DomainModel.SendMailResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static dev.cmartin.learn.generics.ErrorHandler.execute;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class ErrorHandlerTest {
    private static final UUID PROCESS_ID = UUID.randomUUID();
    private static final UUID SEND_MAIL_RESPONSE_ID = UUID.randomUUID();

    private final BusinessService service = Mockito.mock(BusinessService.class);

    @Test
    void shouldHandleBooleanSupplier() {
        // given
        final var expected = true;
        when(this.service.hasPendingItems(PROCESS_ID))
                .thenReturn(expected);
        // when
        final Boolean result = ErrorHandler.execute(() -> this.service.hasPendingItems(PROCESS_ID));
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldHandleIntegerSupplier() {
        // given
        final var expected = 7;
        when(this.service.getIndex(PROCESS_ID))
                .thenReturn(7);
        // when
        final Integer result = execute(() -> this.service.getIndex(PROCESS_ID));
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldHandleUUIDSupplier() {
        // given
        final var expected = UUID.randomUUID();
        when(this.service.createReference(PROCESS_ID))
                .thenReturn(expected);
        // when
        final UUID result = execute(() -> this.service.createReference(PROCESS_ID));
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldThrowsExceptionUUIDSupplier() {
        // given
        when(this.service.createReference(PROCESS_ID))
                .thenThrow(new RuntimeException("Processing Error"));

        // when & then
        assertThatThrownBy(() -> execute(() -> this.service.createReference(PROCESS_ID)))
                .isInstanceOf(ServiceUnavailableException.class);
    }

    @Test
    void shouldHandleSendMailResponseSupplier() {
        // given
        final var orderIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        final var request = new SendMailRequest(
                "from@mail.com", "to@mail.com", "subject", "body", orderIds);
        final var expected = SendMailResponse.succeeded(SEND_MAIL_RESPONSE_ID);

        when(this.service.sendMail(request))
                .thenReturn(expected);
        // when
        final SendMailResponse result = execute(() -> this.service.sendMail(request));

        // then
        assertThat(result).isEqualTo(expected);
    }
}
