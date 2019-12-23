package concurrentmap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ConcurrentMapMemoryConsistency {

    @Test
    @DisplayName("Параллельное суммирование с ошибкой")
    void sumParallel_error() throws InterruptedException {
        Map<String, Integer> map = new HashMap<>();
        List<Integer> sumList = parallelSum100(map, 100);
        long wrongResultCount = sumList.stream().filter(num -> num != 100).count();

        assertNotEquals(1, sumList.stream().distinct().count());
        assertTrue(wrongResultCount > 0);
    }

    @Test
    @DisplayName("Правильное параллельное суммирование")
    void sumParallel_correct() throws InterruptedException {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> sumList = parallelSum100(map, 1000);
        long wrongResultCount = sumList.stream().filter(num -> num != 100).count();
        assertEquals(1, sumList.stream().distinct().count());
        assertEquals(0, wrongResultCount);
    }

    @Test
    @DisplayName("Null не допустим для ключа и значения")
    void nullKeyAndRemapping() {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("test", new Object());
        map.compute("test", (s, o) -> null);
        assertThrows(NullPointerException.class, (() -> map.put(null, new Object())));
        assertNull(map.get("test"));
    }

    private List<Integer> parallelSum100(Map<String, Integer> map, int executionTime) throws InterruptedException {
        List<Integer> sumList = new ArrayList<>();

        for (int i = 0; i < executionTime; i++) {
            map.put("test", 0);
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            for (int j = 0; j < 10; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < 10; k++) {
                        map.computeIfPresent("test", (key, value) -> value + 1);
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            sumList.add(map.get("test"));
        }
        return sumList;
    }
}
