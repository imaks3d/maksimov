package commands;

import java.io.PrintStream;

public class ExitCommand extends GenericCommand {
   volatile Boolean exitCondition = false;

   public Boolean getExitCondition() {
      return this.exitCondition;
   }

   public ExitCommand(PrintStream printStream) {
      super(printStream);
   }

   public void Execute() throws Exception {
      this.exitCondition = true;
   }

   public String Description() {
      return " - closes the application.";
   }

   public boolean VerifyInputParameters(String[] args) {
      return args.length == 1;
   }
}
