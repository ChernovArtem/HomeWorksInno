package part4.lesson19.dao.impl;

import part4.lesson19.dao.GeneralDao;
import part4.lesson19.dao.methods.DeleteMethod;
import part4.lesson19.dbUtils.ConnectionDatabase;
import part4.lesson19.pojo.Entity;
import part4.lesson19.pojo.Product;

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
public class ProductDao extends DeleteMethod implements GeneralDao {

    /** Подключение к бд */
    private static final Connection connection = ConnectionDatabase.getInstance();

    /** Получение всех элементов из таблицы */
    private static final String SELECT_ALL = "SELECT * FROM products";

    /** Получение элемента по id */
    private static final String SELECT_BY_ID = "SELECT * FROM products WHERE id = ?";

    /** Добавление элемента в таблицу */
    private static final String INSERT = "INSERT INTO products values (DEFAULT, ?, ?, ?, ?)";

    /** Обновление элемента в таблице */
    private static final String UPDATE = "UPDATE products SET type = ?, manufacturer = ?, name = ?, price = ? WHERE id = ?";

    /** Удаление элемемнта из таблицы */
    private static final String DELETE = "DELETE FROM products WHERE id = ?";

    @Override
    public List<Entity> getAll() throws SQLException {

        List<Entity> list = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    list.add(new Product(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)));
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
                    return new Product(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5));
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
        if (!(entity instanceof Product)) {
            return false;
        }

        Product product = (Product) entity;
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getType());
            statement.setString(2, product.getManufacturer());
            statement.setString(3, product.getName());
            statement.setInt(4, product.getPrice());
            statement.executeUpdate();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Entity entity) throws SQLException {
        if (!(entity instanceof Product)) {
            return false;
        }

        Product product = (Product) entity;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, product.getType());
            statement.setString(2, product.getManufacturer());
            statement.setString(3, product.getName());
            statement.setInt(4, product.getPrice());
            statement.setInt(5, product.getId());
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
}
