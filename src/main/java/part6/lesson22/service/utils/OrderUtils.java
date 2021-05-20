package part6.lesson22.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.pojo.Product;
import part6.lesson22.pojo.User;
import part6.lesson22.service.ProductService;
import part6.lesson22.service.UserService;

/**
 * Сервис-класс для помощи в получении продуктов и пользователя по их id для заказа в слое dao
 */
public class OrderUtils {

    private Logger log = LoggerFactory.getLogger(OrderUtils.class);

    private ProductService productService = new ProductService();

    private UserService userService = new UserService();

    /**
     * Получение продукта по id
     * @param id - идентификатор продукта
     * @return продукт
     */
    public Product getProductById(Integer id) {
        log.debug("Method getProductById({})", id);
        return productService.getProductById(id);
    }

    /**
     * Получение пользователя по id
     * @param id - идентификатор пользователя
     * @return пользователь
     */
    public User getUserById(Integer id) {
        log.debug("Method getUserById({})", id);
        return userService.getUserById(id);
    }
}
