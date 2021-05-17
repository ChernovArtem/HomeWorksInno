package part6.lesson22.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.dao.impl.OrderDao;
import part6.lesson22.pojo.Order;
import part6.lesson22.pojo.Product;
import part6.lesson22.pojo.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с заказами
 */
public class OrderService {

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    /** Слой dao для работы с заказами */
    private GeneralDao<Order> orderDao = new OrderDao();

    /**
     * Получение всех заказов
     * @return все заказы
     */
    public List<Order> getAllOrders() {
        try {
            log.debug("Method getAllOrders()");
            return orderDao.getAll();
        } catch (SQLException e) {
            log.error("SQLException in method getAllOrders()", e);
        }
        return Collections.emptyList();
    }

    /**
     * Добавление заказа
     * @param status - статус заказа
     * @param user - пользователь
     * @param products - продукты
     * @return true если заказ добавился и не произошло ошибки
     */
    public boolean addOrder(String status, User user, List<Product> products) {
        try {
            log.debug("Method addOrder({}, {}, {})", status, user, products);
            Order order = new Order(status, user, products);
            orderDao.add(order);
        } catch (SQLException e) {
            log.error("SQLException in method addOrder()", e);
            return false;
        }
        return true;
    }

    /**
     * Обновление статуса заказа по его id
     * @param id - идентификатор заказа
     * @param status - новый статус заказа
     * @return true если заказ обновился и не произошло ошибки
     */
    public boolean updateStatusOrderById(Integer id, String status) {
        try {
            log.debug("Method updateStatusOrderById({}, {})", id, status);
            Order order = orderDao.getById(id);
            order.setStatus(status);
            orderDao.update(order);
        } catch (SQLException e) {
            log.error("SQLException in method updateStatusOrderById()", e);
            return false;
        }
        return true;
    }

    /**
     * Удаление заказа
     * @param id - идентификатор заказа
     * @return true если заказ удалился и не произошло ошибки
     */
    public boolean deleteOrderById(Integer id) {
        try {
            log.debug("Method deleteOrderById({})", id);
            Order order = orderDao.getById(id);
            orderDao.delete(order);
        } catch (SQLException e) {
            log.error("SQLException in method deleteOrderById()", e);
            return false;
        }
        return true;
    }
}
