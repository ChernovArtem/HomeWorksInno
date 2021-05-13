package part4.lesson19.service.utils;

import part4.lesson19.pojo.Product;
import part4.lesson19.pojo.User;
import part4.lesson19.service.ProductService;
import part4.lesson19.service.UserService;

/**
 * Сервис-класс для помощи в получении продуктов и пользователя по их id для заказа в слое dao
 */
public class OrderUtils {

    /**
     * Получение продукта по id
     * @param id - идентификатор продукта
     * @return продукт
     */
    public Product getProductById(Integer id) {
        ProductService productService = new ProductService();
        return productService.getProductById(id);
    }

    /**
     * Получение пользователя по id
     * @param id - идентификатор пользователя
     * @return пользователь
     */
    public User getUserById(Integer id) {
        UserService userService = new UserService();
        return userService.getUserById(id);
    }
}
