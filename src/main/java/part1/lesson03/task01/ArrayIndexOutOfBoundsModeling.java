package part1.lesson03.task01;

/**
 * Моделирование ошибки ArrayIndexOutOfBoundsException
 */
public class ArrayIndexOutOfBoundsModeling {

    public static void main(String[] args) {
        try {
            System.out.println("Hello, World!");

            int[] array = {1, 2, 3, 4};
            for (int i = 0; i < 5; i++) {
                System.out.println(array[i]);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
