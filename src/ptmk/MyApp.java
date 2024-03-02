package src.ptmk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Random;

public class MyApp {
    private static final String DB_URL = "jdbc:sqlite:ptmk.db";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyApp <mode> [additional parameters]");
            return;
        }

        int mode = Integer.parseInt(args[0]);

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            switch (mode) {
                case 1:
                    createEmployeeTable(connection);
                    break;
                case 2:
                    if (args.length < 5) {
                        System.out.println("Usage: java MyApp 2 <full_name> <dob> <gender>");
                        return;
                    }
                    addEmployee(connection, args[1], args[2], args[3]);
                    break;
                case 3:
                    displayEmployees(connection);
                    break;
                case 4:
                    autoFillEmployees(connection);
                    break;
                case 5:
                    measureQueryTime(connection);
                    break;
                case 6:
                    optimizeDatabase(connection);
                    break;
                default:
                    System.out.println("Invalid mode.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createEmployeeTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "full_name TEXT NOT NULL," +
                    "dob TEXT NOT NULL," +
                    "gender TEXT NOT NULL)";
            statement.executeUpdate(query);
            System.out.println("Employee table created successfully.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void addEmployee(Connection connection, String fullName, String dob, String gender) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO employees (full_name, dob, gender) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, dob);
            preparedStatement.setString(3, gender);
            preparedStatement.executeUpdate();
            System.out.println("Employee added successfully.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void displayEmployees(Connection connection) {
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees ORDER BY full_name")) {

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Full Name: " + resultSet.getString("full_name"));
                System.out.println("DOB: " + resultSet.getString("dob"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                System.out.println();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void autoFillEmployees(Connection connection) {
        Random random = new Random();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO employees (full_name, dob, gender) VALUES (?, ?, ?)")) {

            for (int i = 0; i < 1000000; i++) {
                String fullName = generateRandomName();
                String dob = generateRandomDOB();
                String gender = random.nextBoolean() ? "Male" : "Female";

                preparedStatement.setString(1, fullName);
                preparedStatement.setString(2, dob);
                preparedStatement.setString(3, gender);

                preparedStatement.addBatch();

                if (i % 100 == 0) {
                    preparedStatement.executeBatch();
                }
            }

            preparedStatement.executeBatch();
            System.out.println("Auto-fill completed successfully.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void measureQueryTime(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT * FROM employees WHERE gender = 'Male' AND full_name LIKE 'F%' ORDER BY full_name")) {

            Instant start = Instant.now();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    
                }
            }

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);

            System.out.println("Query executed in: " + duration.toMillis() + " milliseconds.");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void optimizeDatabase(Connection connection) {
        // Реализация оптимизации базы данных или запросов
        // ...
    }

    private static String generateRandomName() {
        String[] firstNames = {"John", "Jane", "Alex", "Emma", "Michael", "Olivia", "William", "Sophia"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson"};
    
        Random random = new Random();
    
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
    
        // Генерация случайного отчества
        String patronymic = (random.nextBoolean()) ? " " + firstNames[random.nextInt(firstNames.length)] : "";
    
        return firstName + " " + lastName + patronymic;
    }
    
    private static String generateRandomDOB() {
        Random random = new Random();

        // Генерация случайной даты в пределах последних 30 лет
        LocalDate startDate = LocalDate.now().minusYears(30);
        int randomDay = random.nextInt(365);
        LocalDate randomDate = startDate.plusDays(randomDay);

        return randomDate.toString();
    }
}
