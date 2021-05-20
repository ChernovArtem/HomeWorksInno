package part7.lesson23.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.dao.impl.ProductDao;
import part6.lesson22.dbUtils.ConnectionDatabase;
import part6.lesson22.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static part6.lesson22.dao.impl.ProductDao.*;

@ExtendWith(MockitoExtension.class)
class ProductDaoTest {

    @Mock
    private ConnectionDatabase connectionDatabase;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    ResultSet resultSet;

    @InjectMocks
    private ProductDao productDao = new ProductDao();

    @BeforeEach
    void setUp() {
        when(connectionDatabase.getConnection()).thenReturn(connection);
    }

    @Test
    @DisplayName("Получение всех продуктов без ошибки")
    void getAllOK() throws SQLException {
        when(connection.prepareStatement(SELECT_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("A");
        when(resultSet.getString(3)).thenReturn("B");
        when(resultSet.getString(4)).thenReturn("C");
        when(resultSet.getInt(5)).thenReturn(100500);

        List<Product> products = new ArrayList<>();
        Product product = new Product(1, "A", "B", "C", 100500);
        products.add(product);

        assertEquals(products, productDao.getAll());
    }

    @Test
    @DisplayName("Получение всех продуктов с ошибкой")
    void getAllWithException() throws SQLException {
        when(connection.prepareStatement(SELECT_ALL)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("test"));

        assertEquals(new ArrayList<>(), productDao.getAll());
    }

    @Test
    @DisplayName("Получение продукта по id без ошибки")
    void getByIdOK() throws SQLException {
        when(connection.prepareStatement(SELECT_BY_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("A");
        when(resultSet.getString(3)).thenReturn("B");
        when(resultSet.getString(4)).thenReturn("C");
        when(resultSet.getInt(5)).thenReturn(100500);

        Product product = new Product(1, "A", "B", "C", 100500);
        assertEquals(product, productDao.getById(1));
    }

    @Test
    @DisplayName("Получение продукта по id с ошибкой")
    void getByIdWithException() throws SQLException {
        when(connection.prepareStatement(SELECT_BY_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenThrow(new SQLException("test"));

        assertNull(productDao.getById(1));
    }

    @Test
    @DisplayName("Добавление продукта без ошибки")
    void addOK() throws SQLException {
        when(connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);

        Product product = new Product(1, "A", "B", "C", 100500);
        assertTrue(productDao.add(product));
    }

    @Test
    @DisplayName("Добавление продукта с ошибкой")
    void addWithException() throws SQLException {
        when(connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        Product product = new Product(1, "A", "B", "C", 100500);
        assertFalse(productDao.add(product));
    }

    @Test
    @DisplayName("Обновление продукта без ошибки")
    void updateOK() throws SQLException {
        when(connection.prepareStatement(UPDATE)).thenReturn(statement);

        Product product = new Product(1, "A", "B", "C", 100500);
        assertTrue(productDao.update(product));
    }

    @Test
    @DisplayName("Обновление продукта с ошибкой")
    void updateWithException() throws SQLException {
        when(connection.prepareStatement(UPDATE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        Product product = new Product(1, "A", "B", "C", 100500);
        assertFalse(productDao.update(product));
    }

    @Test
    @DisplayName("Удаление продукта без ошибки")
    void deleteOK() throws SQLException {
        when(connection.prepareStatement(DELETE)).thenReturn(statement);

        Product product = new Product(1, "A", "B", "C", 100500);
        assertTrue(productDao.delete(product));
    }

    @Test
    @DisplayName("Удаление продукта с ошибкой")
    void deleteWithException() throws SQLException {
        when(connection.prepareStatement(DELETE)).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException("test"));

        Product product = new Product(1, "A", "B", "C", 100500);
        assertFalse(productDao.delete(product));
    }
}