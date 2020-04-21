package liquibase;

import org.junit.Ignore;
import org.junit.Test;
import tracker.Item;
import tracker.SqlTracker;
import tracker.Store;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
/**
 *
 */
@Ignore
public class Test1 {
    @Test
    public void createItem() {
        Store tracker = new SqlTracker();
        tracker.init();
        tracker.add(new Item("name"));
        assertThat(tracker.findByName("name").size(), is(1));
    }
}
