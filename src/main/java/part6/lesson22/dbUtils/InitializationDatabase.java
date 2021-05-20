package part6.lesson22.dbUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Удаление, добавление и заполнение таблиц
 */
public class InitializationDatabase {

    private Logger log = LoggerFactory.getLogger(InitializationDatabase.class);

    /**
     * SQL на удаление таблиц
     */
    public static final String DELETE_TABLES = "DROP TABLE IF EXISTS orders_products;"
            + "DROP TABLE IF EXISTS orders;"
            + "DROP TABLE IF EXISTS users;"
            + "DROP TABLE IF EXISTS products;"
            + "DROP TABLE IF EXISTS logs;";

    /**
     * SQL на создание таблиц
     */
    public static final String CREATE_TABLES = "CREATE TABLE products (\n"
            + "    id bigserial primary key,\n"
            + "    type varchar(100) NOT NULL,"
            + "    manufacturer varchar(100) NOT NULL,"
            + "    name varchar(100) NOT NULL,"
            + "    price integer NOT NULL);"
            + "\n"
            + "CREATE TABLE users (\n"
            + "    id bigserial primary key,\n"
            + "    fio varchar(100) NOT NULL,\n"
            + "    address varchar(100) NOT NULL,\n"
            + "    email varchar(100) NOT NULL,\n"
            + "    telephone varchar(12) NOT NULL);"
            + "\n"
            + "CREATE TABLE orders (\n"
            + "    id bigserial primary key,\n"
            + "    status varchar(100) NOT NULL,\n"
            + "    user_id bigint REFERENCES users NOT NULL);"
            + "\n"
            + "CREATE TABLE orders_products (\n"
            + "    order_id bigint REFERENCES orders NOT NULL,\n"
            + "    product_id bigint REFERENCES products NOT NULL);"
            + "\n"
            + "CREATE TABLE logs (\n"
            + "    logId varchar(100) primary key,\n"
            + "    eventDate timestamp,\n"
            + "    level varchar(100),\n"
            + "    logger varchar(100),\n"
            + "    message text,\n"
            + "    exception text);";

    /**
     * SQL на заполнение таблиц
     */
    public static final String INSERT_TABLES = "INSERT INTO products (type, manufacturer, name, price)\n"
            + "VALUES\n"
            + "   ('Телефон', 'Xiaomi', 'MI 10T', 20000),\n"
            + "   ('Телефон', 'Xiaomi', 'MI 10', 15000),\n"
            + "   ('Телефон', 'Xiaomi', 'MI 10Pro', 25000),\n"
            + "   ('Телефон', 'Apple', 'Iphone 12', 100400),\n"
            + "   ('Телефон', 'Apple', 'Iphone 12SE', 100450),\n"
            + "   ('Телефон', 'Apple', 'Iphone 12Pro', 100500),\n"
            + "   ('Планшет', 'Apple', 'Ipad', 210000),\n"
            + "   ('Планшет', 'Lenova', 'Yoga111', 234567),\n"
            + "   ('Планшет', 'Samsung', 'A23', 45000);"
            + "\n"
            + "INSERT INTO users (fio, address, email, telephone)\n"
            + "VALUES\n"
            + "   ('Иванов Иван Иванович', 'Москва', 'ivanov@mail.ru', '+79990003322'),\n"
            + "   ('Алексеев Алексей Алексеевич', 'Санкт-Петербург', 'alexeev@mail.ru', '+79991114433'),\n"
            + "   ('Петров Петр Петрович', 'Новосибирск', 'petrov@mail.ru', '+79992225566'),\n"
            + "   ('Васильев Василий Васильевич', 'Владивосток', 'vasiliev@mail.ru', '+79993337788');"
            + "\n"
            + "INSERT INTO orders (status, user_id)\n"
            + "VALUES\n"
            + "   ('Выполнен', 1),\n"
            + "   ('В работе', 4),\n"
            + "   ('В доставке', 1),\n"
            + "   ('Ожидает', 4),\n"
            + "   ('Выполнен', 3);"
            + "\n"
            + "INSERT INTO orders_products (order_id, product_id)\n"
            + "VALUES\n"
            + "   (1, 4),\n"
            + "   (2, 7),\n"
            + "   (3, 1),\n"
            + "   (3, 9),\n"
            + "   (4, 2),\n"
            + "   (5, 8);";

    private ConnectionDatabase connectionDatabase = new ConnectionDatabase();

    /**
     * Инициализация бд (удаление прошлых таблиц, создание новых и заполнение)
     */
    public void initTables() {
        log.debug("Start method initTables()");
        deleteTables();
        createTables();
        insertTables();
        log.debug("Finish method initTables()");
    }

    /**
     * Удаление таблиц
     * @throws SQLException ошибка связанная с SQL
     */
    public void deleteTables() {
        try {
            log.debug("Method deleteTables()");
            executeSQL(DELETE_TABLES);
        } catch (SQLException e) {
            log.error("SQLException in method deleteTables()", e);
        }
    }

    /**
     * Создание таблиц
     * @throws SQLException ошибка связанная с SQL
     */
    public void createTables() {
        try {
            log.debug("Method createTables()");
            executeSQL(CREATE_TABLES);
        } catch (SQLException e) {
            log.error("SQLException in method createTables()", e);
        }
    }

    /**
     * Заполнение таблиц
     * @throws SQLException ошибка связанная с SQL
     */
    public void insertTables()  {
        try {
            log.debug("Method insertTables()");
            executeSQL(INSERT_TABLES);
        } catch (SQLException e) {
            log.error("SQLException in method insertTables()", e);
        }
    }

    /**
     * Метод выполнения SQL
     * @param sql
     * @throws SQLException ошибка связанная с SQL
     */
    private void executeSQL(String sql) throws SQLException {
        try (Connection connection = connectionDatabase.getConnection();
             Statement statement = connection.createStatement()) {

            log.debug("Method executeSQL(sql)");
            log.debug("SQL: {}", sql);

            statement.execute(sql);
            connection.commit();
        }
    }
}
