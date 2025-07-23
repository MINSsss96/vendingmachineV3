package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/vendingmachine_db";
                String user = "root";
                String password = "1111";  // ← 너의 MySQL 비밀번호로 변경
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("✅ DB 연결 성공!");
            } catch (Exception e) {
                System.out.println("❌ DB 연결 실패");
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void close() {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn = null;
    }
}
