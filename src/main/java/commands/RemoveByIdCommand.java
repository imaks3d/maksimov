package commands;

import dataBase.DataBase;
import commands.UserStatusManager;
import java.io.PrintStream;

public class RemoveByIdCommand extends GenericCommand {
   private final DataBase db;
   private final UserStatusManager userStatusManager;
   private int id;

   public RemoveByIdCommand(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
      super(printStream);
      this.db = db;
      this.userStatusManager = userStatusManager;
   }

   public void Execute() throws Exception {
      if (!this.userStatusManager.getStatus()) {
         System.out.println("You are not authorized, try to login or register");
      } else {
         this.db.removeById(this.id, this.userStatusManager.getUser_name());
      }

   }

   public String Description() {
      return " - removes the element with entered id.";
   }

   public boolean VerifyInputParameters(String[] tokens) {
      if (tokens.length != 2) {
         return false;
      } else {
         try {
            this.id = Integer.parseInt(tokens[1]);
            return true;
         } catch (Exception var3) {
            return false;
         }
      }
   }
}
