package part1.lesson11.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Отдельный поток для чтения с консоли
 */
public class SocketRead extends Thread {

    /** специальный атрибут для отправки сообщений конкретному пользователю */
    private final static String SPEC = "/spec ";

    /** общий словарь клиентов */
    private final Map<String, SocketThread> clientMap;

    /** поток для чтения */
    private final BufferedReader reader;

    /** имя клиента */
    private final String name;

    /**
     * Конструктор
     * @param clientMap - справочник
     * @param reader - поток для чтения
     * @param name - имя клиента
     */
    public SocketRead(Map<String, SocketThread> clientMap, BufferedReader reader, String name) {
        this.clientMap = clientMap;
        this.reader = reader;
        this.name = name;
    }

    /**
     * отдельный поток для чтения с консоли, т.к. reader.readLine() блочит весь поток
     */
    @Override
    public void run() {
        try {
            String message;
            while (((message = reader.readLine()) != null)) {

                if (message.startsWith(SPEC)) {
                    //отправляем конкретному пользователю
                    individualMessage(message.replace(SPEC, ""));
                } else {
                    //отправляем всем, кроме себя
                    generalMessage(message);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Отправка сообщения конкретному пользователю
     * @param message - отправляемое сообщение
     */
    private void individualMessage(String message) throws IOException {

        //считываем имя клиента
        char chr;
        StringBuilder whom = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            chr = message.charAt(i);
            if (chr == ' ') {
                break;
            }
            whom.append(chr);
        }

        //отправляем сообщение клиенту, если удалось найти в справочнике
        final String readyMessage = message.replace(whom.toString() + " ", "");
        final SocketThread socketThread = clientMap.computeIfPresent(whom.toString(), (key, value) -> {
            try {
                value.sendMessage(name, readyMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return value;
        });

        //если не удалось найти указанного пользователя, то отправляем сообщение всем
        if (socketThread == null) {
            generalMessage(readyMessage);
        }
    }

    /**
     * Отправка сообщения всем пользователям
     * @param message - отправляемое сообщение
     */
    private void generalMessage(String message) throws IOException {
        Set<Map.Entry<String, SocketThread>> entries = clientMap.entrySet();
        for (Map.Entry<String, SocketThread> entry : entries) {
            if (!entry.getKey().equals(name)) {
                entry.getValue().sendMessage(name, message);
            }
        }
    }
}
