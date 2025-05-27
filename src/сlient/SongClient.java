package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class SongClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("📡 Подключено к серверу. Получаем песни...");

            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(";");
                String title = parts[0];
                String artist = parts[1];
                String album = parts[2];
                String duration = parts[3];

                System.out.println("🎵 " + title + " - " + artist + " [" + album + "] (" + duration + " сек)");
            }

            System.out.println("✅ Получение завершено.");

        } catch (Exception e) {
            System.out.println("❌ Ошибка клиента:");
            e.printStackTrace();
        }
    }
}
