package part4.lesson19.dao.impl;

import part4.lesson19.dao.GeneralDao;
import part4.lesson19.dao.methods.DeleteMethod;
import part4.lesson19.dbUtils.ConnectionDatabase;
import part4.lesson19.pojo.Entity;
import part4.lesson19.pojo.Order;
import part4.lesson19.pojo.Product;
import part4.lesson19.pojo.User;

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
public class OrderDao extends DeleteMethod implements GeneralDao {

    /** Подключение к бд */
    private static final Connection connection = ConnectionDatabase.getInstance();

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

    /** Класс-CRUD для объекта продукт */
    private static final ProductDao productDao = new ProductDao();

    /** Класс-CRUD для объекта пользователь */
    private static final UserDao userDao = new UserDao();

    @Override
    public List<Entity> getAll() throws SQLException {

        List<Entity> list = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Integer orderId = resultSet.getInt(1);
                List<Product> listProduct = getArrayProducts(orderId);
                User user = (User) userDao.getById(resultSet.getInt(3));

                list.add(new Order(orderId, resultSet.getString(2), user, listProduct));
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Entity getById(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    Integer orderId = resultSet.getInt(1);
                    List<Product> listProduct = getArrayProducts(orderId);
                    User user = (User) userDao.getById(resultSet.getInt(3));

                    return new Order(orderId, resultSet.getString(2), user, listProduct);
                }
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(Entity entity) throws SQLException {
        if (!(entity instanceof Order)) {
            return false;
        }

        Order order = (Order) entity;
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, order.getStatus());
            statement.setInt(2, order.getUser().getId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys();
                 PreparedStatement statementProducts = connection.prepareStatement(
                         INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS)) {

                if (generatedKeys.next()) {

                    Integer orderId = generatedKeys.getInt(1);
                    for (Product product : order.getProducts()) {
                        statementProducts.setInt(1, orderId);
                        statementProducts.setInt(2, product.getId());
                        statementProducts.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Entity entity) throws SQLException {
        if (!(entity instanceof Order)) {
            return false;
        }

        Order order = (Order) entity;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, order.getStatus());
            statement.setInt(2, order.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Entity entity) throws SQLException {
        return delete(entity, DELETE);
    }

    /**
     *
     * @param orderId
     * @return
     * @throws SQLException
     */
    private List<Product> getArrayProducts(Integer orderId) throws SQLException {
        List<Product> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTS)) {

            statement.setLong(1, orderId);
            try (ResultSet resultProductsSet = statement.executeQuery()) {

                while (resultProductsSet.next()) {
                    Product product = (Product) productDao.getById(resultProductsSet.getInt(2));
                    list.add(product);
                }
            }
        }
        return list;
    }
}
