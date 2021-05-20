package part7.lesson23.dbUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.dbUtils.InitializationDatabase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static part6.lesson22.dbUtils.InitializationDatabase.*;

@ExtendWith(MockitoExtension.class)
class InitializationDatabaseTest {

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @InjectMocks
    private InitializationDatabase initializationDatabase = new InitializationDatabase();

    @BeforeEach
    void setUp() throws SQLException {
        when(connectionDatabase.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    @DisplayName("Инициализация БД")
    void initTables() {
        assertDoesNotThrow(() -> initializationDatabase.initTables());
    }

    @Test
    @DisplayName("Удаление БД без ошибки")
    void deleteTablesOK() throws SQLException {
        when(statement.execute(DELETE_TABLES)).thenReturn(true);
        assertDoesNotThrow(() -> initializationDatabase.deleteTables());
    }

    @Test
    @DisplayName("Удаление БД с ошибкой")
    void deleteTablesWithException() throws SQLException {
        when(statement.execute(DELETE_TABLES)).thenThrow(new SQLException("test"));
        assertDoesNotThrow(() -> initializationDatabase.deleteTables());
    }

    @Test
    @DisplayName("Создание БД без ошибки")
    void createTablesOK() throws SQLException {
        when(statement.execute(CREATE_TABLES)).thenReturn(true);
        assertDoesNotThrow(() -> initializationDatabase.createTables());
    }

    @Test
    @DisplayName("Создание БД с ошибкой")
    void createTablesWithException() throws SQLException {
        when(statement.execute(CREATE_TABLES)).thenThrow(new SQLException("test"));
        assertDoesNotThrow(() -> initializationDatabase.createTables());
    }

    @Test
    @DisplayName("Внесение данных в БД без ошибки")
    void insertTablesOK() throws SQLException {
        when(statement.execute(INSERT_TABLES)).thenReturn(true);
        assertDoesNotThrow(() -> initializationDatabase.insertTables());
    }

    @Test
    @DisplayName("Внесение данных в БД с ошибкой")
    void insertTablesWithException() throws SQLException {
        when(statement.execute(INSERT_TABLES)).thenThrow(new SQLException("test"));
        assertDoesNotThrow(() -> initializationDatabase.insertTables());
    }
}