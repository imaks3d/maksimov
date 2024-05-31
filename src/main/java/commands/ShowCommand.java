package commands;

import dataBase.DataBase;
import java.io.PrintStream;

public class ShowCommand extends GenericCommand {
   private final DataBase db;

   public ShowCommand(PrintStream printStream, DataBase db) {
      super(printStream);
      this.db = db;
   }

   public void Execute() throws Exception {
      if (this.printStream != null) {
         this.printStream.println(this.db);
      }

   }

   public String Description() {
      return " - prints out the entities from the database.";
   }

   public boolean VerifyInputParameters(String[] args) {
      return args.length == 1;
   }
}
