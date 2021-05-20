package part6.lesson22.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.dao.methods.DeleteMethod;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.Order;
import part6.lesson22.pojo.Product;
import part6.lesson22.pojo.User;
import part6.lesson22.service.utils.OrderUtils;

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

    private Logger log = LoggerFactory.getLogger(OrderDao.class);

    /** Получение всех элементов из таблицы */
    public static final String SELECT_ALL = "SELECT * FROM orders";

    /** Получение элемента по id */
    public static final String SELECT_BY_ID = "SELECT * FROM orders WHERE id = ?";

    /** Получение всех элементов из таблицы по id */
    public static final String SELECT_PRODUCTS = "SELECT * FROM orders_products WHERE order_id = ?";

    /** Добавление элемента в таблицу */
    public static final String INSERT = "INSERT INTO orders values (DEFAULT, ?, ?)";

    /** Добавление элемента в таблицу */
    public static final String INSERT_PRODUCTS = "INSERT INTO orders_products values (?, ?)";

    /** Обновление элемента в таблице */
    public static final String UPDATE = "UPDATE orders SET status = ? WHERE id = ?";

    /** Удаление элемемнта из таблицы */
    public static final String DELETE = "DELETE FROM orders_products WHERE order_id = ?;\n"
            + "DELETE FROM orders WHERE id = ?";

    private ConnectionDatabase connectionDatabase = new ConnectionDatabase();

    /** Сервис для помомощи в получении пользователя и продуктов по их id */
    private OrderUtils orderUtils = new OrderUtils();

    @Override
    public List<Order> getAll() throws SQLException {
        log.debug("Method getAll()");

        List<Order> list = new ArrayList();
        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
                 ResultSet resultSet = statement.executeQuery()) {
                log.debug("select all SQL: {}", SELECT_ALL);

                while (resultSet.next()) {
                    Integer orderId = resultSet.getInt(1);
                    List<Product> listProduct = getArrayProducts(connection, orderId);

                    Integer userId = resultSet.getInt(3);
                    User user = orderUtils.getUserById(userId);

                    Order order = new Order(orderId, resultSet.getString(2), user, listProduct);
                    log.debug("get order: {}", order);
                    list.add(order);
                }

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method getAll()", e);
            }
        }
        return list;
    }

    @Override
    public Order getById(Integer id) throws SQLException {
        log.debug("Method getById({})", id);

        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    log.debug("select SQL: {}", statement);

                    if (resultSet.next()) {
                        Integer orderId = resultSet.getInt(1);
                        List<Product> listProduct = getArrayProducts(connection, orderId);

                        Integer userId = resultSet.getInt(3);
                        User user = orderUtils.getUserById(userId);

                        Order order = new Order(orderId, resultSet.getString(2), user, listProduct);
                        log.debug("get order: {}", order);
                        return order;
                    }
                }

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method getById()", e);
            }
        }
        return null;
    }

    @Override
    public boolean add(Order order) throws SQLException {
        log.debug("Method add({})", order);

        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, order.getStatus());
                statement.setInt(2, order.getUser().getId());

                log.debug("insert SQL: {}", statement);
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

                            log.debug("insert SQL: {}", statementProducts);
                            statementProducts.executeUpdate();
                            connection.commit();
                        }
                    }
                }

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method add()", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Order order) throws SQLException {
        log.debug("Method update({})", order);

        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

                statement.setString(1, order.getStatus());
                statement.setInt(2, order.getId());

                log.debug("update SQL: {}", statement);
                statement.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method update()", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Order order) throws SQLException {
        log.debug("Method delete({})", order);
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
        log.debug("Method getArrayProducts({}, {})", connection, orderId);

        List<Product> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS)) {

            statement.setLong(1, orderId);
            try (ResultSet resultProductsSet = statement.executeQuery()) {
                log.debug("select SQL: {}", statement);

                while (resultProductsSet.next()) {
                    Integer productId = resultProductsSet.getInt(2);
                    Product product = orderUtils.getProductById(productId);
                    log.debug("get product: {}", product);
                    list.add(product);
                }
            }
        }
        return list;
    }
}
