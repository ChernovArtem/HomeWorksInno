package part1.lesson11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientMain {

    private static final int PORT = 25543;

    public static void main(String[] args) {
        try {
            final Socket socket = new Socket("localhost", PORT);

            //поток с чтением консоли
            Thread consoleWrite = getConsoleWriteThread(socket);
            consoleWrite.start();

            //общий поток с записью пришедших сообщений
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while (consoleWrite.isAlive()) {
                    message = reader.readLine();
                    if (message != null) {
                        System.out.println(message);
                    }
                }
            }
        } catch (SocketException ex) {
            //Nothing
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Создание потока с чтением консоли
     * если в косоли напишут quit, то поток закрывается
     * @param socket - сокет для передачи сообщений
     * @return поток с чтением консоли
     */
    private static Thread getConsoleWriteThread(Socket socket) {
        return new Thread(() -> {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                Scanner scanner = new Scanner(System.in);

                String message;
                while (!(message = scanner.nextLine()).equals("quit")) {
                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
