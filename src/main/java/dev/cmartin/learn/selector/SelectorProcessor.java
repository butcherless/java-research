package dev.cmartin.learn.selector;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SelectorProcessor {
    enum Color {
        RED,
        GREEN,
        BLUE
    }

    public record Shape(
            Color color,
            String name
    ) {
    }

    public record Results(
            List<Shape> redList,
            List<Shape> greenList,
            List<Shape> blueList
    ) {
    }

    /**
     * Groups the collection of shapes by color attribute
     *
     * @param shapes collection of shapes to classify or group
     * @return Grouped shapes by color
     */
    public Results process(final List<Shape> shapes) {
        final Map<Color, List<Shape>> colorListMap = shapes
                .stream()
                .collect(Collectors.groupingBy(Shape::color));

        return new Results(
                colorListMap.getOrDefault(Color.RED, List.of()),
                colorListMap.getOrDefault(Color.GREEN, List.of()),
                colorListMap.getOrDefault(Color.BLUE, List.of())
        );
    }

    public Results processPar(final List<Shape> shapes) {
        var colorGroups = Flux.fromIterable(shapes)
                .collect(Collectors.groupingBy(Shape::color))
                .block(); // Convert to synchronous map

        if (Objects.isNull(colorGroups)) {
            return new Results(List.of(), List.of(), List.of());
        }

        return new Results(
                colorGroups.getOrDefault(Color.RED, List.of()),
                colorGroups.getOrDefault(Color.GREEN, List.of()),
                colorGroups.getOrDefault(Color.BLUE, List.of())
        );
    }
}
