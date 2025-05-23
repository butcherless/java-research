package dev.cmartin.learn.parallel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


class ParallelProcessorTest {

    ParallelProcessor processor = new ParallelProcessor();

    @Test
    void shouldHandleBooleanSupplier() {

        var ids = generateUUIDs(32);
        var results = processor.processPar(ids);
        assertThat(results.size()).isEqualTo(ids.size());
    }


    public static List<UUID> generateUUIDs(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }

        return IntStream.range(0, count)
                .mapToObj(i -> UUID.randomUUID())
                .collect(Collectors.toList());
    }

}
