package part6.lesson22.pojo;

import java.util.Objects;

/**
 * Объект продукта
 */
public class Product extends Entity {

    /** тип продукта */
    private String type;

    /** производитель продукта */
    private String manufacturer;

    /** название продукта */
    private String name;

    /** стоимость продукта */
    private Integer price;

    /**
     * Класс для создания продукта
     * @param type - тип продукта
     * @param manufacturer - производитель
     * @param name - название
     * @param price - стоимость
     */
    public Product(String type, String manufacturer, String name, Integer price) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    /**
     * Класс для получения объекта из таблицы
     * @param id - id объекта
     * @param type - тип продукта
     * @param manufacturer - производитель
     * @param name - название
     * @param price -  стоимость
     */
    public Product(Integer id, String type, String manufacturer, String name, Integer price) {
        this.id = id;
        this.type = type;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    /**
     * Получить тип продукта
     * @return тип продукта
     */
    public String getType() {
        return type;
    }

    /**
     * Указать тип продукта
     * @param type - тип продукта
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Получить производителя
     * @return производитель
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Указать производителя
     * @param manufacturer - производитель
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Получить название продукта
     * @return название продукта
     */
    public String getName() {
        return name;
    }

    /**
     * Указать название продукта
     * @param name - название
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получение стоимости продукта
     * @return стоимость
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Указать стоимость продукта
     * @param price - стоимость продукта
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(type, product.type) &&
                Objects.equals(manufacturer, product.manufacturer) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, manufacturer, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
