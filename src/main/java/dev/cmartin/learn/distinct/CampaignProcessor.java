package dev.cmartin.learn.distinct;

import reactor.core.publisher.Flux;

import java.util.List;

public class CampaignProcessor {
    public record Campaign(
        String id,
        String name
    ){}

    public Flux<Campaign> process(final List<Campaign> campaigns) {
        return Flux.fromIterable(campaigns)
                .distinct(Campaign::id)
                .log();
    }
}
