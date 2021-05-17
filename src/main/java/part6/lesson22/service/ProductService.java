package part6.lesson22.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.dao.impl.ProductDao;
import part6.lesson22.pojo.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с продуктами
 */
public class ProductService {

    private Logger log = LoggerFactory.getLogger(ProductService.class);

    /** Слой dao для работы с продуктами */
    private GeneralDao<Product> productDao = new ProductDao();

    /**
     * Получить все продукты
     * @return все продукты
     */
    public List<Product> getAllProducts() {
        try {
            log.debug("Method getAllProducts()");
            return productDao.getAll();
        } catch (SQLException e) {
            log.error("SQLException in method getAllProducts()", e);
        }
        return Collections.emptyList();
    }

    /**
     * Получить все продукты по id
     * @param ids - id продуктов, которые нужно получить
     * @return продукты
     */
    public List<Product> getProductByIds(Integer... ids) {
        try {
            log.debug("Method getProductByIds({})", ids);
            List<Product> products = new ArrayList<>();
            for (Integer id : ids) {
                products.add(productDao.getById(id));
            }
            return products;
        } catch (SQLException e) {
            log.error("SQLException in method getProductByIds()", e);
        }
        return Collections.emptyList();
    }

    /**
     * Получить продукт по id
     * @param id - id продукта
     * @return продукт
     */
    public Product getProductById(Integer id) {
        try {
            log.debug("Method getProductById({})", id);
            return productDao.getById(id);
        } catch (SQLException e) {
            log.error("SQLException in method getProductById()", e);
        }
        return null;
    }

    /**
     * Добавить продукт
     * @param type - тип продукта
     * @param manufacturer - производитель
     * @param name - название
     * @param price - стоимость
     * @return true если продукт добавлен и не произошло ошибки
     */
    public boolean addProduct(String type, String manufacturer, String name, Integer price) {
        try {
            log.debug("Method addProduct({}, {}, {}, {})", type, manufacturer, name, price);
            Product product = new Product(type, manufacturer, name, price);
            productDao.add(product);
        } catch (SQLException e) {
            log.error("SQLException in method addProduct()", e);
            return false;
        }
        return true;
    }

    /**
     * Обновление названия продукта по его id
     * @param id - идентификатор продукта
     * @param name - название на которое нужно заменить
     * @return true если название обновилось и не произошло ошибки
     */
    public boolean updateNameProductById(Integer id, String name) {
        try {
            log.debug("Method updateNameProductById({}, {})", id, name);
            Product product = productDao.getById(id);
            product.setName(name);
            productDao.update(product);
        } catch (SQLException e) {
            log.error("SQLException in method updateNameProductById()", e);
            return false;
        }
        return true;
    }

    /**
     * Удаление продукта
     * @param id - идентификатор продукта
     * @return true если продукт удалился и не произошло ошибки
     */
    public boolean deleteProductById(Integer id) {
        try {
            log.debug("Method deleteProductById({})", id);
            Product product = productDao.getById(id);
            productDao.delete(product);
        } catch (SQLException e) {
            log.error("SQLException in method deleteProductById()", e);
            return false;
        }
        return true;
    }
}
