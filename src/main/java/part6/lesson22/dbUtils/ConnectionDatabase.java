package part6.lesson22.dbUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Подключение к БД
 */
public class ConnectionDatabase {

    private static final Logger log = LoggerFactory.getLogger(ConnectionDatabase.class);

    /**
     * Получение подключения
     * @return подключение
     */
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/onlineShop",
                    "postgres",
                    "1");
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            log.error("SQLException in method getConnection()", e);
        }
        return null;
    }
}
