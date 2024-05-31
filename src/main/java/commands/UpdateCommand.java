package commands;

import dataBase.DataBase;
import utility.StudyGroupReader;
import commands.UserStatusManager;
import data.StudyGroup;
import java.io.PrintStream;

public class UpdateCommand extends GenericCommand {
   private Integer id;
   private final DataBase db;
   private final StudyGroupReader reader;
   private final UserStatusManager userStatusManager;

   public UpdateCommand(PrintStream printStream, DataBase db, StudyGroupReader reader, UserStatusManager userStatusManager) {
      super(printStream);
      this.db = db;
      this.reader = reader;
      this.userStatusManager = userStatusManager;
   }

   public void Execute() throws Exception {
      System.out.println("Executing UpdateCommand with ID: " + this.id);
      if (this.id == null) {
         throw new IllegalStateException("ID not set. VerifyInputParameters should be called before Execute.");
      }
      StudyGroup studyGroup = this.reader.readStudyGroupFromConsole();
      if (!this.userStatusManager.getStatus()) {
         this.printStream.println("You are not authorized, try to login or register");
      } else {
         // Устанавливаем user_name для обновленной StudyGroup
         studyGroup.setUser_name(this.userStatusManager.getUser_name());
         // Вызываем метод обновления в базе данных
         this.db.update(this.id, studyGroup, this.userStatusManager.getUser_name());
      }
   }

   public String Description() {
      return " - updates a StudyGroup which \"id\" is id.";
   }

   public boolean VerifyInputParameters(String[] tokens) {
      System.out.println("Verifying parameters: " + String.join(", ", tokens));
      if (tokens.length != 2) {
         return false;
      } else {
         try {
            this.id = Integer.parseInt(tokens[1]);
            System.out.println("Parsed ID: " + this.id);
            return true;
         } catch (NumberFormatException e) {
            System.out.println("Failed to parse ID: " + tokens[1]);
            return false;
         }
      }
   }
}
