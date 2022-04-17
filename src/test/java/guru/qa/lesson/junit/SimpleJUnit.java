package guru.qa.lesson.junit;

import guru.qa.lesson.SimpleTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleJUnit {

    public static void main(String[] args) throws Exception {

        // находит классы с тестами
        Method[] declaredMethods = SimpleTest.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            method.setAccessible(true);
            // смотрит есть ли над методом @Test и @Disabled
            Test testAnnotation = method.getAnnotation(Test.class);
            Disabled disabled = method.getAnnotation(Disabled.class);
            if (testAnnotation != null && disabled == null) {
                // запускает
                try {
                    // если не упал, то все ОК
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    // если тест упал, сообщает нам
                    System.out.println("Тест упал: " + e.getCause().getMessage());
                    throw e;
                }
            }
        }
    }
}
