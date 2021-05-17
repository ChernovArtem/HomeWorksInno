package part6.lesson22.dao.methods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.Entity;
import part6.lesson22.pojo.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс с методом на удаление
 * Паттерн "Шаблонный метод"
 */
public class DeleteMethod {

    private final static Logger log = LoggerFactory.getLogger(DeleteMethod.class);

    /**
     * Удалить объект из бд
     * @param entity - сущность, которую нужно удалить
     * @param sql - запрос удаления
     * @return true - если объект добавлен и не произошло ошибок
     * @throws SQLException ошибка связанная с SQL
     */
     protected boolean delete(Entity entity, String sql) throws SQLException {

         log.debug("Method delete({})", entity);

         try (Connection connection = ConnectionDatabase.getConnection()) {
             try (PreparedStatement statement = connection.prepareStatement(sql)) {

                 statement.setLong(1, entity.getId());
                 if (entity instanceof Order) {
                     statement.setLong(2, entity.getId());
                 }

                 log.debug("delete SQL: {}", statement);
                 statement.executeUpdate();
                 connection.commit();

             } catch (SQLException e) {
                 connection.rollback();
                 log.error("SQLException in method delete()", e);
                 return false;
             }
         }
         return true;
    }
}
