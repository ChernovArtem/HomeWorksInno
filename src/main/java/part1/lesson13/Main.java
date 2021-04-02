package part1.lesson13;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){

        //task01
        exampleWithHeapSpace();

        //task02
        exampleWithMetaspace();
    }

    /**
     * Ошибка OutOfMemoryError c пометкой Metaspace.
     * Для более быстрого результата, параметры: -Xmx100m
     */
    private static void exampleWithHeapSpace() {
        StringBuilder builder = new StringBuilder();

        for (long i = 1; ; i++) {
            //удаляем по 10к, т.к. по заданию указано периодически частично удалять,
            //т.е. примерно с 1000 итераций записывается по 14к символов
            if ((i % 1000) == 0) {
                builder.delete(0, 10000);
            }

            builder.append("just test text");
        }
    }

    /**
     * Ошибка OutOfMemoryError c пометкой Java Heap Space.
     * Для более быстрого результата, параметры: -XX:MaxMetaspaceSize=25m
     */
    private static void exampleWithMetaspace() {

        List<Object> list = new ArrayList<>();

        StringBuilder builder = null;
        for (int i = 0; ; i++) {

            //удаление переодически частично
            if ((i % 1000) == 0 && i != 0) {
                for (int d = 0; d < 100; d++) {
                    list.remove(d);
                }
            }

            WorkFile workFile = new WorkFile(i);
            if (builder == null) {
                builder = workFile.readClass();
            }

            String str = workFile.changeClass(builder);
            workFile.writeClass(str);

            workFile.compile();

            Object obj = workFile.loadAndInitClass();
            list.add(obj);
        }
    }
}
