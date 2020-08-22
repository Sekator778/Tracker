package tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * для таких тестов нужно таблицу всегда чистую
 * как это делать пока не знаю
 * 22.08.20 уже знаю %)
 */

public class StartUITest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Test
    public void whenInit() {
        Consumer<String> stringConsumer = System.out::println;
        StubInput input = new StubInput(new String[]{"0"});
        StubAction action = new StubAction();
        new StartUI().init(input, new MemTracker(), Arrays.asList(new UserAction[]{action}), stringConsumer);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Menu.")
                .add("0. Stub action")
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }

    @Test
    public void whenFindAllTest() {
        Store tracker = new MemTracker();
        Item item = new Item("how how");
        tracker.init();
        tracker.add(item);
        ShowAllAction act = new ShowAllAction();
        Consumer<String> stringConsumer = System.out::println;
        act.execute(new StubInput(new String[]{}), tracker, stringConsumer);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("name: " + item.getName() + " id:" + item.getId())
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }

    @Test
    public void whenFindByNameActionTest() {
        Store tracker = new MemTracker();
        Item item = new Item("find me");
        tracker.init();
        tracker.add(item);
        FindByNameAction operate = new FindByNameAction();
        Consumer<String> stringConsumer = System.out::println;
        operate.execute(new StubInput(new String[]{"find me"}), tracker, stringConsumer);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("name: " + item.getName() + " id:" + item.getId())
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }
}
