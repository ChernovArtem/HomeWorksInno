package part6.lesson22.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.dao.impl.UserDao;
import part6.lesson22.pojo.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с пользователями
 */
public class UserService {

    private Logger log = LoggerFactory.getLogger(UserService.class);

    /** Слой dao для работы с пользователями */
    private GeneralDao<User> userDao = new UserDao();

    /**
     * Получить всех пользователей
     * @return все пользователи
     */
    public List<User> getAllUsers() {
        try {
            log.debug("Method getAllUsers()");
            return userDao.getAll();
        } catch (SQLException e) {
            log.error("SQLException in method getAllUsers()", e);
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
            log.debug("Method getUserById({})", id);
            return userDao.getById(id);
        } catch (SQLException e) {
            log.error("SQLException in method getUserById()", e);
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
            log.debug("Method addUser({}, {}, {}, {})", fio, address, email, telephone);
            User user = new User(fio,address, email, telephone);
            userDao.add(user);
        } catch (SQLException e) {
            log.error("SQLException in method addUser()", e);
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
            log.debug("Method updateAddressAndTelephoneUserById({}, {}, {})", id, address, telephone);
            User user = userDao.getById(id);
            user.setAddress(address);
            user.setTelephone(telephone);
            userDao.update(user);
        } catch (SQLException e) {
            log.error("SQLException in method updateAddressAndTelephoneUserById()", e);
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
            log.debug("Method deleteUserById({})", id);
            User user = userDao.getById(id);
            userDao.delete(user);
        } catch (SQLException e) {
            log.error("SQLException in method deleteUserById()", e);
            return false;
        }
        return true;
    }
}
