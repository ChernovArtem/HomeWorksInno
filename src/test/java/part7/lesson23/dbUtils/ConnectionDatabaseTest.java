package part7.lesson23.dbUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import part6.lesson22.dbUtils.ConnectionDatabase;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionDatabaseTest {

    @Test
    @DisplayName("Проверка подключения")
    void getConnection() {
        ConnectionDatabase connectionDatabase = new ConnectionDatabase();
        Connection connection = connectionDatabase.getConnection();
        assertNotNull(connection);
    }
}
