package commands;

import dataBase.DataBase;
import commands.UserStatusManager;
import java.io.PrintStream;

public class FilterContainsNameCommand extends GenericCommand {
   private final DataBase db;
   private String substring;
   private final UserStatusManager userStatusManager;

   public FilterContainsNameCommand(PrintStream printStream, DataBase db, UserStatusManager userStatusManager) {
      super(printStream);
      this.db = db;
      this.userStatusManager = userStatusManager;
   }

   public void Execute() {
      if (!this.userStatusManager.getStatus()) {
         System.out.println("You are not authorized, try to login or register");
      } else {
         this.db.filterContainsName(this.substring).forEach((studyGroup) -> {
            this.printStream.println(studyGroup);
         });
      }

   }

   public String Description() {
      return " - displays elements whose name field contains the given substring.";
   }

   public boolean VerifyInputParameters(String[] tokens) {
      if (tokens.length != 2) {
         if (this.printStream != null) {
            this.printStream.println("Usage: filter_contains_name <substring>");
         }

         return false;
      } else {
         this.substring = tokens[1];
         return true;
      }
   }
}
