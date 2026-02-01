package org.example.infrastructure;

import java.util.stream.Collector;

public class CustomCollector {

    public static <T> Collector<T, CustomList<T>, CustomList<T>> toCustomList() {
    return Collector.of(
            CustomCollector::supplier,
            CustomCollector::accumulator,
            CustomCollector::combiner,
            Collector.Characteristics.IDENTITY_FINISH
        );
    }

    private static <T> CustomList<T> supplier() {
        return new CustomList<>();
    }

    private static <T> void accumulator(CustomList<T> list, T item) {
        list.add(item);
    }

    private static <T> CustomList<T> combiner(CustomList<T> left, CustomList<T> right) {
        for (T item : right) {
            left.add(item);
        }
        return left;
    }
}
