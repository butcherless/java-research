package dev.cmartin.learn.selector;

import dev.cmartin.learn.selector.SelectorProcessor.Results;
import dev.cmartin.learn.selector.SelectorProcessor.Shape;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SelectorProcessorTest {
    private final SelectorProcessor processor = new SelectorProcessor();

    @Test
    void shouldProcessShapeList() {
        final var shapes = ShapeGenerator.generate(100);
        final var results = processor.process(shapes);

        assertThat(results.redList())
                .allMatch(shape -> shape.color() == SelectorProcessor.Color.RED);
        assertThat(results.greenList())
                .allMatch(shape -> shape.color() == SelectorProcessor.Color.GREEN);
        assertThat(results.blueList())
                .allMatch(shape -> shape.color() == SelectorProcessor.Color.BLUE);

        assertThat(results.redList().size() +
                results.greenList().size() +
                results.blueList().size())
                .isEqualTo(shapes.size());
    }

    @Test
    void shouldProcessSpecificShapes() {
        final var shapes = List.of(
                new Shape(SelectorProcessor.Color.RED, "Circle"),
                new Shape(SelectorProcessor.Color.GREEN, "Square"),
                new Shape(SelectorProcessor.Color.BLUE, "Triangle"),
                new Shape(SelectorProcessor.Color.RED, "Pentagon")
        );

        Results results = processor.process(shapes);

        assertThat(results.redList()).hasSize(2);
        assertThat(results.greenList()).hasSize(1);
        assertThat(results.blueList()).hasSize(1);
    }

    @Test
    void shouldProcessEmptyList() {
        final var results = processor.process(List.of());

        assertThat(results.redList()).isEmpty();
        assertThat(results.greenList()).isEmpty();
        assertThat(results.blueList()).isEmpty();
    }

    @Test
    void shouldProcessParEmptyList() {
        final var results = processor.processPar(List.of());

        assertThat(results.redList()).isEmpty();
        assertThat(results.greenList()).isEmpty();
        assertThat(results.blueList()).isEmpty();
    }

    @Test
    void shouldProcessParShapeList() {
        final var shapes = ShapeGenerator.generate(100);
        final var results = processor.processPar(shapes);

        assertThat(results.redList())
                .allMatch(shape -> shape.color() == SelectorProcessor.Color.RED);
        assertThat(results.greenList())
                .allMatch(shape -> shape.color() == SelectorProcessor.Color.GREEN);
        assertThat(results.blueList())
                .allMatch(shape -> shape.color() == SelectorProcessor.Color.BLUE);

        assertThat(results.redList().size() +
                results.greenList().size() +
                results.blueList().size())
                .isEqualTo(shapes.size());
    }

    @Test
    void shouldProcessParSpecificShapes() {
        final var shapes = List.of(
                new Shape(SelectorProcessor.Color.RED, "Circle"),
                new Shape(SelectorProcessor.Color.GREEN, "Square"),
                new Shape(SelectorProcessor.Color.BLUE, "Triangle"),
                new Shape(SelectorProcessor.Color.RED, "Pentagon")
        );

        Results results = processor.processPar(shapes);

        assertThat(results.redList()).hasSize(2);
        assertThat(results.greenList()).hasSize(1);
        assertThat(results.blueList()).hasSize(1);
    }

}
