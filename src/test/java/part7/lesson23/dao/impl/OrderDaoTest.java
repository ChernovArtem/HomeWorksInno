package part7.lesson23.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dao.impl.OrderDao;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.Order;
import part6.lesson22.pojo.Product;
import part6.lesson22.pojo.User;
import part6.lesson22.service.utils.OrderUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static part6.lesson22.dao.impl.OrderDao.*;

@ExtendWith(MockitoExtension.class)
class OrderDaoTest {

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    ResultSet resultSet;

    @Mock
    OrderUtils orderUtils;

    @InjectMocks
    private OrderDao orderDao = new OrderDao();

    @BeforeEach
    void setUp() throws SQLException {
        when(connectionDatabase.getConnection()).thenReturn(connection);
    }

    @Test
    @DisplayName("Получение всех заказов без ошибки")
    void getAllOK() throws SQLException {
        when(connection.prepareStatement(SELECT_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false, false);

        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Тип заказа");
        when(resultSet.getInt(3)).thenReturn(1);

        when(connection.prepareStatement(SELECT_PRODUCTS)).thenReturn(statement);
        when(resultSet.getInt(2)).thenReturn(1);

        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);

        when(orderUtils.getUserById(1)).thenReturn(user);
        when(orderUtils.getProductById(1)).thenReturn(product);

        List<Order> orders = new ArrayList<>();
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        orders.add(order);

        assertEquals(orders, orderDao.getAll());
    }

    @Test
    @DisplayName("Получение всех заказов с ошибкой")
    void getAllWithException() throws SQLException {
        when(connection.prepareStatement(SELECT_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("test"));

        assertEquals(new ArrayList<>(), orderDao.getAll());
    }

    @Test
    @DisplayName("Получение заказа по id без ошибки")
    void getByIdOK() throws SQLException {
        when(connection.prepareStatement(SELECT_BY_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Тип заказа");
        when(resultSet.getInt(3)).thenReturn(1);

        when(connection.prepareStatement(SELECT_PRODUCTS)).thenReturn(statement);
        when(resultSet.getInt(2)).thenReturn(1);

        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);

        when(orderUtils.getUserById(1)).thenReturn(user);
        when(orderUtils.getProductById(1)).thenReturn(product);

        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        assertEquals(order, orderDao.getById(1));
    }

    @Test
    @DisplayName("Получение заказа по id с ошибкой")
    void getByIdWithException() throws SQLException {
        when(connection.prepareStatement(SELECT_BY_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("test"));

        assertNull(orderDao.getById(1));
    }

    @Test
    @DisplayName("Добавление заказа без ошибки")
    void addOK() throws SQLException {
        when(connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);

        when(connection.prepareStatement(INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        assertTrue(orderDao.add(order));
    }

    @Test
    @DisplayName("Добавление заказа с ошибкой")
    void addWithException() throws SQLException {
        when(connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        assertFalse(orderDao.add(order));
    }

    @Test
    @DisplayName("Обновление заказа без ошибки")
    void updateOK() throws SQLException {
        when(connection.prepareStatement(UPDATE)).thenReturn(statement);

        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        assertTrue(orderDao.update(order));
    }

    @Test
    @DisplayName("Обновление заказа с ошибкой")
    void updateWithException() throws SQLException {
        when(connection.prepareStatement(UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        Order order = new Order(1, "Тип заказа", null, null);
        assertFalse(orderDao.update(order));
    }

    @Test
    @DisplayName("Удаление заказа без ошибки")
    void deleteOK() throws SQLException {
        when(connection.prepareStatement(DELETE)).thenReturn(statement);

        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        assertTrue(orderDao.delete(order));
    }

    @Test
    @DisplayName("Удаление заказа с ошибкой")
    void deleteWithException() throws SQLException {
        when(connection.prepareStatement(DELETE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        Order order = new Order(1, "Тип заказа", null, null);
        assertFalse(orderDao.delete(order));
    }
}