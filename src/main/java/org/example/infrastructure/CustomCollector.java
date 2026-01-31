package org.example.infrastructure;

import java.util.stream.Collector;

public class CustomCollector {

    public static <T> Collector<T, ?, CustomList<T>> toCustomList() {
        return Collector.of(
                CustomList::new,
                CustomList::add,
                (left, right) -> {
                    for (T item : right) {
                        left.add(item);
                    }
                    return left;
                },
                Collector.Characteristics.IDENTITY_FINISH
        );
    }
}
