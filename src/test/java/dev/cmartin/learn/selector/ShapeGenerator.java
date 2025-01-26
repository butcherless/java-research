package dev.cmartin.learn.selector;

import dev.cmartin.learn.selector.SelectorProcessor.Color;
import dev.cmartin.learn.selector.SelectorProcessor.Shape;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShapeGenerator {
    private static final Random RANDOM = new Random();
    private static final List<String> SHAPE_NAMES = List.of(
            "Circle", "Square", "Triangle", "Pentagon",
            "Hexagon", "Star", "Diamond", "Oval", "Fistro"
    );

    public static List<Shape> generate(final Integer count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new Shape(
                        randomColor(),
                        SHAPE_NAMES.get(RANDOM.nextInt(SHAPE_NAMES.size()))
                ))
                .collect(Collectors.toList());
    }

    private static Color randomColor() {
        Color[] colors = Color.values();
        return colors[RANDOM.nextInt(colors.length)];
    }
}