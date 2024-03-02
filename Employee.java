import java.util.Date;
import java.util.Random;


public class Employee {
    
    private String fullName;
    private Date birthdate;
    private String gender;

    public Employee(String fullName, Date birthdate, String gender) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public void addToDatabase() {
        
            String insertSQL = "INSERT INTO Employees (FullName, Birthdate, Gender) VALUES (?, ?, ?)";
            WorkDB db = new WorkDB();
            db.NonQuery(insertSQL);
            System.out.println("Запись добавлена успешно");
            
    }

    public static int calculateAge(Date birthdate) {
        Date now = new Date();
        int age = now.getYear() - birthdate.getYear();
        if (now.getMonth() < birthdate.getMonth() || (now.getMonth() == birthdate.getMonth() && now.getDate() < birthdate.getDate())) {
            age--;
        }
        return age;
    }

    public static String generateRandomFullName() {
        String[] firstNames = {"John", "Jane", "Michael", "Emily", "Christopher", "Olivia"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis"};

        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];

        return firstName + " " + lastName;
    }

    public static Date generateRandomDate() {
        long millisInYear = 365L * 24 * 60 * 60 * 1000; // milliseconds in a year
        long randomMillis = (long) (Math.random() * millisInYear * 30); // up to 30 years in the past

        return new Date(System.currentTimeMillis() - randomMillis);
    }
}
