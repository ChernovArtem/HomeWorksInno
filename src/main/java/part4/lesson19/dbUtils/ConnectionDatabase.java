package part4.lesson19.dbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Подключение к БД
 */
public class ConnectionDatabase {

    /**
     *  Конструктор скрыт, класс singleton
     */
    private ConnectionDatabase () {

    }

    /**
     * Получение подключения
     * @return подключение
     */
    public static Connection getInstance() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/onlineShop",
                    "postgres",
                    "1");
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
