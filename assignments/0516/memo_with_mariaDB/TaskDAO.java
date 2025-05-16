package com.example.todolist;

public class TaskDAO {
    public static void insertTask(String title) {
        Connection conn = DBManager.connect();
        try {
            String sql = "INSERT INTO tasks (title) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTasks() {
        List<String> tasks = new ArrayList<>();
        Connection conn = DBManager.connect();
        try {
            String sql = "SELECT * FROM tasks";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tasks.add(rs.getString("title") + " - " + (rs.getInt("is_completed") == 1 ? "완료" : "미완료"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
