package part6.lesson22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part6.lesson22.dbUtils.InitializationDatabase;
import part6.lesson22.service.OrderService;
import part6.lesson22.service.ProductService;
import part6.lesson22.service.UserService;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Если конфигурация log4j2 не подцепляется автоматически:
     * -Dlog4j.configurationFile=src/main/resources/log4j2.xml
     */
    public static void main(String[] args) {

        //инициализация бд
        InitializationDatabase initDatabase = new InitializationDatabase();
        initDatabase.initTables();

        log.debug("===========================");

        //работа с продуктом
        ProductService productService = new ProductService();

        log.debug("Products old:");
        productService.getAllProducts()
                .forEach(product -> log.debug(product.toString()));

        productService.addProduct("Телефон", "Samsung", "A5", 12000);
        productService.updateNameProductById(8, "YOGA");
        productService.deleteProductById(3);

        log.debug("===============");
        log.debug("Products new:");
        productService.getAllProducts()
                .forEach(product -> log.debug(product.toString()));
        log.debug("===========================");

        //работа с пользователем
        UserService userService = new UserService();

        log.debug("Users old:");
        userService.getAllUsers()
                .forEach(user -> log.debug(user.toString()));

        userService.addUser("Аркадьев Аркадий Аркадьевич", "Казань",
                "arkad@mail.ru", "89991234567");
        userService.updateAddressAndTelephoneUserById(4, "Иркутск", "89993337788");
        userService.deleteUserById(2);

        log.debug("===============");
        log.debug("Users new:");
        userService.getAllUsers()
                .forEach(user -> log.debug(user.toString()));
        log.debug("===========================");

        //работа с заказами
        OrderService orderService = new OrderService();

        log.debug("Orders old:");
        orderService.getAllOrders()
                .forEach(order -> log.debug(order.toString()));

        orderService.addOrder("Новый заказ", userService.getUserById(5),
                productService.getProductByIds(1, 6, 8));
        orderService.updateStatusOrderById(3, "Доставлен");
        orderService.deleteOrderById(1);

        log.debug("===============");
        log.debug("Orders new:");
        orderService.getAllOrders()
                .forEach(order -> log.debug(order.toString()));
        log.debug("===========================");
    }
}
