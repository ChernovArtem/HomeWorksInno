package part4.lesson19.service;

import part4.lesson19.dao.GeneralDao;
import part4.lesson19.dao.impl.ProductDao;
import part4.lesson19.pojo.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сервис по работе с продуктами
 */
public class ProductService {

    /** Слой dao для работы с продуктами */
    private GeneralDao<Product> productDao = new ProductDao();

    /**
     * Получить все продукты
     * @return все продукты
     */
    public List<Product> getAllProducts() {
        try {
            return productDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
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
            List<Product> products = new ArrayList<>();
            for (Integer id : ids) {
                products.add(productDao.getById(id));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
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
            return productDao.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
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
            Product product = new Product(type, manufacturer, name, price);
            productDao.add(product);
        } catch (SQLException e) {
            e.printStackTrace();
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
            Product product = productDao.getById(id);
            product.setName(name);
            productDao.update(product);
        } catch (SQLException e) {
            e.printStackTrace();
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
            Product product = productDao.getById(id);
            productDao.delete(product);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
