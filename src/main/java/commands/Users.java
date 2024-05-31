package commands;

import dataBase.DataBase;

import java.io.PrintStream;
import java.util.List;

public class Users extends GenericCommand {
    private final DataBase db;

    public Users(PrintStream printStream, DataBase db) {
        super(printStream);
        this.db = db;
    }

    @Override
    public void Execute() {
        if (this.printStream != null) {
            this.printStream.println("USERS: ");
            List<String> usersList = this.db.getUsers();
            for (String user : usersList) {
                this.printStream.println(user);
            }
        }
    }

    @Override
    public String Description() {
        return " - show users.";
    }

    @Override
    public boolean VerifyInputParameters(String[] args) {
        return args.length == 1;
    }
}
