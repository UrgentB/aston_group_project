import org.example.domain.Bus;
import org.example.multithreading.MultithreadedCounter;
import java.util.Arrays;
import java.util.List;

public class DemoMultithreading {
    public static void main(String[] args) throws Exception {
        System.out.println("=== ДЕМО МНОГОПОТОЧНОГО СЧЕТЧИКА ===");
        
        // Простой тест с Bus
        List<Bus> buses = Arrays.asList(
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(102, "Volvo", 20000.0),
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(101, "Mercedes", 15000.5)
        );
        
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, 2);
        int result = counter.countOccurrences(target);
        
        System.out.println("Тестовая коллекция: " + buses.size() + " автобусов");
        System.out.println("Ищем: " + target);
        System.out.println("Найдено: " + result + " (ожидается: 3)");
        
        if (result == 3) {
            System.out.println("✅ ЗАДАНИЕ №4 ВЫПОЛНЕНО!");
        }
    }
}
