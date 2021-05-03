package part4.lesson19.dao.methods;

import part4.lesson19.dbUtils.ConnectionDatabase;
import part4.lesson19.pojo.Entity;
import part4.lesson19.pojo.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс с методом на удаление
 * Паттерн "Шаблонный метод"
 */
public class DeleteMethod {

    /**
     *
     * @param entity
     * @param sql
     * @return true - если объект добавлен и не произошло ошибок
     * @throws SQLException ошибка связанная с SQL при roolback
     */
     protected boolean delete(Entity entity, String sql) throws SQLException {
        try (PreparedStatement statement = ConnectionDatabase.getInstance().prepareStatement(sql)) {

            statement.setLong(1, entity.getId());
            if (entity instanceof Order) {
                statement.setLong(2, entity.getId());
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            ConnectionDatabase.getInstance().rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
