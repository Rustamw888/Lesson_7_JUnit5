package guru.qa.homework.homeWorkJUnit;

import guru.qa.lesson.SimpleTest;
import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HomeWorkJUnit {

    public static void main(String[] args) throws Exception {

        // находит классы с тестами
        Method[] declaredMethods = SimpleTest.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            method.setAccessible(true);
            // смотрит есть ли над методом @Test и @Disabled
            BeforeEach beforeEachAnnotation = method.getAnnotation(BeforeEach.class);
            BeforeAll beforeAllAnnotation = method.getAnnotation(BeforeAll.class);
            AfterEach afterEachAnnotation = method.getAnnotation(AfterEach.class);
            AfterAll afterAllAnnotation = method.getAnnotation(AfterAll.class);
            Test testAnnotation = method.getAnnotation(Test.class);
            Disabled disabled = method.getAnnotation(Disabled.class);
            if (beforeAllAnnotation != null) {
                // Если не null, вызовет все методы под этой аннотацией / для всех, значит должен идти вначале всего кода
                method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
            }
            if (testAnnotation != null && disabled == null) {
                if (beforeEachAnnotation != null) {
                    // Если не null, вызовет все методы под этой аннотацией / для каждого, значит должен быть внутри цикла, в начале
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                }
                // запускает
                try {
                    // если не упал, то все ОК
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    // если тест упал, сообщает нам
                    System.out.println("Тест упал: " + e.getCause().getMessage());
                    throw e;
                }
                if (afterEachAnnotation != null) {
                    // Если не null, вызовет все методы под этой аннотацией / для каждого, значит должен быть внутри цикла, в конце
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                }
            }
            if (afterAllAnnotation != null) {
                // Если не null, вызовет все методы под этой аннотацией / для всех, значит должен идти после выполнения всех методов
                method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
            }
        }
    }
}
