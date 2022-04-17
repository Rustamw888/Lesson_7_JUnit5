package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.domain.MenuItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WebTest {

    @ValueSource(strings = {
            "Selenide",
            "JUnit"
    })
    @DisplayName("Проверка поиска в яндексе")
    @ParameterizedTest(name = "по слову {0}")
    public void yandexSearchSimpleTest(String testData) {
//          Предусловия:
        Selenide.open("https://ya.ru");
//          Шаги:
        $("#text").setValue(testData);
        $("button[type='submit']").click();
//          Ожидаемый результат:
        $$(".serp-item")
                .find(Condition.text(testData))
                .shouldBe(Condition.visible);
    }

    @CsvSource(value = {
            "Selenide      |       is an open source library for test",
            "JUnit| Support JUnit"
    },
    delimiter = '|'
    )
    @DisplayName("Проверка поиска в яндексе")
    @ParameterizedTest(name = "по слову {0}, ожидаем результат: {1}")
    public void yandexSearchComplexTest(String testData, String expectedResult) {
//          Предусловия:
        Selenide.open("https://ya.ru");
//          Шаги:
        $("#text").setValue(testData);
        $("button[type='submit']").click();
//          Ожидаемый результат:
        $$(".serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(Condition.visible);
    }

    static Stream<Arguments> methodSourceExampleTest1() {
        return Stream.of(
                Arguments.of("first string", List.of(43, 31)),
                Arguments.of("second string", List.of(3, 1))
        );
    }

    @MethodSource("methodSourceExampleTest1")
    @ParameterizedTest
    public void methodSourceExampleTest(String first, List<Integer> second) {
        System.out.println(first + " and list: " + second);
    }

    @EnumSource(MenuItem.class)
    @DisplayName("Проверка поиска в меню яндекса")
    @ParameterizedTest(name = "по слову {0}")
    public void yandexSearchMenuTest(MenuItem testData) {
//          Предусловия:
        Selenide.open("https://ya.ru");
//          Шаги:
        $("#text").setValue("Allure TestOps");
        $("button[type='submit']").click();
//          Ожидаемый результат:
        $$(".navigation__item")
                .find(Condition.text(testData.rusName))
                .click();
        Assertions.assertEquals(
                2,
                WebDriverRunner.getWebDriver().getWindowHandles().size()
        );
    }

    @AfterEach
    public void close() {
        Selenide.closeWebDriver();
    }
}
