package part4.lesson19.pojo;

import java.util.List;
import java.util.Objects;

/**
 * Объект заказа
 */
public class Order extends Entity {

    /** статус заказа */
    private String status;

    /** пользователь, совершивший заказ */
    private User user;

    /** продукты в заказе */
    private List<Product> products;

    /**
     * Конструктор, для создания заказа
     * @param status - статус заказа
     * @param user - пользователь
     * @param products - продукты
     */
    public Order(String status, User user, List<Product> products) {
        this.status = status;
        this.user = user;
        this.products = products;
    }

    /**
     * Конструктор, для получение объектка из таблицы
     * @param id - id объекта
     * @param status - статус заказа
     * @param user - пользователь
     * @param products - продукты
     */
    public Order(Integer id, String status, User user, List<Product> products) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.products = products;
    }

    /**
     * Получение статуса заказа
     * @return статус заказа
     */
    public String getStatus() {
        return status;
    }

    /**
     * Указать статус заказа
     * @param status - статус заказа
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Получить пользователя заказа
     * @return пользователь
     */
    public User getUser() {
        return user;
    }

    /**
     * Получить продукты из заказа
     * @return продукты
     */
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(user, order.user) &&
                Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, products);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
