package commands;

import dataBase.DataBase;
import commands.UserStatusManager;
import java.io.PrintStream;

public class SaveCommand extends GenericCommand {
   private final DataBase db;
   private final UserStatusManager userStatusManager;

   public SaveCommand(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
      super(printStream);
      this.db = db;
      this.userStatusManager = userStatusManager;
   }

   public void Execute() throws Exception {
      if (!this.userStatusManager.getStatus()) {
         System.out.println("You are not authorized, try to login or register");
      } else {
         this.db.save(this.userStatusManager.getUser_name());
      }

   }

   public String Description() {
      return " - saves database to JSON file.";
   }

   public boolean VerifyInputParameters(String[] args) {
      return args.length == 1;
   }
}
