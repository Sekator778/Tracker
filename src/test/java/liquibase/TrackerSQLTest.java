package liquibase;

import org.junit.Ignore;
import org.junit.Test;
import tracker.ConnectionRollback;
import tracker.SqlTracker;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 *
 */
@Ignore
public class TrackerSQLTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
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

    @Test
    public void checkConnection() throws SQLException {
        SqlTracker sqlTracker = new SqlTracker();
        ConnectionRollback.create(this.init());
//        assertThat(sqlTracker.init(), is(true));
    }
}
