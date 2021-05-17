package part6.lesson22.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.dao.methods.DeleteMethod;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-CRUD для объекта пользователь
 */
public class UserDao extends DeleteMethod implements GeneralDao<User> {

    private Logger log = LoggerFactory.getLogger(UserDao.class);

    /** Получение всех элементов из таблицы */
    private static final String SELECT_ALL = "SELECT * FROM users";

    /** Получение элемента по id */
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";

    /** Добавление элемента в таблицу */
    private static final String INSERT = "INSERT INTO users values (DEFAULT, ?, ?, ?, ?)";

    /** Обновление элемента в таблице */
    private static final String UPDATE = "UPDATE users SET fio = ?, address = ?, email = ?, telephone = ? WHERE id = ?";

    /** Удаление элемемнта из таблицы */
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    @Override
    public List<User> getAll() throws SQLException {
        log.debug("Method getAll()");

        List<User> list = new ArrayList();
        try (Connection connection = ConnectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
                 ResultSet resultSet = statement.executeQuery()) {
                log.debug("select all SQL: {}", SELECT_ALL);

                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5));
                    log.debug("get user: {}", user);
                    list.add(user);
                }

            } catch (SQLException e) {
                connection.rollback();
                log.error("SQLException in method getAll()", e);
            }
        }
        return list;
    }

    @Override
    public User getById(Integer id) throws SQLException {

        log.debug("Method getById({})", id);

        try (Connection connection = ConnectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    log.debug("select SQL: {}", statement);

                    if (resultSet.next()) {
                        User user = new User(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5));
                        log.debug("get user: {}", user);
                        return user;
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
    public boolean add(User user) throws SQLException {
        log.debug("Method add({})", user);

        try (Connection connection = ConnectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, user.getFio());
                statement.setString(2, user.getAddress());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getTelephone());

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
    public boolean update(User user) throws SQLException {
        log.debug("Method update({})", user);

        try (Connection connection = ConnectionDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

                statement.setString(1, user.getFio());
                statement.setString(2, user.getAddress());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getTelephone());
                statement.setInt(5, user.getId());

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
    public boolean delete(User user) throws SQLException {
        log.debug("Method delete({})", user);
        return delete(user, DELETE);
    }
}
