import java.sql.*;

public class WorkDB {
    private String connString;

    public WorkDB(){
        String relativePath = "ptmk.db";  // относительный путь к базе данных

        // Получаем текущий рабочий каталог
        String currentDirectory = System.getProperty("user.dir");

        // Составляем полный путь к базе данных
        String fullPath = currentDirectory + "/" + relativePath;

        connString = "jdbc:sqlite:" + fullPath;
    }

    public void NonQuery(String command){
        try(Connection conn = DriverManager.getConnection(connString)){
            try(Statement statement = conn.createStatement()){
                statement.executeUpdate(command);
                System.out.println("Success create table!");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
