package commands;

import dataBase.DataBase;
import utility.StudyGroupReader;
import commands.UserStatusManager;
import data.StudyGroup;
import java.io.PrintStream;

public class AddCommand extends GenericCommand {
   DataBase db;
   StudyGroupReader reader;
   UserStatusManager userStatusManager;

   public AddCommand(PrintStream printStream, DataBase db, StudyGroupReader reader, UserStatusManager userStatusManager) {
      super(printStream);
      this.db = db;
      this.reader = reader;
      this.userStatusManager = userStatusManager;
   }

   public void Execute() throws Exception {
      if (!this.userStatusManager.getStatus()) {
         System.out.println("You are not authorized, try to login or register");
      } else {
         StudyGroup studyGroup = this.reader.readStudyGroupFromConsole();
         studyGroup.setUser_name(this.userStatusManager.getUser_name());
         this.db.add(studyGroup);
      }

   }

   public String Description() {
      return " - adds a new StudyGroup in to the database.";
   }

   public boolean VerifyInputParameters(String[] args) {
      return args.length == 1;
   }
}
