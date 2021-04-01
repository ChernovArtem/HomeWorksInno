package part1.lesson11.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Отдельный поток для сервера, т.к. serverSocket.accept() блочит весь поток
 */
public class Server extends Thread {

    /** слушаемый сокет */
    private final ServerSocket serverSocket;

    /** словарь клиентов */
    private final Map<String, SocketThread> clientMap = new ConcurrentHashMap();;

    /**
     * Конструктор
     * @param serverSocket - слушаемый сокет
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Слушаем сокет и создаем для каждого клиента свой поток
     */
    @Override
    public void run() {
        try {
            while(!isInterrupted()) {
                Socket socket = serverSocket.accept();
                Thread socketThread = new SocketThread(clientMap, socket);
                socketThread.start();
            }
        } catch (SocketException ex) {
            //Nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
