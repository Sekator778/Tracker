package tracker;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class CreateActionTest {
    /**
     * додаем итем с именем
     * в конец базы данных
     * и сверяем его имя
     */
    @Test
    public void whenAddItemToEndListItems() {
        String newMessage = "This name item just add";
        Store tracker = new SqlTracker();
        String[] answers = {newMessage};
        tracker.init();
        new CreateAction().execute(new StubInput(answers), tracker, System.out::println);
        List<Item> items = tracker.findAll();
        Item added = items.get(items.size() - 1);
        assertThat(added.getName(), is(newMessage));
    }
}