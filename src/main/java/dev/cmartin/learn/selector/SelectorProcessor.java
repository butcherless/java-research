package dev.cmartin.learn.selector;

import java.util.List;

public class SelectorProcessor {
    enum Color {
        RED, GREEN, BLUE
    }

    public record FigureShape(
            Color color,
            String name
    ) {
    }

    public record Results(
            List<FigureShape> redList,
            List<FigureShape> greenList,
            List<FigureShape> blueList
    ) {
    }


}
