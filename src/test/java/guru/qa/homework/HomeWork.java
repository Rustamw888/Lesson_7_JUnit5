package guru.qa.homework;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.homework.enums.Sections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Класс с тестами по домашней работе")
public class HomeWork {

    @BeforeAll
    public void setUp() {
        baseUrl = "https://www.ivi.ru";
        browserSize = "1920x1080";
    }

    @ValueSource(strings = {
            "Звездные войны",
            "Властелин колец"
    })
    @DisplayName("Проверка работы блока 'поиск'")
    @ParameterizedTest(name = "находим все элементы содержащие фразу запроса: {0}")
    public void cinemaSearching(String valueName) {
        // Предусловия:
        open(baseUrl);
        // Шаги:
        $("[data-test='header_search']").click();
        $("[data-test='search_input']").setValue(valueName).submit();
        // Ожидаемый результат:
        $$(".searchResultItem")
                .find(text(valueName))
                .shouldBe(visible);
    }

    @CsvSource(value = {
            "Я – легенда    *    2",
            "В погоне за счастьем * 1"
    },
    delimiter = '*')
    @DisplayName("Проверка количества результатов")
    @ParameterizedTest(name = "находим элементы {0}, в количестве: {1}")
    public void searchResultChecking(String valueName, int sizeValue) {
        // Предусловия:
        open(baseUrl);
        // Шаги:
        $("[data-test='header_search']").click();
        $("[data-test='search_input']").setValue(valueName).submit();
        // Ожидаемый результат:
        $$(".searchResultItem")
                .find(text(valueName))
                .shouldBe(visible)
                .getSize().equals(sizeValue);
    }

    static Stream<Arguments> filterValues() {
        return Stream.of(
                Arguments.of("Рейтинг ivi", List.of(4, 1)),
                Arguments.of("Годы", List.of(14, 1))
        );
    }

    @MethodSource("filterValues")
    @DisplayName("Проверка содержимого фильтра")
    @ParameterizedTest(name = "в разделе {0}, количество подразделов {1}")
    public void filterChecking(String sectionName, List<Integer> sum) {
        // Предусловия:
        open("/movies");
        // Шаги:
        $$(".filtersDesktop__plank")
                .find(text(sectionName))
                .shouldBe(visible)
                .click();
        $(".filtersDesktop__plank").shouldBe(visible).getAttribute("class").contains("isActive");
        // Ожидаемый результат:
        int counterChecked = 0;
        int counterUnChecked = 0;
        ElementsCollection collection = $$(".filterDropdown__item_radio");
        if (!collection.isEmpty()) {
            for (SelenideElement element: collection) {
                if (element.getAttribute("class").contains("checked")) {
                    counterChecked++;
                } else {
                    counterUnChecked++;
                }
            }
        }
        Assertions.assertEquals(counterChecked, sum.get(1));
        Assertions.assertEquals(counterUnChecked, sum.get(0));
    }

    @EnumSource(Sections.class)
    @DisplayName("Проверка главного меню на отображение элементов")
    @ParameterizedTest(name = "по слову {0}")
    public void menuTesting(Sections dataName) {
        // Предусловия:
        open(baseUrl);
        // Шаги:
        $$(".headerMenu__listItem>a")
                .find(text(dataName.sectionName))
                .click();
        // Ожидаемый результат:
        $("h1.headerBar__title")
                .shouldHave(text(dataName.sectionName + " смотреть онлайн"));
    }
}
