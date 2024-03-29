package сommands;

import data.StudyGroup;
import exceptions.FileReadPermissionException;
import exceptions.RecursiveScriptException;
import utility.StudyGroupReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class CommandManager {
    private final Set<String> scriptNames;
    private final CollectionManager collectionManager;
    private final StudyGroupReader studyGroupReader;
    private final FileManager fileManager;
    private final Method[] methods;
    private boolean isScriptExecuting;
    /**
     * Class that execute user commands
     */
    public CommandManager(FileManager fileManager, StudyGroupReader studyGroupReader, CollectionManager collectionManager) {
        this.fileManager = fileManager;
        this.studyGroupReader = studyGroupReader;
        this.collectionManager = collectionManager;
        this.methods = CommandManager.class.getMethods();
        this.isScriptExecuting = false;
        scriptNames = new HashSet<>();
    }
    /**
     * prints supporting information
     */
    public void help() {
        System.out.println("info: Выводит информацию о коллекции");
        System.out.println("show: Выводит все элементы коллекции");
        System.out.println("add: Добавляет элемент в коллекцию");
        System.out.println("update: Обновляет значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_by_id: Удаляет элемент из коллекции по его id");
        System.out.println("clear: Очищает коллекцию");
        System.out.println("save: Сохраняет коллекцию в файл");
        System.out.println("execute_script: Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь");
        System.out.println("add_if_max: Добавляет новый элемент в коллекцию, если его значение больше, чем у наибольшего элемента этой коллекции");
        System.out.println("remove_greater: Удаляет из коллекции все элементы, превышающие заданный");
        System.out.println("remove_lower: Удаляет из коллекции все элементы, меньшие, чем заданный");
        System.out.println("exit: Завершает выполнение программы без сохранения в файл");
        System.out.println("min_by_coordinates: Выводит объект из коллекции, значение поля coordinates которого является минимальным");
    }
    /**
     * prints info about collection
     */
    public void info() {
        System.out.println("Тип коллекции - " + collectionManager.getCollectionName());
        System.out.println("Количество элементов - " + collectionManager.getSize());
        System.out.println("Время инициализации - " + collectionManager.getCreationDateTime());
    }
    /**
     * prints all elements of collection
     */
    public void show() {
        for (StudyGroup studyGroup : this.collectionManager.getSortedCollection()) {
            System.out.println(studyGroup);
        }
    }
    /**
     * add new route to collection
     */
    public void add() {
        boolean success = collectionManager.add(getStudyGroup());
        if (!success) {
            System.out.println("Ошибка при добавлении элемента. Возможно, такой элемент уже существует.");
        }
    }
    /**
     * update a studyGroup in collection
     * @param argument user-entered argument, which should be a long number
     */
    public void update(String argument) {
        try {
            int id = Integer.parseInt(argument);
            if (collectionManager.existElementWithId(id)) {
                collectionManager.updateById(id, getStudyGroup());
                System.out.println("Элемент успешно обновлён.");
            } else {
                System.out.println("Элемента с таким id не существует.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе целого числа.");
        }
    }
    /**
     * remove studyGroup from collection
     * @param argument user-entered argument, which should be a long number
     */
    public void removeById(String argument) {
        try {
            int id = Integer.parseInt(argument);
            if (collectionManager.existElementWithId(id)) {
                collectionManager.removeById(id);
                System.out.println("Элемент успешно удалён");
            } else {
                System.out.println("Элемента с таким id не существует.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе целого числа.");
        }
    }
    /**
     * remove all element from collection
     */
    public void clear() {
        collectionManager.clear();
        System.out.println("Коллекция успешно очищена.");
    }

    /**
     * save collection to file
     */
    public void save() {
        fileManager.saveToFile(collectionManager.getSortedCollection());
    }
    public void executeScript(String scriptName) throws FileNotFoundException {
        File file = new File(scriptName);
        if (!file.exists()) {
            throw new FileNotFoundException("Скрипта с таким именем не существует");
        }
        if (!file.canRead()) {
            throw new FileReadPermissionException("Нет прав для чтения скрипта");
        }
        if (scriptNames.contains(scriptName)) {
            throw new RecursiveScriptException("Скрипты нельзя вызывать рекурсивно");
        }
        this.isScriptExecuting = true;
        scriptNames.add(scriptName);
        Scanner scannerToScript = new Scanner(file);
        Scanner consoleScanner = studyGroupReader.getScanner();
        studyGroupReader.setScanner(scannerToScript);
        System.out.printf("Исполнение скрипта \"%s\"%n", scriptName);
        System.out.println("Исполнение скрипта \"" + scriptName + "\"");
        while (scannerToScript.hasNext()) {
            String inputCommand = scannerToScript.nextLine();
            System.out.println("Исполнение команды \"" + inputCommand + "\"");
            this.executeCommand(inputCommand);
        }
        System.out.println("Исполнение скрипта \"" + scriptName + "\" завершено");

        studyGroupReader.setScanner(consoleScanner);
        scriptNames.remove(scriptName);
        this.isScriptExecuting = false;
    }
    /**
     * add studyGroup entered by user to collection if it's greater than the minimal studyGroup in collection
     */
    public void addIfMax() {
        boolean success = collectionManager.addIfMax(getStudyGroup());
        if (success) {
            System.out.println("Элемент успешно добавлен.");
        } else {
            System.out.println("Элемент не больше наибольшего элемента коллекции.");
        }
    }
    /**
     * remove all studyGroups in collection that greater than route entered by user
     */
    public void removeGreater() {
        collectionManager.removeGreater(getStudyGroup());
        System.out.println("Все элементы большие данного успешно удалены");
    }
    /**
     * remove all studyGroup in collection that lower than route entered by user
     */
    public void removeLower() {
        collectionManager.removeLower(getStudyGroup());
        System.out.println("Все элементы меньшие данного успешно удалены");
    }
    /**
     * print studyGroup from collection which coordinates is minimum
     */
    public void minByCoordinates() {
        try {
            System.out.println(collectionManager.minByCoordinates());
        } catch (NoSuchElementException e) {
            System.out.println("Коллекция пуста.");
        }
    }
    /**
     * method which get new studyGroup from console or from script
     * @return new studyGroup read from script of from console
     */
    public StudyGroup getStudyGroup() {
        if (isScriptExecuting) {
            System.out.println("Попытка чтения элемента из скрипта");
            return studyGroupReader.readStudyGroupFromScript();
        } else {
            return studyGroupReader.readStudyGroupFromConsole();
        }
    }
    /**
     * main method, that execute commands using Reflection API
     * @param inputCommand command entered by user
     */

    public boolean executeCommand(String inputCommand) {
        String[] inputLineDivided = inputCommand.trim().split(" ", 2);
        String command = inputCommandToJavaStyle(inputLineDivided[0].toLowerCase());
        if ("exit".equals(command)) {
            return true;
        }
        try {
            Method methodToInvoke = null;
            for (Method method : methods) {
                if (method.getName().equals(command)) {
                    methodToInvoke = method;
                    break;
                }
            }
            if (methodToInvoke == null) {
                throw new NoSuchMethodException();
            }
            if (inputLineDivided.length == 1) {
                methodToInvoke.invoke(this);
            } else {
                methodToInvoke.invoke(this, inputLineDivided[1]);
            }
        } catch (NoSuchMethodException | IllegalArgumentException e) {
            System.out.println("Такой команды не существует");
        } catch (InvocationTargetException e) {
            if (e.getCause().getClass().equals(NoSuchElementException.class)) {
                return true;
            }
            System.out.println(e.getCause().getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
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
