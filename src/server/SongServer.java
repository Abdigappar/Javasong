package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class SongServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("🎧 Сервер запущен на порту 8888. Ожидание клиента...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("👥 Клиент подключился!");

                    // Подключение к БД
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/music_app", "root", "Abdigappar0328!@#");

                    String query = "SELECT title, artist, album, duration FROM songs";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    // Отправка каждой песни в формате: title;artist;album;duration
                    while (rs.next()) {
                        String line = rs.getString("title") + ";" +
                                rs.getString("artist") + ";" +
                                rs.getString("album") + ";" +
                                rs.getInt("duration");
                        out.println(line);
                    }

                    System.out.println("📤 Все песни отправлены клиенту.");
                    conn.close();
                } catch (Exception e) {
                    System.out.println("❌ Ошибка при обслуживании клиента:");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Ошибка запуска сервера:");
            e.printStackTrace();
        }
    }
}
