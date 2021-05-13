package part4.lesson19.service;

import part4.lesson19.dao.GeneralDao;
import part4.lesson19.dao.impl.UserDao;
import part4.lesson19.pojo.User;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с пользователями
 */
public class UserService {

    /** Слой dao для работы с пользователями */
    private GeneralDao<User> userDao = new UserDao();

    /**
     * Получить всех пользователей
     * @return все пользователи
     */
    public List<User> getAllUsers() {
        try {
            return userDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Получить пользователя по id
     * @param id - идентификатор пользователя
     * @return пользователь
     */
    public User getUserById(Integer id) {
        try {
            return userDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Добавление пользователя
     * @param fio - фио пользователя
     * @param address - адрес
     * @param email - эл.почта
     * @param telephone - телефон
     * @return true если пользователь добавился
     */
    public boolean addUser(String fio, String address, String email, String telephone) {
        try {
            User user = new User(fio,address, email, telephone);
            userDao.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Обновление адреса и телефона пользователя по его id
     * @param id - идентификатор пользователя
     * @param address - адрес
     * @param telephone - телефон
     * @return true если пользователь обновился
     */
    public boolean updateAddressAndTelephoneUserById(Integer id, String address, String telephone) {
        try {
            User user = userDao.getById(id);
            user.setAddress(address);
            user.setTelephone(telephone);
            userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Удаление пользователя
     * @param id - идентификатор пользователя
     * @return true если пользователь удалился
     */
    public boolean deleteUserById(Integer id) {
        try {
            User user = userDao.getById(id);
            userDao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
