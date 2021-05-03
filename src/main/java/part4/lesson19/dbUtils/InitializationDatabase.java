package part4.lesson19.dbUtils;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Удаление, добавление и заполнение таблиц
 */
public class InitializationDatabase {

    /**
     * SQL на удаление таблиц
     */
    private static final String DELETE_TABLES = "DROP TABLE IF EXISTS orders_products;"
            + "DROP TABLE IF EXISTS orders;"
            + "DROP TABLE IF EXISTS users;"
            + "DROP TABLE IF EXISTS products;";

    /**
     * SQL на создание таблиц
     */
    private static final String CREATE_TABLES = "CREATE TABLE products (\n"
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
            + "    product_id bigint REFERENCES products NOT NULL);";

    /**
     * SQL на заполнение таблиц
     */
    private static final String INSERT_TABLES = "INSERT INTO products (type, manufacturer, name, price)\n"
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

    /**
     * Инициализация бд (удаление прошлых таблиц, создание новых и заполнение)
     */
    public static void initTables() {
        deleteTables();
        createTables();
        insertTables();
    }

    /**
     * Удаление таблиц
     * @throws SQLException ошибка связанная с SQL
     */
    public static void deleteTables() {
        try {
            executeSQL(DELETE_TABLES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создание таблиц
     * @throws SQLException ошибка связанная с SQL
     */
    public static void createTables() {
        try {
            executeSQL(CREATE_TABLES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Заполнение таблиц
     * @throws SQLException ошибка связанная с SQL
     */
    public static void insertTables()  {
        try {
            executeSQL(INSERT_TABLES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод выполнения SQL
     * @param sql
     * @throws SQLException ошибка связанная с SQL
     */
    private static void executeSQL(String sql) throws SQLException {
        try (Statement statement = ConnectionDatabase.getInstance().createStatement()) {
            statement.execute(sql);
        }
    }
}
