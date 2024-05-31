package commands;

import java.io.PrintStream;

public class HelpCommand extends GenericCommand {
   CommandManager commandManager;

   public HelpCommand(PrintStream printStream, CommandManager commandManager) {
      super(printStream);
      this.commandManager = commandManager;
   }

   public void Execute() throws Exception {
      if (this.printStream != null) {
         this.printStream.println(this.commandManager.help());
      }
   }

   public String Description() {
      return " - prints a list of commands available.";
   }

   public boolean VerifyInputParameters(String[] args) {
      return args.length == 1;
   }
}
