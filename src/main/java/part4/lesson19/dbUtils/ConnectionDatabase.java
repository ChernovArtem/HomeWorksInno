package part4.lesson19.dbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Подключение к БД
 */
public class ConnectionDatabase {

    /**
     * Подключение
     */
    private static Connection INSTANCE;

    /**
     *  Конструктор скрыт, клласс singleton
     */
    private ConnectionDatabase () {

    }

    /**
     * Получение подключения, если его еще н ебыло, то инициализируем
     * @return подключение
     */
    public static Connection getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/onlineShop",
                        "postgres",
                        "1");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }
}
