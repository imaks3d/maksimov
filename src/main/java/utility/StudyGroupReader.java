
package utility;

import data.*;
import exceptions.ReadElementFromScriptException;

import java.util.Arrays;
import java.util.Scanner;
/**
 * Class that read new StudyGroup from console or from script
 */
public class StudyGroupReader {
    private Scanner scanner;

    public StudyGroupReader(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * set Scanner object for data input
     * @param scanner new scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Return the value of the scanner variable that was previously set
     * @return return current scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * create a new StudyGroup object using console input
     * @return new StudyGroup read from console
     */
    public StudyGroup readStudyGroupFromConsole() {
        return new StudyGroup(readName(), readCoordinates(), readCountStudents(), readShouldBeExpelled(), readTransferredStudents(), readFormOfEducation(), readPerson());
    }

    /**
     * return new StudyGroup read from script
     * @return new StudyGroup read from script
     */

    public StudyGroup readStudyGroupFromScript() {
        return new StudyGroup(readName(), readCoordinates(), readCountStudents(), readShouldBeExpelled(), readTransferredStudents(), readFormOfEducation(), readPerson());

    }
            /**
             * return read name of StudyGroup from console
             * @return read name of StudyGroup from console
             */
    public String readName() {
        System.out.print("Введите название учебной группы: ");
        String name = scanner.nextLine();
        while (name == null || name.isEmpty()) {
            System.out.print("Название учебной группы не может быть пустым, повторите попытку: ");
            name = scanner.nextLine();
        }
        return name;
    }
    /**
     * return read Count Student of StudyGroup from console
     * @return read Count Student of StudyGroup from console
     */
    public int readCountStudents() {
        final int countMinValue = 1;
        int countStudents;
        System.out.print("Введите количество учащихся: ");
        countStudents = readInt();
        while (countStudents < countMinValue) {
            System.out.print("Количество учащихся должно быть больше нуля: ");
            countStudents = readInt();
        }
        return countStudents;
    }
    /**
     * return read ShouldBeExpelled of StudyGroup from console
     * @return read ShouldBeExpelled of StudyGroup from console
     */
    public int readShouldBeExpelled() {
        final int expelledMinValue = 1;
        int shouldBeExpelled;
        System.out.print("Введите количество учащихся, которые будут исключены: ");
        shouldBeExpelled = readInt();
        while (shouldBeExpelled < expelledMinValue) {
            System.out.print("Количество учащихся, которые будут исключены, должно быть больше нуля: ");
            shouldBeExpelled = readInt();
        }
        return shouldBeExpelled;
    }
    /**
     * return read TransferredStudents of StudyGroup from console
     * @return read TransferredStudents of StudyGroup from console
     */
    public long readTransferredStudents() {
        final int transMinValue = 0;
        long transferredStudents;
        System.out.print("Введите количество переведенных учащихся: ");
        transferredStudents = readLong();
        while (transferredStudents <= transMinValue) {
            System.out.print("Количество переведенных учащихся должно быть больше нуля: ");
            transferredStudents = readLong();
        }
        return transferredStudents;
    }
    /**
     * return read Coordinates of StudyGroup from console
     * @return read Coordinates of StudyGroup from console
     */
    public Coordinates readCoordinates() {
        final int xMinValue = -300;
        int x;
        Double y;
        System.out.print("Введите координату X: ");
        x = readInt();
        while (x < xMinValue) {
            System.out.print("Координата X должна быть больше -301, повторите попытку: ");
            x = readInt();
        }
        System.out.print("Введите координату Y: ");
        y = readDouble();
        return new Coordinates(x, y);
    }
    /**
     *return read FormOfEducation of StudyGroup from console
     * @return read FormOfEducation of StudyGroup from console
     */
    public FormOfEducation readFormOfEducation() {
        System.out.println("Выберите форму обучения:");
        int index = 1;
        for (FormOfEducation value : FormOfEducation.values()) {
            System.out.println(index + ". " + value.getDescription());
            index++;
        }

        int choice;
        do {
            System.out.print("Введите номер выбранной формы обучения: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Введите число!");
                System.out.print("Введите номер выбранной формы обучения: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера
        } while (choice < 1 || choice > FormOfEducation.values().length);

        return FormOfEducation.values()[choice - 1];
    }

    /**
     * return read Person of StudyGroup from console
     * @return read Person of StudyGroup from console
     */

    public Person readPerson() {
        String name;
        Long height;
        final int minHeight = 0;
        double weight;
        final int minWeight = 0;
        String passportID;
        Location location;
        System.out.print("Введите имя учащегося: ");
        name = readString();
        while (name == null || name.isEmpty()) {
            System.out.print("Вы не ввели имя учащегося, повторите попытку: ");
            name = readString();
        }
        System.out.print("Введите рост: ");
        height = readLongOrNull();
        while (height != null && height.longValue() <= minHeight) {
            System.out.print("Рост должен быть больше нуля: ");
            height = readLong();}
        System.out.print("Введите вес: ");
        weight = readDouble();
        while (weight <= minWeight) {
            System.out.print("Вес должен быть больше нуля: ");
            weight = readDouble();
        }
        System.out.print("Введите ID паспорта: ");
        passportID = readStringOrNull(); // Метод для чтения String с возможностью ввода null
        while (passportID != null && (passportID.length() < 5)) {
            System.out.print("ID паспорта должен содержать не менее 5 символов. Пожалуйста, повторите ввод: ");
            passportID = readStringOrNull();
        }
        System.out.println("Введите координаты локации:");
        System.out.print("Введите координату X: ");
        Long x = readLong();
        System.out.print("Введите координату Y: ");
        Double y = readDouble();
        System.out.print("Введите координату Z: ");
        double z = readDouble();
        System.out.print("Введите название локации: ");
        String locationName = readStringOrNull(); // Метод для чтения String с возможностью ввода null
        while (locationName == null) {
            System.out.print("Название локации не может быть равно null, пожалуйста, повторите ввод: ");
            locationName = readStringOrNull();
        }
        location = new Location(x, y, z, locationName);
        return new Person(name, height, weight, passportID, location);
    }

    /**
     *
     * read a line from the console or other input source using the Scanner object
     */
    private String readString() {
        String value;
        while (true) {
            try {
                value = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.print("Ошибка при вводе, повторите попытку: ");
            }
        }
        return value;
    }

    /**
     * provide the ability to enter null if an empty string is entered
     * @return null
     */
    private String readStringOrNull() {
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    /**
     * read an integer from the console
     * @return  the value of an integer
     */
    private int readInt() {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка при вводе, повторите попытку: ");
            }
        }
        return value;
    }

    /**
     * count long integers
     * @return the value of a number of type long
     */
    private long readLong() {
        Long value;
        while (true) {
            try {
                value = Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка при вводе, повторите попытку: ");
            }
        }
        return value;
    }

    /**
     * This method allows you to enter null if the entered string is empty
     * @return null
     */
    private Long readLongOrNull() {
        Long value;
        try {
            String input = scanner.nextLine().trim();
            return input.isEmpty() ? null : Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.print("Ошибка при вводе, повторите попытку: ");
            return null;
        }
    }

    /**
     * count a floating point number
     * @return the value of a floating point number
     */
    private double readDouble() {
        double value;
        while (true) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка при вводе, повторите попытку: ");
            }
        }
        return value;
    }
}
