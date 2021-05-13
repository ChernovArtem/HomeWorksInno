package part4.lesson19;

import part4.lesson19.dbUtils.InitializationDatabase;
import part4.lesson19.service.OrderService;
import part4.lesson19.service.ProductService;
import part4.lesson19.service.UserService;

public class Main {

    public static void main(String[] args) {

        //инициализация бд
        InitializationDatabase initDatabase = new InitializationDatabase();
        initDatabase.initTables();

        System.out.println("===========================");

        //работа с продуктом
        ProductService productService = new ProductService();
        productService.getAllProducts()
                .forEach(product -> System.out.println(product));
        productService.addProduct("Телефон", "Samsung", "A5", 12000);
        productService.updateNameProductById(8, "YOGA");
        productService.deleteProductById(3);

        System.out.println("===========================");

        //работа с пользователем
        UserService userService = new UserService();
        userService.getAllUsers()
                .forEach(user -> System.out.println(user));
        userService.addUser("Аркадьев Аркадий Аркадьевич", "Казань",
                "arkad@mail.ru", "89991234567");
        userService.updateAddressAndTelephoneUserById(4, "Иркутск", "89993337788");
        userService.deleteUserById(2);

        System.out.println("===========================");

        //работа с заказами
        OrderService orderService = new OrderService();
        orderService.getAllOrders()
                .forEach(order -> System.out.println(order));
        orderService.addOrder("Новый заказ", userService.getUserById(5),
                productService.getProductByIds(1, 6, 8));
        orderService.updateStatusOrderById(3, "Доставлен");
        orderService.deleteOrderById(1);

        System.out.println("===========================");
    }
}
