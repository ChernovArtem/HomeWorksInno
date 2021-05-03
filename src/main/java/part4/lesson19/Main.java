package part4.lesson19;

import part4.lesson19.dao.impl.OrderDao;
import part4.lesson19.dao.impl.ProductDao;
import part4.lesson19.dao.impl.UserDao;
import part4.lesson19.dbUtils.ConnectionDatabase;
import part4.lesson19.dbUtils.InitializationDatabase;
import part4.lesson19.pojo.Entity;
import part4.lesson19.pojo.Order;
import part4.lesson19.pojo.Product;
import part4.lesson19.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        InitializationDatabase.initTables();
        Connection connection = ConnectionDatabase.getInstance();

        connection.setAutoCommit(false);

        Savepoint savepoint = connection.setSavepoint();
        allMethodWithProducts(connection, savepoint);

        savepoint = connection.setSavepoint();
        allMethodWithUsers(connection, savepoint);

        savepoint = connection.setSavepoint();
        allMethodWithOrders(connection, savepoint);
    }

    private static void allMethodWithProducts(Connection connection,  Savepoint savepoint) throws SQLException {
        System.out.println("===========================");
        System.out.println("Products:");
        try {
            ProductDao productDao = new ProductDao();

            //вывод всей информации
            List<Entity> products = productDao.getAll();
            products.forEach(elem -> System.out.println(elem));

            //добавление
            Product product = new Product("Телефон","Samsung", "A5", 12000);
            productDao.add(product);

            //обновление
            product = (Product) productDao.getById(8);
            product.setName("YOGA");
            productDao.update(product);

            //удаление
            product = (Product) productDao.getById(3);
            productDao.delete(product);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepoint);
            e.printStackTrace();
        }
        System.out.println("===========================");
    }

    private static void allMethodWithUsers(Connection connection,  Savepoint savepoint) throws SQLException {
        System.out.println("===========================");
        System.out.println("Users:");
        try {
            UserDao userDao = new UserDao();

            //вывод всей информации
            List<Entity> users = userDao.getAll();
            users.forEach(elem -> System.out.println(elem));

            //добаление
            User user = new User("Аркадьев Аркадий Аркадьевич","Казань", "arkad@mail.ru", "89991234567");
            userDao.add(user);

            //обновление
            user = (User) userDao.getById(4);
            user.setAddress("Иркутск");
            user.setTelephone("89993337788");
            userDao.update(user);

            //удаление
            user = (User) userDao.getById(2);
            userDao.delete(user);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepoint);
            e.printStackTrace();
        }
        System.out.println("===========================");
    }

    private static void allMethodWithOrders(Connection connection,  Savepoint savepoint) throws SQLException {
        System.out.println("===========================");
        System.out.println("Orders:");
        try {
            ProductDao productDao = new ProductDao();
            UserDao userDao = new UserDao();
            OrderDao orderDao = new OrderDao();

            //вывод всей информации
            List<Entity> orders = orderDao.getAll();
            orders.forEach(elem -> System.out.println(elem));

            //добаление
            User user = (User) userDao.getById(5);

            List<Product> products = new ArrayList<>();
            products.add((Product) productDao.getById(1));
            products.add((Product) productDao.getById(6));
            products.add((Product) productDao.getById(8));

            Order order = new Order("Новый заказ", user, products);
            orderDao.add(order);

            //обновление
            order = (Order) orderDao.getById(3);
            order.setStatus("Доставлен");
            orderDao.update(order);

            //удаление
            order = (Order) orderDao.getById(1);
            orderDao.delete(order);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback(savepoint);
            e.printStackTrace();
        }
        System.out.println("===========================");
    }


}
