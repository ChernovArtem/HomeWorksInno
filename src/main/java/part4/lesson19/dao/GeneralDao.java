package part4.lesson19.dao;

import part4.lesson19.pojo.Entity;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс фасад, CRUD для объектов таблицы
 */
public interface GeneralDao<T extends Entity> {

    /**
     * Получение всех объектов из таблицы
     * @return список всех объектов
     * @throws SQLException ошибка связанная с SQL
     */
    List<T> getAll() throws SQLException;

    /**
     * Получение объекта по id
     * @param id - id объекта, который нужно получить
     * @return объект из таблицы
     * @throws SQLException ошибка связанная с SQL
     */
    T getById(Integer id) throws SQLException;

    /**
     * Добавление объекта в таблицу
     * @param entity - объект, который нужно добавить
     * @return true - если объект добавлен и не произошло ошибок
     * @throws SQLException ошибка связанная с SQL
     */
    boolean add(T entity) throws SQLException;

    /**
     * Обновление объекта в таблице
     * @param entity - объект, который нужно обновить в таблице
     * @return true - если объект обновлен и не произошло ошибок
     * @throws SQLException ошибка связанная с SQL
     */
    boolean update(T entity) throws SQLException;

    /**
     * Удаление объекта из таблицы
     * @param entity - объект, который нужно удалить
     * @return true - если объект добавлен и не произошло ошибок
     * @throws SQLException ошибка связанная с SQL
     */
    boolean delete(T entity) throws SQLException;
}
