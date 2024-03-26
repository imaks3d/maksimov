import data.StudyGroup;
import сommands.CollectionManager;
import сommands.CommandManager;
import сommands.Console;
import сommands.FileManager;
import exceptions.FileReadPermissionException;
import exceptions.StudyGroupValidateException;
import utility.StudyGroupReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Main class that start interactive mode
 *
 */
public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            File file = new File(fileName);
            Scanner scanner = new Scanner(System.in);

            StudyGroupReader studyGroupReader = new StudyGroupReader(scanner);
            FileManager fileManager = new FileManager(file);
            CollectionManager collectionManager = new CollectionManager(fileManager.readElementsFromFile());
            CommandManager commandManager = new CommandManager(fileManager, studyGroupReader, collectionManager);

            Console console = new Console(scanner, commandManager);
            console.startInteractiveMode();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Имя файла не указано");
        } catch (FileNotFoundException | FileReadPermissionException | StudyGroupValidateException e) {
            System.out.println(e.getMessage());
        }
    }
}
