import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyApp{

    static WorkDB db = new WorkDB();
    public static void main(String[] args){
        if (args.length > 0){
            int mode = Integer.parseInt(args[0]);
            
            switch (mode) {
                case 1:
                    CreateTable();
                    break;
                case 2:
                if (args.length >= 6) {
                    String fullName = args[1] + " " + args[2] + " " + args[3];
                    String birthdate = args[4];
                    String gender = args[5];

                    Employee employee = new Employee(fullName, parseDate(birthdate), gender);
                    employee.addToDatabase();
                    System.out.println("Режим 2 выбран");
                } else {
                    System.out.println("Недостаточно аргументов для режима 2");
                }
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;

                default:
                    System.out.println("Неверно введены параметры!");
                    break;
            }
        }
        else
            System.out.println("Введите параметры!");
        
    }

    static void CreateTable(){
        String query = "CREATE TABLE IF NOT EXISTS Employees (" +
        "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "FullName TEXT NOT NULL, " +
        "Birthdate DATE NOT NULL, " +
        "Gender TEXT NOT NULL)";
        db.NonQuery(query);
    }

    private static Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Неверный ввод даты");
            return null;
        }
    }
}