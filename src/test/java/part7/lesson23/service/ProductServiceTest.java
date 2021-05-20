package part7.lesson23.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dao.GeneralDao;
import part6.lesson22.pojo.Product;
import part6.lesson22.service.ProductService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private GeneralDao<Product> productDao;

    @InjectMocks
    private ProductService productService = new ProductService();

    @Test
    @DisplayName("Получение всех продуктов без ошибки")
    void getAllProductsOK() throws SQLException {
        List<Product> products = new ArrayList<>();
        Product product = new Product(1, "A", "B", "C", 100500);
        products.add(product);

        when(productDao.getAll()).thenReturn(products);
        assertEquals(products, productService.getAllProducts());
    }

    @Test
    @DisplayName("Получение всех продуктов с ошибкой")
    void getAllProductsWithException() throws SQLException {
        when(productDao.getAll()).thenThrow(new SQLException("test"));
        assertEquals(Collections.emptyList(), productService.getAllProducts());
    }

    @Test
    @DisplayName("Получение всех продуктов по id без ошибки")
    void getProductByIdsOK() throws SQLException {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(1, "A", "B", "C", 100500);
        Product product3 = new Product(3, "B", "C", "D", 100503);
        Product product5 = new Product(5, "C", "D", "E", 100505);
        products.add(product1);
        products.add(product3);
        products.add(product5);

        when(productDao.getById(1)).thenReturn(product1);
        when(productDao.getById(3)).thenReturn(product3);
        when(productDao.getById(5)).thenReturn(product5);

        assertEquals(products, productService.getProductByIds(1, 3, 5));
    }

    @Test
    @DisplayName("Получение всех продуктов по id с ошибкой")
    void getProductByIdsWithException() throws SQLException {
        when(productDao.getById(1)).thenThrow(new SQLException("test"));
        assertEquals(Collections.emptyList(), productService.getProductByIds(1, 3, 5));
    }

    @Test
    @DisplayName("Получение продукта по id без ошибки")
    void getProductByIdOK() throws SQLException {
        Product product = new Product(1, "A", "B", "C", 100500);

        when(productDao.getById(1)).thenReturn(product);
        assertEquals(product, productService.getProductById(1));
    }

    @Test
    @DisplayName("Получение продукта по id с ошибкой")
    void getProductByIdWithException() throws SQLException {
        when(productDao.getById(1)).thenThrow(new SQLException("test"));
        assertNull(productService.getProductById(1));
    }

    @Test
    @DisplayName("Добавление продукта без ошибки")
    void addProductOK() throws SQLException {
        assertTrue(productService.addProduct("A", "B", "C", 100500));
    }

    @Test
    @DisplayName("Добавление продукта с ошибкой")
    void addProductWithException() throws SQLException {
        Product product = new Product("A", "B", "C", 100500);
        when(productDao.add(product)).thenThrow(new SQLException("test"));

        assertFalse(productService.addProduct("A", "B", "C", 100500));
    }

    @Test
    @DisplayName("Обновление продукта без ошибки")
    void updateNameProductByIdOK() throws SQLException {
        Product product = new Product(1, "A", "B", "C", 100500);
        when(productDao.getById(1)).thenReturn(product);

        assertTrue(productService.updateNameProductById(1, "C"));
    }

    @Test
    @DisplayName("Обновление продукта с ошибкой")
    void updateNameProductByIdWithException() throws SQLException {
        Product product = new Product(1, "A", "B", "C", 100500);
        when(productDao.getById(1)).thenReturn(product);
        when(productDao.update(product)).thenThrow(new SQLException("test"));

        assertFalse(productService.updateNameProductById(1, "C"));
    }

    @Test
    @DisplayName("Удаление продукта без ошибки")
    void deleteProductByIdOK() throws SQLException {
        Product product = new Product(1, "A", "B", "C", 100500);
        when(productDao.getById(1)).thenReturn(product);

        assertTrue(productService.deleteProductById(1));
    }

    @Test
    @DisplayName("Удаление продукта с ошибкой")
    void deleteProductByIdWithException() throws SQLException {
        Product product = new Product(1, "A", "B", "C", 100500);
        when(productDao.getById(1)).thenReturn(product);
        when(productDao.delete(product)).thenThrow(new SQLException("test"));

        assertFalse(productService.deleteProductById(1));
    }
}