package com.example.todolist;

public class DBManager {
    private static final String DB_URL = "jdbc:mariadb://<IP주소>:3306/todolist";
    private static final String USER = "android_user";
    private static final String PASSWORD = "password123";

    public static Connection connect() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
