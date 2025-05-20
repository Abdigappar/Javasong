package controllers;

import java.sql.*;

public class DBManager {
    private static Connection conn;

    // Подключение к базе данных
    public static void connect() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/music_app", // Название БД
                    "root", // логин
                    "Abdigappar0328!@#"  // пароль
            );
            System.out.println("✅ Успешно подключено к базе данных!");
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения к базе данных:");
            e.printStackTrace();
        }
    }

    // Получение соединения (для других классов)
    public static Connection getConnection() {
        return conn;
    }

    // Проверка логина и пароля
    public static boolean authenticate(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("❌ Ошибка при аутентификации:");
            e.printStackTrace();
            return false;
        }
    }

    // Отключение от базы (необязательно, но полезно)
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔌 Подключение к БД закрыто.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
