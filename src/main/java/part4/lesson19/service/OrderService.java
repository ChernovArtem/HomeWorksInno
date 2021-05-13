package part4.lesson19.service;

import part4.lesson19.dao.GeneralDao;
import part4.lesson19.dao.impl.OrderDao;
import part4.lesson19.pojo.Order;
import part4.lesson19.pojo.Product;
import part4.lesson19.pojo.User;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с заказами
 */
public class OrderService {

    /** Слой dao для работы с заказами */
    private GeneralDao<Order> orderDao = new OrderDao();

    /**
     * Получение всех заказов
     * @return все заказы
     */
    public List<Order> getAllOrders() {
        try {
            return orderDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
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
            Order order = new Order("Новый заказ", user, products);
            orderDao.add(order);
        } catch (SQLException e) {
            e.printStackTrace();
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
            Order order = orderDao.getById(id);
            order.setStatus(status);
            orderDao.update(order);
        } catch (SQLException e) {
            e.printStackTrace();
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
            Order order = orderDao.getById(id);
            orderDao.delete(order);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
