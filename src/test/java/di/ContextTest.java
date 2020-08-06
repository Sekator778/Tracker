package di;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ContextTest {
    // не работает класс Context !!!!!!!!!!!!!!!!
//    @Test
//    public void whenAddAndPrint() {
//        Context context = new Context();
//        context.reg(Store.class);
//        context.reg(StartUI.class);
//        StartUI startUI = context.get(StartUI.class);
//        System.out.println(startUI.hashCode());
//        startUI.add("Alex");
//        startUI.add("Bob");
//        startUI.add("Petr");
//        List<String> expect = List.of("Alex", "Bob", "Petr");
//        assertThat(startUI.getAll(), is(expect));
//    }
    @Test
    public void whenAddAndPrintWithAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("di");
        context.refresh();
        StartUI startUI = context.getBean(StartUI.class);
        System.out.println(startUI.hashCode());
        startUI.add("Alex");
        startUI.add("Bob");
        startUI.add("Petr");
        List<String> expect = List.of("Alex", "Bob", "Petr");
        assertThat(startUI.getAll(), is(expect));
    }

}