package part6.lesson22.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.dao.methods.DeleteMethod;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-CRUD для объекта продукт
 */
public class ProductDao extends DeleteMethod implements GeneralDao<Product> {

    private Logger log = LoggerFactory.getLogger(ProductDao.class);

    /** Получение всех элементов из таблицы */
    public static final String SELECT_ALL = "SELECT * FROM products";

    /** Получение элемента по id */
    public static final String SELECT_BY_ID = "SELECT * FROM products WHERE id = ?";

    /** Добавление элемента в таблицу */
    public static final String INSERT = "INSERT INTO products values (DEFAULT, ?, ?, ?, ?)";

    /** Обновление элемента в таблице */
    public static final String UPDATE = "UPDATE products SET type = ?, manufacturer = ?, name = ?, price = ? WHERE id = ?";

    /** Удаление элемемнта из таблицы */
    public static final String DELETE = "DELETE FROM products WHERE id = ?";

    private ConnectionDatabase connectionDatabase = new ConnectionDatabase();

    @Override
    public List<Product> getAll() throws SQLException {
        log.debug("Method getAll()");

        List<Product> list = new ArrayList();
        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
                 ResultSet resultSet = statement.executeQuery()) {
                log.debug("select all SQL: {}", SELECT_ALL);

                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5));
                    log.debug("get product: {}", product);
                    list.add(product);
                }

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method getAll()", e);
            }
        }
        return list;
    }

    @Override
    public Product getById(Integer id) throws SQLException {
        log.debug("Method getById({})", id);

        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    log.debug("select SQL: {}", statement);

                    if (resultSet.next()) {
                        Product product = new Product(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5));
                        log.debug("get product: {}", product);
                        return product;
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
    public boolean add(Product product) throws SQLException {
        log.debug("Method add({})", product);

        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, product.getType());
                statement.setString(2, product.getManufacturer());
                statement.setString(3, product.getName());
                statement.setInt(4, product.getPrice());

                log.debug("insert SQL: {}", statement);
                statement.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method add()", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        log.debug("Method update({})", product);

        try (Connection connection = connectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

                statement.setString(1, product.getType());
                statement.setString(2, product.getManufacturer());
                statement.setString(3, product.getName());
                statement.setInt(4, product.getPrice());
                statement.setInt(5, product.getId());

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
    public boolean delete(Product product) throws SQLException {
        log.debug("Method delete({})", product);
        return delete(product, DELETE);
    }
}
