package dev.cmartin.learn.parallel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ParallelProcessor {

    private static final Integer MAX_DELAY = 2;

    public record ProcessResult(
            UUID id,
            Integer numberCount,
            Integer letterCount) {
    }

    public List<ProcessResult> processPar(final List<UUID> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(this::processId)
                .log()
                .sequential()
                .collectList()
                .block();

    }

    private Mono<ProcessResult> processId(final UUID id) {
        return Mono.just(id)
                .log()
                .delayElement(Duration.ofSeconds(this.genDelay(MAX_DELAY)))
                .map(UUID::toString)
                .flatMap(idString -> {
                    var numberCount = this.countDigits(idString);
                    final int letterCount = this.countLetters(idString);
                    return Mono.just(new ProcessResult(id, numberCount, letterCount));
                });
    }

    private Integer countDigits(final String s) {
        long count = s.chars()
                .filter(Character::isDigit)
                .count();
        return (int) count;
    }

    private Integer countLetters(final String s) {
        long count = s.chars()
                .filter(Character::isLetter)
                .count();
        return (int) count;
    }

    // Generates a number between 1 and 5
    private Integer genDelay(final Integer maxDelay) {
        Random random = new Random();
        return random.nextInt(maxDelay) + 1;
    }
}
