package hbmtest;

import hibernate.HbmTracker;
import org.junit.Test;
import tracker.Item;
import tracker.Store;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 */

public class HbmTrackerTest {

    @Test
    public void whenFindByName() {
        Store store = new HbmTracker();
        Item item = store.findAll().get(0);
       String name = item.getName();
       assertThat(item, is(store.findByName(name).get(0)));
    }

    @Test
    public void whenFindByNameCriteriaAndHQL() {
        HbmTracker store = new HbmTracker();
        String name = store.findAll().get(0).getName();
        List<Item> listCriteria = store.findByName(name);
        List<Item> listHQL = store.findByName2(name);
        listHQL.forEach(System.out::println);
        assertThat(listCriteria, is(listHQL));
    }
}
