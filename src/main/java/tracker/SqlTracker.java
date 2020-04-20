package tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlTracker implements Store {
    private Connection connection;
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker.class.getName());

//    /**
//     * конструктор сразу с
//     *
//     * @param connection - соединение к БД
//     */
//    public SqlTracker(Connection connection) {
//        this.connection = connection;
//    }

    /**
     * тут надо разобраться четко с потоком из файла app.properties
     * потом с пропертью а так все просто надо запомнить последовательность
     */
    public void init() {
        System.out.println("up");
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * через соединение с помощью класса PreparedStatement
     * дабавляем итем
     * и с помощью ResultSet получаем номера столбцов тоблицы
     * и этот номер ставим в Id item
     *
     * @param item - item
     * @return item
     */
    @Override
    public Item add(Item item) {
        Item rsl = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into items (name) values (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            int rows = preparedStatement.executeUpdate();
            System.out.printf("%d rows added \n", rows);
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                item.setId(String.valueOf(id));
                rsl = item;
            } else {
                throw new SQLException("Not key for Item");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * тут пока мне не ясно
     * так как сначало мы отпраляем иммя потом ид
     * надо апдейт потренировать
     *
     * @param id   - номер строчки по сути
     * @param item итем чтоб дернуть его имя
     * @return строки в таблице изменились ?
     */
    @Override
    public boolean replace(String id, Item item) {
        boolean rsl = false;

        try (PreparedStatement statement = connection.prepareStatement("update items set name = ? where id = ?")) {
            statement.setString(1, item.getName());
            statement.setInt(2, Integer.parseInt(id));
            int rows = statement.executeUpdate();
            if (rows == 1) {
                rsl = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * метод получает строку где есть ид итема
     * connection.prepareStatement - удаляэм запись из таблицы под номером итема
     * и все
     *
     * @param id - номер итема для удаления
     * @return результат
     */
    @Override
    public boolean delete(String id) {
        boolean rsl = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from items where id = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            rsl = true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * делаем селект полей и вот норм метод  ResultSet set = statement.executeQuery();
     * по сути все запихиваем в множество сет ?? интересно или могут там быть дубли ?? это ж сет
     * а потом из сета по номеру коллонки значения в итем новый и его в лист на возврат
     *
     * @return лист
     */
    @Override
    public List<Item> findAll() {
        List<Item> rsl = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select items.id, name from items")) {
            ResultSet set = statement.executeQuery();
            rsl = findSet(statement);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * тут дубль кода пишет если будет верно надо вынести в метод который в лист инфу тащит по statement.executeQuery()
     * statement.executeQuery() =  норм метод уже писал он дает нам множество а это норм там есть все
     *
     * @param key кей тут имя итема выступает ключем для поиска
     * @return лист итемов
     */
    @Override
    public List<Item> findByName(String key) {
        List<Item> rsl = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select items.id, name from items where name = ?")) {
            statement.setString(1, key);
            rsl = findSet(statement);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }


    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement statement = connection.prepareStatement("select items.id, name from items where id = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            List<Item> rsl = findSet(statement);
            if (!rsl.isEmpty()) {
                item = rsl.get(0);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    /**
     * вынес то что писал код повторяеться
     * statement.executeQuery() =  норм метод уже писал он дает нам множество а это норм там есть все
     *
     * @param statement для выполнения запросов четкий класс PreparedStatement
     * @return лист
     */
    private List<Item> findSet(PreparedStatement statement) {
        List<Item> result = new ArrayList<>();
        try (ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                Item item = new Item(name);
                item.setId(String.valueOf(id));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}