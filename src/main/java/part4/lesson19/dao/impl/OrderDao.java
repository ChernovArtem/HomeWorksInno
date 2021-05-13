package part4.lesson19.dao.impl;

import part4.lesson19.dao.GeneralDao;
import part4.lesson19.dao.methods.DeleteMethod;
import part4.lesson19.dbUtils.ConnectionDatabase;
import part4.lesson19.pojo.Order;
import part4.lesson19.pojo.Product;
import part4.lesson19.pojo.User;
import part4.lesson19.service.utils.OrderUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-CRUD для объекта заказ
 */
public class OrderDao extends DeleteMethod implements GeneralDao<Order> {

    /** Получение всех элементов из таблицы */
    private static final String SELECT_ALL = "SELECT * FROM orders";

    /** Получение элемента по id */
    private static final String SELECT_BY_ID = "SELECT * FROM orders WHERE id = ?";

    /** Получение всех элементов из таблицы по id */
    private static final String SELECT_PRODUCTS = "SELECT * FROM orders_products WHERE order_id = ?";

    /** Добавление элемента в таблицу */
    private static final String INSERT = "INSERT INTO orders values (DEFAULT, ?, ?)";

    /** Добавление элемента в таблицу */
    private static final String INSERT_PRODUCTS = "INSERT INTO orders_products values (?, ?)";

    /** Обновление элемента в таблице */
    private static final String UPDATE = "UPDATE orders SET status = ? WHERE id = ?";

    /** Удаление элемемнта из таблицы */
    private static final String DELETE = "DELETE FROM orders_products WHERE order_id = ?;\n"
            + "DELETE FROM orders WHERE id = ?";

    /** Сервис для помомощи в получении пользователя и продуктов по их id */
    private OrderUtils orderUtils = new OrderUtils();

    @Override
    public List<Order> getAll() throws SQLException {

        List<Order> list = new ArrayList();
        try (Connection connection = ConnectionDatabase.getInstance()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Integer orderId = resultSet.getInt(1);
                    List<Product> listProduct = getArrayProducts(connection, orderId);

                    Integer userId = resultSet.getInt(3);
                    User user = orderUtils.getUserById(userId);

                    list.add(new Order(orderId, resultSet.getString(2), user, listProduct));
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public Order getById(Integer id) throws SQLException {
        try (Connection connection = ConnectionDatabase.getInstance()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {

                        Integer orderId = resultSet.getInt(1);
                        List<Product> listProduct = getArrayProducts(connection, orderId);

                        Integer userId = resultSet.getInt(3);
                        User user = orderUtils.getUserById(userId);

                        return new Order(orderId, resultSet.getString(2), user, listProduct);
                    }
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean add(Order order) throws SQLException {
        try (Connection connection = ConnectionDatabase.getInstance()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, order.getStatus());
                statement.setInt(2, order.getUser().getId());
                statement.executeUpdate();
                connection.commit();

                try (ResultSet generatedKeys = statement.getGeneratedKeys();
                     PreparedStatement statementProducts = connection.prepareStatement(
                             INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS)) {

                    if (generatedKeys.next()) {
                        Integer orderId = generatedKeys.getInt(1);
                        for (Product product : order.getProducts()) {
                            statementProducts.setInt(1, orderId);
                            statementProducts.setInt(2, product.getId());
                            statementProducts.executeUpdate();
                            connection.commit();
                        }
                    }
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Order order) throws SQLException {
        try (Connection connection = ConnectionDatabase.getInstance()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

                statement.setString(1, order.getStatus());
                statement.setInt(2, order.getId());
                statement.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Order order) throws SQLException {
        return delete(order, DELETE);
    }

    /**
     * Получение всех продуктов заказа
     * @param connection - подключенная бд
     * @param orderId - идентификатор заказа
     * @return список продуктов
     * @throws SQLException ошибка связанная с SQL
     */
    private List<Product> getArrayProducts(Connection connection, Integer orderId) throws SQLException {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS)) {

            statement.setLong(1, orderId);
            try (ResultSet resultProductsSet = statement.executeQuery()) {

                while (resultProductsSet.next()) {
                    Integer productId = resultProductsSet.getInt(2);
                    Product product = orderUtils.getProductById(productId);
                    list.add(product);
                }
            }
        }
        return list;
    }
}
