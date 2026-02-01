package org.example.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import org.junit.jupiter.api.Test;

public class CustomCollectorTest {
    
    @Test
    public void testPositiveCustomCollectorToCustomList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        CustomList<Integer> customList = list.stream().collect(CustomCollector.toCustomList());
        assertEquals(1, customList.get(0));
        assertEquals(2, customList.get(1));
        assertEquals(3, customList.get(2));
    }

    @Test
    public void testCustomCollectorParallelLargeInput() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            list.add(i);
        }

        CustomList<Integer> customList = list.parallelStream()
                .collect(CustomCollector.toCustomList());

        assertEquals(list.size(), customList.size());
    }

    @Test
    public void testCombinerManually() {
        Collector<Integer, CustomList<Integer>, CustomList<Integer>> collector = CustomCollector.toCustomList();

        CustomList<Integer> left = new CustomList<>();
        left.add(1);

        CustomList<Integer> right = new CustomList<>();
        right.add(2);

        CustomList<Integer> result = collector.combiner().apply(left, right);

        assertEquals(2, result.size());
    }

    @Test
    void testCombinerWithBothEmpty() {
        Collector<Integer, CustomList<Integer>, CustomList<Integer>> collector = CustomCollector.toCustomList();

        CustomList<Integer> left = new CustomList<>();
        CustomList<Integer> right = new CustomList<>();

        CustomList<Integer> result = collector.combiner().apply(left, right);

        assertEquals(0, result.size());
    }

    @Test
    void testCombinerWithEmptyRightExplicit() {
        Collector<Integer, CustomList<Integer>, CustomList<Integer>> collector = CustomCollector.toCustomList();

        CustomList<Integer> left = new CustomList<>();
        left.add(42);

        CustomList<Integer> right = new CustomList<>();

        CustomList<Integer> result = collector.combiner().apply(left, right);

        assertEquals(1, result.size());
        assertEquals(42, result.get(0));
    }
}
