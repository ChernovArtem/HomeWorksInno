package part1.lesson04.task02;

public class Main {

    public static void main(String[] args) {

        ObjectBox objectBox = new ObjectBox();

        Object obj1 = "1";
        Object obj2 = 2;
        Object obj3 = 3.14;

        //добавляем
        objectBox.addObject(obj1);
        objectBox.addObject(obj2);
        objectBox.addObject(obj3);

        //проверяем формирование строки
        System.out.println(objectBox.dump());

        //удаление
        objectBox.deleteObject(obj2);
        System.out.println(objectBox.dump());
    }
}
