package tracker;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReplaceActionTest {

    @Test
    public void whenReplaceItem() {
        String newMessage = "New Item";
        String replacedMessage = "Replaced Item";
        Store tracker = new SqlTracker();
        Item item = new Item(newMessage);
        tracker.add(item);
        String[] answers = {item.getId(), replacedMessage};
        new ReplaceItemAction().execute(new StubInput(answers), tracker, System.out::println);
        Item replaced = tracker.findById(item.getId());
        assertThat(replaced.getName(), is(replacedMessage));
    }

    @Test
    public void whenReplaceItemOnItemWithNullName() {
        String newMessage = "New Item";
        String replacedMessage = null;
        Store tracker = new SqlTracker();
        Item item = new Item(newMessage);
        tracker.add(item);
        String[] answers = {item.getId(), replacedMessage};
        new ReplaceItemAction().execute(new StubInput(answers), tracker, System.out::println);
        Item replaced = tracker.findById(item.getId());
        assertThat(replaced.getName(), is(replacedMessage));
    }

    @Test
    public void whenReplacingItemHasNullName() {
        String newMessage = null;
        String replacedMessage = "Replaced Item";
        Store tracker = new SqlTracker();
        Item item = new Item(newMessage);
        tracker.add(item);
        String[] answers = {item.getId(), replacedMessage};
        new ReplaceItemAction().execute(new StubInput(answers), tracker, System.out::println);
        Item replaced = tracker.findById(item.getId());
        assertThat(replaced.getName(), is(replacedMessage));
    }

}