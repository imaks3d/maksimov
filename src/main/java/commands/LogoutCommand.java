package commands;

import commands.UserStatusManager;
import java.io.PrintStream;
import java.util.Scanner;

public class LogoutCommand extends GenericCommand {
    private final UserStatusManager userStatusManager;

    public LogoutCommand(UserStatusManager userStatusManager, PrintStream printStream) {
        super(printStream);
        this.userStatusManager = userStatusManager;
    }

    public void Execute() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Y if you really want to logout");
        String inputCheck = scanner.nextLine();
        if (inputCheck.equals("Y") || inputCheck.equals("y")) {
            this.userStatusManager.setStatus(false);
            this.userStatusManager.setUser_name("");
            System.out.println("You are logged out");
        }

    }

    public String Description() {
        return " - logout user.";
    }

    public boolean VerifyInputParameters(String[] tokens) {
        return tokens.length == 1;
    }
}