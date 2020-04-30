package liquibase;

import org.junit.Ignore;
import org.junit.Test;
import tracker.ConnectionRollback;
import tracker.Item;
import tracker.TrackerSQL;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * https://habr.com/ru/post/436994/
 * перед тестами нужна чистая таблица  почему так ??
 *
 */
@Ignore
public class SQLTrackerTest {

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * проверяем метод создания итема
     * меряем размер масива %)
     */
    @Test
    public void createItem() {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            assertThat(tracker.findByName("name").size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * добавляем 3 итема и вытаскиваем и сравниваем два листа
     * @throws SQLException - ошибка
     */
    @Test
    public void whenFindAllShouldFoundAllItems() throws SQLException {
        TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()));
        Item item1 = new Item("itemOne");
        Item item2 = new Item("itemTwo");
        Item item3 = new Item("itemThree");
        sql.add(item1);
        sql.add(item2);
        sql.add(item3);
        List<Item> expected = List.of(item1, item2, item3);
        assertThat(sql.findAll(), is(expected));
    }

    @Test
    public void whenCreateItemAndGetByIdShouldBeSame() throws SQLException {
        TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()));
        Item item = new Item("test item");
        Item expected = sql.add(item);
        Item actual = sql.findById(expected.getId());
        assertThat(actual, is(expected));
    }

    @Test
    public void whenDeleteItemByIdShouldBeDeleted() throws SQLException {
        TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()));
        Item item = new Item("delete item");
        sql.add(item);
        assertTrue(sql.delete(item.getId()));
        assertNull(sql.findById(item.getId()));
        assertTrue(sql.delete(item.getId()));
    }

    @Test
    public void whenReplaceItemAndGetByIdShouldBeReplaced() throws SQLException {
        TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()));
        Item item = new Item("replace item");
        sql.add(item);
        item.setName("replaced");
        assertTrue(sql.replace(item.getId(), item));
        assertThat(item, is(sql.findById(item.getId())));
    }

    @Test
    public void whenFindItemByNameShouldFoundAllItems() throws SQLException {
        TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()));
        Item item1 = new Item("same item");
        Item item2 = new Item("same item");
        Item item3 = new Item("same item");
        sql.add(item1);
        sql.add(item2);
        sql.add(item3);
        List<Item> expected = List.of(item1, item2, item3);
        assertTrue(sql.findByName("same item").containsAll(expected));
    }
}
