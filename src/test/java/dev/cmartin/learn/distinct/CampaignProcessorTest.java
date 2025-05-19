package dev.cmartin.learn.distinct;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

public class CampaignProcessorTest {

    @Test
    void shouldHandleDistinct() {
        var processor = new CampaignProcessor();
        var campaigns = List.of(
                new CampaignProcessor.Campaign(
                        "f85cad61-023b-416c-ab0f-be47c1011ecc",
                        "S25"),
                new CampaignProcessor.Campaign(
                        "5c2776df-c1a5-473e-aa75-96c57b8ad2b5",
                        "V25"),
                new CampaignProcessor.Campaign(
                        "f85cad61-023b-416c-ab0f-be47c1011ecc",
                        "S25"),
                new CampaignProcessor.Campaign(
                        "5c2776df-c1a5-473e-aa75-96c57b8ad2b5",
                        "V25")
        );

        var result = processor.process(campaigns);

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }
}
