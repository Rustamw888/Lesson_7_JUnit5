package guru.qa.lesson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Класс с демонстрационными методами")
public class SimpleTest {

    @Disabled
    @DisplayName("Демонстрационный метод №1")
    @Test
    public void firstTest() {
        Assertions.assertTrue(3 > 2, "3 > 2");
        Assertions.assertFalse(3 < 2, "3 < 2");
        Assertions.assertAll("11111",
                () -> Assertions.assertTrue(3 < 2, "3 > 2"),
                () -> Assertions.assertFalse(3 < 2, "3 < 2")
        );
    }

    @DisplayName("Демонстрационный метод №2")
    @Test
    public void secondTest() {
        Assertions.assertTrue(3 == 2, "3 > 2");
    }
}
