import dataBase.DataBase;
import dataBase.DataBaseManager;
import commands.CommandManager;
import commands.UserStatusManager;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/studs";
            Properties info = new Properties();
            info.load(new FileInputStream(args[0]));
            DataBaseManager dataBaseManager = new DataBaseManager(url, info);
            dataBaseManager.initDataBase(); // Инициализация базы данных до загрузки данных
            DataBase db = new DataBase(dataBaseManager);
            UserStatusManager userStatusManager = new UserStatusManager(false, "");
            CommandManager commandManager = new CommandManager(userStatusManager, dataBaseManager, db, new Scanner(System.in));
            commandManager.Run();
        } catch (Exception e) {
            System.out.println("Abnormal termination:");
            e.printStackTrace(); // Вывод полного стека ошибок для отладки
        } finally {
            System.out.println("The application is closed.");
        }
    }
}
