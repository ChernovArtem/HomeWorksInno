package part6.lesson22.pojo;

import java.util.Objects;

/**
 * Объект пользователя
 */
public class User extends Entity {

    /** ФИО пользователя */
    private String fio;

    /** адрес */
    private String address;

    /** эл. почта */
    private String email;

    /** телефон */
    private String telephone;

    /**
     * Констрктор для создания пользователя
     * @param fio - фио
     * @param address - адрес
     * @param email - эл. почта
     * @param telephone - телефон
     */
    public User(String fio, String address, String email, String telephone) {
        this.fio = fio;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
    }

    /**
     * Конструктор для получения пользователя из таблицы
     * @param id - id объекта
     * @param fio - фио
     * @param address - адрес
     * @param email - эл. почта
     * @param telephone - телефон
     */
    public User(Integer id, String fio, String address, String email, String telephone) {
        this.id = id;
        this.fio = fio;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
    }

    /**
     * Получить ФИО
     * @return ФИО
     */
    public String getFio() {
        return fio;
    }

    /**
     * Указать ФИО
     * @param fio - ФИО
     */
    public void setFio(String fio) {
        this.fio = fio;
    }

    /**
     * Получить адрес
     * @return адрес
     */
    public String getAddress() {
        return address;
    }

    /**
     * Указать адрес
     * @param address - адрес
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Получить эл. почту
     * @return эл. почта
     */
    public String getEmail() {
        return email;
    }

    /**
     * Указать эл. почту
     * @param email - эл. почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Получить телефон
     * @return телефон
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Указать телефон
     * @param telephone - телефон
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(fio, user.fio) &&
                Objects.equals(address, user.address) &&
                Objects.equals(email, user.email) &&
                Objects.equals(telephone, user.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, address, email, telephone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
