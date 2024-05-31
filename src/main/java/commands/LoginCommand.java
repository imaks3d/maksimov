package commands;

import dataBase.DataBaseManager;
import commands.PasswordManager;
import commands.UserStatusManager;
import java.io.PrintStream;
import java.util.Scanner;

public class LoginCommand extends GenericCommand {
    DataBaseManager db;
    private final UserStatusManager userStatusManager;

    public LoginCommand(UserStatusManager userStatusManager, PrintStream printStream, DataBaseManager db) {
        super(printStream);
        this.db = db;
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        Scanner scanner = new Scanner(System.in);

        String inputCheck;
        do {
            do {
                System.out.print("Enter name to login: ");
            } while(!scanner.hasNextLine());

            String user_name = scanner.nextLine();
            if (this.db.checkUser(user_name)) {
                String inputCheck2;
                do {
                    System.out.print("Enter password: ");
                    inputCheck2 = PasswordManager.enterPassword();
                    if (this.db.checkPassword(user_name, inputCheck2)) {
                        this.userStatusManager.setStatus(true);
                        this.userStatusManager.setUser_name(user_name);
                        System.out.println("You have successfully logged in to your account");
                        return;
                    }

                    System.out.println("Enter 'Y' if you want to try again");
                    inputCheck = scanner.nextLine();
                } while(inputCheck.equals("Y") || inputCheck.equals("y"));

                return;
            }

            System.out.println("Error: Cant find such user");
            System.out.println("Enter 'Y' if you want to try again, you can try to register!!!");
            inputCheck = scanner.nextLine();
        } while(inputCheck.equals("Y") || inputCheck.equals("y"));

    }

    public String Description() {
        return " - login user.";
    }

    public boolean VerifyInputParameters(String[] tokens) {
        return tokens.length == 1;
    }
}
