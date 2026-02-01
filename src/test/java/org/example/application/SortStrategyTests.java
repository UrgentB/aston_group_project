package org.example.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.example.application.comparators.NumberBusComparator;
import org.example.application.sorting.BubbleSortStrategy;
import org.example.application.sorting.ConditionalSortStrategy;
import org.example.application.sorting.QuickSortStrategy;
import org.example.application.sorting.SelectionSortStrategy;
import org.example.application.sorting.SortAlgorithm;
import org.example.application.sorting.SortCondition;
import org.example.application.sorting.SortStrategy;
import org.example.application.sorting.SortType;
import org.example.domain.Bus;
import org.example.infrastructure.CustomList;
import org.junit.jupiter.api.Test;

public class SortStrategyTests {
    
    @Test
    public void testPositiveBubbleSortStrategySort() {
        Comparator<Bus> comparator = new NumberBusComparator();
        SortStrategy<Bus> sortStrategy = new BubbleSortStrategy<Bus>(comparator);

        CustomList<Bus> list1 = new CustomList<Bus>();
        for(int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int)(Math.random() * 100));
            list1.add(bus);
        }
        sortStrategy.sort(list1);
        boolean isSorted = true;
        for(int i = 1; i < list1.size(); i++) {
            isSorted = comparator.compare(list1.get(i - 1), list1.get(i)) <= 0 && isSorted;
        }
        assertTrue(isSorted);
    }

    @Test
    public void testPositiveSelectionSortStrategySort() {
        Comparator<Bus> comparator = new NumberBusComparator();
        SortStrategy<Bus> sortStrategy = new SelectionSortStrategy<Bus>(comparator);

        CustomList<Bus> list1 = new CustomList<Bus>();
        for (int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int) (Math.random() * 100));
            list1.add(bus);
        }
        sortStrategy.sort(list1);
        boolean isSorted = true;
        for (int i = 1; i < list1.size(); i++) {
            isSorted = comparator.compare(list1.get(i - 1), list1.get(i)) <= 0 && isSorted;
        }
        assertTrue(isSorted);
    }

    @Test
    public void testPositiveQuickSortStrategySort() {
        Comparator<Bus> comparator = new NumberBusComparator();
        SortStrategy<Bus> sortStrategy = new QuickSortStrategy<Bus>(comparator);

        CustomList<Bus> list1 = new CustomList<Bus>();
        for (int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int) (Math.random() * 100));
            list1.add(bus);
        }
        sortStrategy.sort(list1);
        boolean isSorted = true;
        for (int i = 1; i < list1.size(); i++) {
            isSorted = comparator.compare(list1.get(i - 1), list1.get(i)) <= 0 && isSorted;
        }
        assertTrue(isSorted);
    }

    @Test
    public void testPositiveConditionalSortStrategySortAll() {
        Comparator<Bus> comparator = SortType.SORT_NUMBER.getComparator();
        SortStrategy<Bus> sortStrategy = new ConditionalSortStrategy<Bus>(
            SortAlgorithm.SORT_BUBBLE.create(comparator),
            SortCondition.SORT_ALL.getPredicate()
        );

        CustomList<Bus> list1 = new CustomList<Bus>();
        for (int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int) (Math.random() * 100));
            list1.add(bus);
        }
        sortStrategy.sort(list1);
        boolean isSorted = true;
        for (int i = 1; i < list1.size(); i++) {
            isSorted = comparator.compare(list1.get(i - 1), list1.get(i)) <= 0 && isSorted;
        }
        assertTrue(isSorted);
    }

    @Test
    public void testPositiveConditionalSortStrategySortOdd() {
        Comparator<Bus> comparator = new NumberBusComparator();
        SortStrategy<Bus> sortStrategy = new ConditionalSortStrategy<Bus>(
            new QuickSortStrategy<Bus>(comparator),
            SortCondition.SORT_ODD_ONLY.getPredicate()
        );

        CustomList<Bus> list1 = new CustomList<Bus>();
        for (int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int) (Math.random() * 100));
            list1.add(bus);
        }
        sortStrategy.sort(list1);
        boolean isSorted = true;
        for (int i = 3; i < list1.size(); i+=2) {
            isSorted = comparator.compare(list1.get(i - 2), list1.get(i)) <= 0 && isSorted;
        }
        assertTrue(isSorted);
    }

    @Test
    public void testPositiveConditionalSortStrategySortEven() {
        Comparator<Bus> comparator = new NumberBusComparator();
        SortStrategy<Bus> sortStrategy = new ConditionalSortStrategy<Bus>(
                new QuickSortStrategy<Bus>(comparator),
                SortCondition.SORT_EVEN_ONLY.getPredicate());

        CustomList<Bus> list1 = new CustomList<Bus>();
        for (int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int) (Math.random() * 100));
            list1.add(bus);
        }
        sortStrategy.sort(list1);
        boolean isSorted = true;
        for (int i = 2; i < list1.size(); i += 2) {
            isSorted = comparator.compare(list1.get(i - 2), list1.get(i)) <= 0 && isSorted;
        }
        assertTrue(isSorted);
    }
}
