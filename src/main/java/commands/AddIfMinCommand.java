package commands;

import dataBase.DataBase;
import utility.StudyGroupReader;
import commands.UserStatusManager;
import data.StudyGroup;
import java.io.PrintStream;

public class AddIfMinCommand extends GenericCommand {
   DataBase db;
   StudyGroupReader reader;
   UserStatusManager userStatusManager;

   public AddIfMinCommand(PrintStream printStream, DataBase db, StudyGroupReader reader, UserStatusManager userStatusManager) {
      super(printStream);
      this.db = db;
      this.reader = reader;
      this.userStatusManager = userStatusManager;
   }

   public void Execute() {
      if (!this.userStatusManager.getStatus()) {
         System.out.println("You are not authorized, try to login or register");
      } else {
         try {
            StudyGroup studyGroup = this.reader.readStudyGroupFromConsole();
            studyGroup.setUser_name(this.userStatusManager.getUser_name());
            this.db.addIfMin(studyGroup);
         } catch (IllegalArgumentException var3) {
            if (this.printStream != null) {
               this.printStream.println(var3.getMessage());
            }
         } catch (Exception var4) {
            if (this.printStream != null) {
               this.printStream.println("An error occurred while adding the StudyGroup: " + var4.getMessage());
            }
         }
      }

   }

   public String Description() {
      return " -  add a StudyGroup in case it is minimal.";
   }

   public boolean VerifyInputParameters(String[] args) {
      return true;
   }
}
