package part1.lesson03.task01;

/**
 * Вызов своего exception
 */
public class CustomExceptionModeling {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        try {
            int a = 2;
            int b = 3;

            if (a > b) {
                throw new CustomException("A больше B");
            } else if (a < b) {
                throw new CustomException("A меньше B");
            } else {
                throw new CustomException("A и B равны");
            }
        } catch (CustomException ex) {
            ex.printStackTrace();
        }
    }

    static class CustomException extends Exception{
        CustomException(String message) {
            super(message);
        }
    }
}
