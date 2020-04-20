package tracker;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class CreateActionTest {

    @Test
    public void whenDeleteItem() {
        String newMessage = "New Item";
        Store tracker = new SqlTracker();
        String[] answers = {newMessage};
        new CreateAction().execute(new StubInput(answers), tracker, System.out::println);
        Item added = tracker.findAll().get(4);
        assertThat(added.getName(), is(newMessage));
    }
}