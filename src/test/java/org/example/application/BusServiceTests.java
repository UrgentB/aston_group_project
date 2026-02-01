package org.example.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.example.application.sorting.ConditionalSortStrategy;
import org.example.application.sorting.SortAlgorithm;
import org.example.application.sorting.SortCondition;
import org.example.application.sorting.SortType;
import org.example.domain.Bus;
import org.example.exception.BusServiceException;
import org.example.infrastructure.CustomList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BusServiceTests {

    @Test
    public void testBusServiceImplSort() {
        
        BusServiceImpl service = new BusServiceImpl(mock(BusExportService.class));

        CustomList<Bus> list1 = new CustomList<Bus>();
        CustomList<Bus> list2 = new CustomList<Bus>();
        for(int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int)(Math.random() * 100));
            list1.add(bus);
            list2.add(bus);
        }
        service.sort(list1, SortType.SORT_NUMBER, SortAlgorithm.SORT_BUBBLE, SortCondition.SORT_ALL);
        new ConditionalSortStrategy<>(
            SortAlgorithm.SORT_BUBBLE.create(
                SortType.SORT_NUMBER.getComparator()
            ),
            SortCondition.SORT_ALL.getPredicate()
        ).sort(list2);
        boolean isSorted = true;
        for(int i = 1; i < list1.size(); i++) {
            isSorted = list2.get(i) == list1.get(i);
        }
        assertTrue(isSorted);
    }

    @Test
    void testPositiveBusServiceImplRead() throws IOException {

        InputStrategy<Bus> mockStrategy = Mockito.mock(FileBusInputStrategyAdapter.class);

        Mockito.when(mockStrategy.loadData()).thenReturn(new CustomList<>());

        InputType inputType = Mockito.spy(InputType.INPUT_FROM_FILE);
        Mockito.doReturn(mockStrategy)
                .when(inputType)
                .getStrategy();

        BusServiceImpl service = new BusServiceImpl(mock(BusExportService.class));

        assertTrue(service.read(inputType) instanceof CustomList<Bus>);
    }

    @Test
    void testNegativeBusServiceImplThrowsIOExceptionOnRead() throws IOException {

        InputStrategy<Bus> mockStrategy = Mockito.mock(FileBusInputStrategyAdapter.class);

        Mockito.when(mockStrategy.loadData()).thenThrow(new IOException("Mocked exception"));

        InputType inputType = Mockito.spy(InputType.INPUT_FROM_FILE);
        Mockito.doReturn(mockStrategy)
                .when(inputType)
                .getStrategy();

        BusServiceImpl service = new BusServiceImpl(mock(BusExportService.class));

        assertThrows(BusServiceException.class, () -> service.read(inputType));
    }

    @Test
    void testPositiveBusServiceImplStreamRead() throws IOException {

        InputStrategy<Bus> mockStrategy = Mockito.mock(FileBusInputStrategyAdapter.class);

        Mockito.when(mockStrategy.loadStreamData()).thenReturn(new CustomList<>());

        InputType inputType = Mockito.spy(InputType.INPUT_FROM_FILE);
        Mockito.doReturn(mockStrategy)
                .when(inputType)
                .getStrategy();

        BusServiceImpl service = new BusServiceImpl(mock(BusExportService.class));

        assertTrue(service.streamRead(inputType) instanceof CustomList<Bus>);
    }

    @Test
    void testNegativeBusServiceImplThrowsIOExceptionOnStreamRead() throws IOException {

        InputStrategy<Bus> mockStrategy = Mockito.mock(FileBusInputStrategyAdapter.class);

        Mockito.when(mockStrategy.loadStreamData()).thenThrow(new IOException("Mocked exception"));

        InputType inputType = Mockito.spy(InputType.INPUT_FROM_FILE);
        Mockito.doReturn(mockStrategy)
                .when(inputType)
                .getStrategy();

        BusServiceImpl service = new BusServiceImpl(mock(BusExportService.class));

        assertThrows(BusServiceException.class, () -> service.streamRead(inputType));
    }

    @Test
    void testPositiveBusServiceImplWrite() throws IOException {
        ExportService<Bus> exportService = mock(BusExportService.class);
        doNothing().when(exportService).save(any(CustomList.class));
        BusServiceImpl service = new BusServiceImpl(exportService);

        assertDoesNotThrow(() -> service.write(new CustomList<Bus>()));
    }
}
