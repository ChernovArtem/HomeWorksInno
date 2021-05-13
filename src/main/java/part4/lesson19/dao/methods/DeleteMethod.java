package part4.lesson19.dao.methods;

import part4.lesson19.dbUtils.ConnectionDatabase;
import part4.lesson19.pojo.Entity;
import part4.lesson19.pojo.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс с методом на удаление
 * Паттерн "Шаблонный метод"
 */
public class DeleteMethod {

    /**
     * Удалить объект из бд
     * @param entity - сущность, которую нужно удалить
     * @param sql - запрос удаления
     * @return true - если объект добавлен и не произошло ошибок
     * @throws SQLException ошибка связанная с SQL
     */
     protected boolean delete(Entity entity, String sql) throws SQLException {
         try (Connection connection = ConnectionDatabase.getInstance()) {
             try (PreparedStatement statement = connection.prepareStatement(sql)) {

                 statement.setLong(1, entity.getId());
                 if (entity instanceof Order) {
                     statement.setLong(2, entity.getId());
                 }
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
}
