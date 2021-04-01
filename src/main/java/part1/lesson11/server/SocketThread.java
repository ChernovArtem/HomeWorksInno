package part1.lesson11.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

/**
 * Поток под каждый клиент
 */
public class SocketThread extends Thread {

    /** общий словаь со всеми клиентами */
    private final Map<String, SocketThread> clientMap;

    /** поток для чтения сообщений от клиента */
    private final BufferedReader reader;

    /** поток для отправки сообщений клиенту */
    private final BufferedWriter writer;

    /** имя клиента */
    private String name;

    /**
     * Конструктор
     * @param clientMap - общий словарь клиентов
     * @param socket - сокет конкретного клиента
     * @throws IOException
     */
    public SocketThread(Map<String, SocketThread> clientMap, Socket socket) throws IOException {
        this.clientMap = clientMap;

        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Работа с клиентом:
     * спрашиваем имя, затем создаем отдельный поток для чтения сообщений из консоли для отправки другим клиентам
     */
    @Override
    public void run() {
        try {
            // отправляем на клиент сообщение о спросе имени
            writer.write("Enter your name, please:");
            writer.newLine();
            writer.flush();

            //спрашиваем имя
            askingForName();

            //поток для чтения получаемых сообщения, которые в дальнейшем отправляем
            Thread read = new SocketRead(clientMap, reader, name);
            read.start();

            while (read.isAlive());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            //закрытие потоков записи и чтения
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Remove " + name);
            clientMap.remove(name);
        }
    }

    /**
     * Отправка сообщения
     * @param nameFrom - от кого
     * @param message - текст сообщения
     * @throws IOException
     */
    public void sendMessage(String nameFrom, String message) throws IOException {
        writer.write(nameFrom + ": " + message);
        writer.newLine();
        writer.flush();
    }

    /**
     * Спрашиваем имя у клиента
     * @throws IOException
     */
    private void askingForName() throws IOException {

        name = reader.readLine();

        if (name.isEmpty() || name.contains(" ") || clientMap.containsKey(name)) {
            //имя занято, спрашиваем имя еще раз
            writer.write("Name is taken, please enter another name:");
            writer.newLine();
            writer.flush();

            askingForName();
        } else {
            //имя свободно - записываем
            System.out.println("Add " + name);
            writer.write("Hello " + name);
            writer.newLine();
            writer.flush();

            clientMap.putIfAbsent(name, this);
        }
    }
}
