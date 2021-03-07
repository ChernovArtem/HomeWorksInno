package part1.lesson03.task01;

/**
 * Моделирование ошибки NullPointerException
 */
public class NullPointerModeling {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        try {
            A a = null;
            a.method();
        } catch (java.lang.NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    static class A {
        void method() {
            System.out.println("Print method");
        }
    }
}
