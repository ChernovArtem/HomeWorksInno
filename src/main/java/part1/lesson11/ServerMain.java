package part1.lesson11;

import part1.lesson11.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServerMain {

    private static final int PORT = 25543;

    public static void main(String[] args) {
        System.out.println("Server is running, listening on port " + PORT);

        //поток для чтения с консоли
        Thread console = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (!(scanner.nextLine()).equals("quit"));
        });
        console.start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            //отдельный поток для сервера
            Thread server = new Server(serverSocket);
            server.start();

            while (console.isAlive());
            server.interrupt();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            System.out.println("Server stopped");
        }
    }
}
