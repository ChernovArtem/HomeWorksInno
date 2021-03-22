package part1.lesson08;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс для хранения значений факториала по числам.
 * Нужен для того, чтобы по несколько раз не пересчитывать факториал по одному и тому же числу
 */
public class FactorialResultMap {

    /** Словарь для хранения результата факториала */
    private final Map<Integer, BigInteger> map = new ConcurrentHashMap<>();

    /**
     * Проверка значения числа в словаре
     * @param key - число для проверки
     * @return {@code true} если число присутствует в словаре
     */
    public boolean containsKey(Integer key) {
        return map.containsKey(key);
    }

    /**
     * Получение результата из справочника
     * @param key - число, по которому получаем результат факториала
     * @return значение факториала
     */
    public BigInteger getResult(Integer key) {
        return map.get(key);
    }

    /**
     * Запись результата факториала по числу
     * @param key - число, по кторому был расчитан факториал
     * @param value - результат факториала
     */
    public void setResult(Integer key, BigInteger value) {
        map.put(key, value);
    }
}
