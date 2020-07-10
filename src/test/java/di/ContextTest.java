package di;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ContextTest {
    @Test
    public void whenAddAndPrint() {
        Context context = new Context();
        context.reg(Store.class);
        context.reg(StartUI.class);
        StartUI startUI = context.get(StartUI.class);
        startUI.add("Alex");
        startUI.add("Bob");
        startUI.add("Petr");
        List<String> expect = List.of("Alex", "Bob", "Petr");
        assertThat(startUI.getAll(), is(expect));
    }

}