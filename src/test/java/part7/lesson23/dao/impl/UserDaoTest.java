package part7.lesson23.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dao.impl.UserDao;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static part6.lesson22.dao.impl.UserDao.*;

@ExtendWith(MockitoExtension.class)
class UserDaoTest {

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    ResultSet resultSet;

    @InjectMocks
    private UserDao userDao = new UserDao();

    @BeforeEach
    void setUp() {
        when(connectionDatabase.getConnection()).thenReturn(connection);
    }

    @Test
    @DisplayName("Получение всех пользователей без ошибки")
    void getAllOK() throws SQLException {
        when(connection.prepareStatement(SELECT_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("A");
        when(resultSet.getString(3)).thenReturn("B");
        when(resultSet.getString(4)).thenReturn("C");
        when(resultSet.getString(5)).thenReturn("D");

        List<User> users = new ArrayList<>();
        User user = new User(1, "A", "B", "C", "D");
        users.add(user);

        assertEquals(users, userDao.getAll());
    }

    @Test
    @DisplayName("Получение всех пользователей с ошибкой")
    void getAllWithException() throws SQLException {
        when(connection.prepareStatement(SELECT_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("test"));

        assertEquals(new ArrayList<>(), userDao.getAll());
    }

    @Test
    @DisplayName("Получение пользователя по id без ошибки")
    void getByIdOK() throws SQLException {
        when(connection.prepareStatement(SELECT_BY_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("A");
        when(resultSet.getString(3)).thenReturn("B");
        when(resultSet.getString(4)).thenReturn("C");
        when(resultSet.getString(5)).thenReturn("D");

        User user = new User(1, "A", "B", "C", "D");
        assertEquals(user, userDao.getById(1));
    }

    @Test
    @DisplayName("Получение пользователя по id с ошибкой")
    void getByIdWithException() throws SQLException {
        when(connection.prepareStatement(SELECT_BY_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("test"));

        assertNull(userDao.getById(1));
    }

    @Test
    @DisplayName("Добавление пользователя без ошибки")
    void addOK() throws SQLException {
        when(connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);

        User user = new User(1, "A", "B", "C", "D");
        assertTrue(userDao.add(user));
    }

    @Test
    @DisplayName("Добавление пользователя с ошибкой")
    void addWithException() throws SQLException {
        when(connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        User user = new User(1, "A", "B", "C", "D");
        assertFalse(userDao.add(user));
    }

    @Test
    @DisplayName("Обновление пользователя без ошибки")
    void updateOK() throws SQLException {
        when(connection.prepareStatement(UPDATE)).thenReturn(statement);

        User user = new User(1, "A", "B", "C", "D");
        assertTrue(userDao.update(user));
    }

    @Test
    @DisplayName("Обновление пользователя с ошибкой")
    void updateWithException() throws SQLException {
        when(connection.prepareStatement(UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        User user = new User(1, "A", "B", "C", "D");
        assertFalse(userDao.update(user));
    }

    @Test
    @DisplayName("Удаление пользователя без ошибки")
    void deleteOK() throws SQLException {
        when(connection.prepareStatement(DELETE)).thenReturn(statement);

        User user = new User(1, "A", "B", "C", "D");
        assertTrue(userDao.delete(user));
    }

    @Test
    @DisplayName("Удаление пользователя с ошибкой")
    void deleteWithException() throws SQLException {
        when(connection.prepareStatement(DELETE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        User user = new User(1, "A", "B", "C", "D");
        assertFalse(userDao.delete(user));
    }
}