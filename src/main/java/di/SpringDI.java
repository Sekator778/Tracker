package di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring DI. Запуск.
 * Мы регистрируем классы, а контекст отдает объекты.
 */

public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("di");
        context.refresh();
        StartUI startUI = context.getBean(StartUI.class);
        startUI.add("Bob");
        startUI.add("Alex");
        startUI.print();
        StartUI startUI2 = context.getBean(StartUI.class);
        System.out.println(startUI == startUI2);
        context.close();
    }
}
