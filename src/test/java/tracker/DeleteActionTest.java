package tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DeleteActionTest {

    @Test
    public void whenDeleteItem() {
        String newMessage = "New Item";
        Store tracker = new MemTracker();
        Item item = new Item(newMessage);
        tracker.init();
        tracker.add(item);
        String[] answers = {String.valueOf(item.getId())};
        new DeleteAction().execute(new StubInput(answers), tracker, System.out::println);
        Item deleted = tracker.findById(item.getId());
        Item expected = null;
        assertThat(deleted, is(expected));
    }

}