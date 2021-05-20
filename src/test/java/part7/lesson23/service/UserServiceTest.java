package part7.lesson23.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.pojo.User;
import part6.lesson22.service.UserService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private GeneralDao<User> userDao;

    @InjectMocks
    private UserService userService = new UserService();

    @Test
    @DisplayName("Получение всех пользователей без ошибки")
    void getAllUsersOK() throws SQLException {
        List<User> users = new ArrayList<>();
        User user = new User(1, "A", "B", "C", "D");
        users.add(user);

        when(userDao.getAll()).thenReturn(users);
        assertEquals(users, userService.getAllUsers());
    }

    @Test
    @DisplayName("Получение всех пользователей с ошибкой")
    void getAllUsersWithException() throws SQLException {
        when(userDao.getAll()).thenThrow(new SQLException("test"));
        assertEquals(Collections.emptyList(), userService.getAllUsers());
    }

    @Test
    @DisplayName("Получение пользователя по id без ошибки")
    void getUserByIdOK() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");

        when(userDao.getById(1)).thenReturn(user);
        assertEquals(user, userService.getUserById(1));
    }

    @Test
    @DisplayName("Получение пользователя по id с ошибкой")
    void getUserByIdWithException() throws SQLException {
        when(userDao.getById(1)).thenThrow(new SQLException("test"));
        assertNull(userService.getUserById(1));
    }

    @Test
    @DisplayName("Добавление пользователя без ошибки")
    void addUserOK() throws SQLException {
        assertTrue(userService.addUser("A", "B", "C", "D"));
    }

    @Test
    @DisplayName("Добавление пользователя с ошибкой")
    void addUserWithException() throws SQLException {
        User user = new User("A", "B", "C", "D");
        when(userDao.add(user)).thenThrow(new SQLException("test"));

        assertFalse(userService.addUser("A", "B", "C", "D"));
    }

    @Test
    @DisplayName("Обновление пользователя без ошибки")
    void updateAddressAndTelephoneUserByIdOK() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        when(userDao.getById(1)).thenReturn(user);

        assertTrue(userService.updateAddressAndTelephoneUserById(1, "B", "D"));
    }

    @Test
    @DisplayName("Обновление пользователя с ошибкой")
    void updateAddressAndTelephoneUserByIdWithException() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        when(userDao.getById(1)).thenReturn(user);
        when(userDao.update(user)).thenThrow(new SQLException("test"));

        assertFalse(userService.updateAddressAndTelephoneUserById(1, "B", "D"));
    }

    @Test
    @DisplayName("Удаление пользователя без ошибки")
    void deleteUserByIdOK() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        when(userDao.getById(1)).thenReturn(user);

        assertTrue(userService.deleteUserById(1));
    }

    @Test
    @DisplayName("Удаление пользователя с ошибкой")
    void deleteUserByIdWithException() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        when(userDao.getById(1)).thenReturn(user);
        when(userDao.delete(user)).thenThrow(new SQLException("test"));

        assertFalse(userService.deleteUserById(1));
    }
}