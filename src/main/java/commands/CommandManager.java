package commands;

import data.StudyGroup;
import dataBase.CommandLogger;
import dataBase.DataBase;
import dataBase.DataBaseManager;
import utility.StudyGroupReader;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class CommandManager {
    private final Set<String> scriptNames;
    private final DataBase db;
    private final DataBaseManager dataBaseManager;
    private final PrintStream printStream;
    private final Map<String, GenericCommand> commands = new HashMap<>();
    private final Scanner scanner;
    private StudyGroupReader studyGroupReader;
    private final UserStatusManager userStatusManager;
    private boolean isScriptExecuting;
    private final ExitCommand exitCommand;
    private final ExecutorService executorService;
    private final ReentrantLock lock = new ReentrantLock();
    private CommandLogger commandLogger;

    public CommandManager(UserStatusManager userStatusManager, DataBaseManager dataBaseManager, DataBase db, Scanner scanner) {
        this.userStatusManager = userStatusManager;
        this.db = db;
        this.scanner = scanner;
        this.studyGroupReader = new StudyGroupReader(scanner);  // Initialize the StudyGroupReader here
        this.printStream = System.out;
        this.dataBaseManager = dataBaseManager;
        scriptNames = new HashSet<>();
        this.exitCommand = new ExitCommand(this.printStream);
        this.initializeCommands();
        this.executorService = Executors.newFixedThreadPool(10); // Инициализация пула потоков с 10 потоками
    }

    private void initializeCommands() {
        this.commands.put("add", new AddCommand(this.printStream, this.db, this.studyGroupReader, this.userStatusManager));
        this.commands.put("add_if_min", new AddIfMinCommand(this.printStream, this.db, this.studyGroupReader, this.userStatusManager));
        this.commands.put("add_if_max", new AddIfMaxCommand(this.printStream, this.db, this.studyGroupReader, this.userStatusManager));
        this.commands.put("clear", new ClearCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("exit", this.exitCommand);
        this.commands.put("help", new HelpCommand(this.printStream, this));
        this.commands.put("info", new InfoCommand(this.printStream, this.db));
        this.commands.put("remove_by_id", new RemoveByIdCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("show", new ShowCommand(this.printStream, this.db));
        this.commands.put("update", new UpdateCommand(this.printStream, this.db, this.studyGroupReader, this.userStatusManager));
        this.commands.put("save", new SaveCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("filter_contains_name", new FilterContainsNameCommand(this.printStream, this.db, this.userStatusManager));
        this.commands.put("execute_script", new ExecuteScriptCommand(this.printStream, this));
        this.commands.put("login", new LoginCommand(this.userStatusManager, this.printStream, this.dataBaseManager));
        this.commands.put("register", new RegisterCommand(this.userStatusManager, this.printStream, this.dataBaseManager));
        this.commands.put("logout", new LogoutCommand(this.userStatusManager, this.printStream));
        this.commands.put("users", new Users(this.printStream, this.db));
        this.commands.put("count_less_than_transferred_students", new CountLessThanTransferredStudentsCommand(this.printStream, this.db));
        this.commands.put("min_by_coordinates", new MinByCoordinatesCommand(this.printStream, this.db));
    }

    public GenericCommand getCommand(String commandName) {
        return this.commands.get(commandName);
    }

    public StudyGroup getStudyGroup() {
        if (isScriptExecuting) {
            System.out.println("Trying to read an element from a script");
            return studyGroupReader.readStudyGroupFromScript();
        } else {
            return studyGroupReader.readStudyGroupFromConsole();
        }
    }

    private boolean exitCondition = false;

    public boolean getExitCondition() {
        return exitCondition;
    }

    public void Run() {
        System.out.println("Enter your login or register to access full functionality!");

        while (!this.exitCommand.getExitCondition()) {
            this.printStream.print("Enter command: ");
            if (this.scanner.hasNextLine()) {
                String commandLine = this.scanner.nextLine();
                this.processCommand(commandLine);
            }
        }
    }
    public void processCommand(String commandLine) {
        if (!commandLine.isEmpty()) {
            String[] tokens = commandLine.trim().split("\\s+");
            if (tokens.length != 0) {
                String commandName = tokens[0];
                GenericCommand command = this.getCommand(commandName);
                if (command != null) {
                    try {
                        command.setTokens(tokens);
                        if (command.VerifyInputParameters(tokens)) {
                            command.Execute();
                            this.commandLogger.Add(commandName);
                        }
                    } catch (Exception var6) {
                        this.printStream.println("");
                    }
                } else {
                    this.printStream.println("This command not found");
                }

            }
        }
    }

    public String help() {
        StringBuilder help = new StringBuilder("Available commands:\n");
        for (Map.Entry<String, GenericCommand> entry : this.commands.entrySet()) {
            help.append(entry.getKey()).append(": ").append(entry.getValue().Description()).append("\n");
        }
        return help.toString();
    }

    private static String inputCommandToJavaStyle(String str) {
        StringBuilder result = new StringBuilder();
        boolean needUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '_') {
                if (needUpperCase) {
                    c = Character.toUpperCase(c);
                    needUpperCase = false;
                }
                result.append(c);
            } else {
                needUpperCase = true;
            }
        }
        return result.toString();
    }
}
