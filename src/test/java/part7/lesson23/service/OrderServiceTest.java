package part7.lesson23.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.pojo.Order;
import part6.lesson22.pojo.Product;
import part6.lesson22.pojo.User;
import part6.lesson22.service.OrderService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private GeneralDao<Order> orderDao;

    @InjectMocks
    private OrderService orderService = new OrderService();

    @Test
    @DisplayName("Получение всех заказов без ошибки")
    void getAllOrdersOK() throws SQLException {
        List<Order> orders = new ArrayList<>();
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));
        orders.add(order);

        when(orderDao.getAll()).thenReturn(orders);
        assertEquals(orders, orderService.getAllOrders());
    }

    @Test
    @DisplayName("Получение всех заказов с ошибкой")
    void getAllOrdersWithException() throws SQLException {
        when(orderDao.getAll()).thenThrow(new SQLException("test"));
        assertEquals(Collections.emptyList(), orderService.getAllOrders());
    }

    @Test
    @DisplayName("Добавление заказа без ошибки")
    void addOrderOK() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);

        assertTrue(orderService.addOrder("Тип заказа", user, Collections.singletonList(product)));
    }

    @Test
    @DisplayName("Добавление заказа с ошибкой")
    void addOrderWithException() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order("Тип заказа", user, Collections.singletonList(product));

        when(orderDao.add(order)).thenThrow(new SQLException("test"));
        assertFalse(orderService.addOrder("Тип заказа", user, Collections.singletonList(product)));
    }

    @Test
    @DisplayName("Обновление заказа без ошибки")
    void updateStatusOrderByIdOK() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));

        when(orderDao.getById(1)).thenReturn(order);
        assertTrue(orderService.updateStatusOrderById(1, "Тип заказа"));
    }

    @Test
    @DisplayName("Обновление заказа с ошибкой")
    void updateStatusOrderByIdWithException() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));

        when(orderDao.getById(1)).thenReturn(order);
        when(orderDao.update(order)).thenThrow(new SQLException("test"));
        assertFalse(orderService.updateStatusOrderById(1, "Тип заказа"));
    }

    @Test
    @DisplayName("Удаление заказа без ошибки")
    void deleteOrderByIdOK() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));

        when(orderDao.getById(1)).thenReturn(order);
        assertTrue(orderService.deleteOrderById(1));
    }

    @Test
    @DisplayName("Удаление заказа с ошибкой")
    void deleteOrderByIdWithException() throws SQLException {
        User user = new User(1, "A", "B", "C", "D");
        Product product = new Product(1, "A", "B", "C", 100500);
        Order order = new Order(1, "Тип заказа", user, Collections.singletonList(product));

        when(orderDao.getById(1)).thenReturn(order);
        when(orderDao.delete(order)).thenThrow(new SQLException("test"));
        assertFalse(orderService.deleteOrderById(1));
    }
}