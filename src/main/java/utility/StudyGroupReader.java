package utility;

import data.*;
import exceptions.ReadElementFromScriptException;

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
     * @param scanner new scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    /**
     * @return return current scanner
     */
    public Scanner getScanner() {
        return scanner;
    }
    /**
     * @return new StudyGroup read from console
     */
    public StudyGroup readStudyGroupFromConsole() {
        return new StudyGroup(readName(), readCoordinates(), readCountStudents(), readShouldBeExpelled(), readTransferredStudents(), readFormOfEducation(), readPerson());
    }
    /**
     * @return new StudyGroup read from script
     */
    public StudyGroup readStudyGroupFromScript() {
        try {
            String studyGroupName = scanner.nextLine();

            int coordinatesX = Integer.parseInt(scanner.nextLine());
            Double coordinatesY = Double.parseDouble(scanner.nextLine());
            int countStudents = Integer.parseInt(scanner.nextLine());
            int shouldBeExpelled = Integer.parseInt(scanner.nextLine());
            long transferredStudents = Long.parseLong(scanner.nextLine());
            String formOfEducationName = scanner.nextLine();
            FormOfEducation formOfEducation = FormOfEducation.valueOf(formOfEducationName.toUpperCase());
            String personName = scanner.nextLine();
            Long personHeight = Long.parseLong(scanner.nextLine());
            double personWeight = Double.parseDouble(scanner.nextLine());
            String personPassportID = scanner.nextLine();
            Long locationX = Long.parseLong(scanner.nextLine());
            Double locationY = Double.parseDouble(scanner.nextLine());
            double locationZ = Double.parseDouble(scanner.nextLine());
            String locationName = scanner.nextLine();


            StudyGroup studyGroup = new StudyGroup(studyGroupName, new Coordinates(coordinatesX, coordinatesY), countStudents, shouldBeExpelled, transferredStudents, formOfEducation, new Person(personName, personHeight, personWeight, personPassportID, new Location(locationX, locationY, locationZ, locationName)));
            StudyGroupValidator.validateStudyGroup(studyGroup);
            return studyGroup;
        } catch (Exception e) {
            throw new ReadElementFromScriptException("Ошибка при чтении элемента из скрипта. Проверьте правильность данных", e);
        }
    }
    /**
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
     * @return read FormOfEducation of StudyGroup from console
     */
    public FormOfEducation readFormOfEducation() {
        System.out.println("Выберите форму обучения:");
        for (FormOfEducation value : FormOfEducation.values()) {
            System.out.println(value.getDescription());
        }
        String choice;
        do {
            System.out.print("Введите название выбранной формы обучения: ");
            choice = scanner.nextLine().trim().toUpperCase(); // Приводим к верхнему регистру
        } while (!isValidFormOfEducation(choice));

        return FormOfEducation.valueOf(choice.replace(" ", "_")); // Приводим к формату перечисления
    }

    private boolean isValidFormOfEducation(String choice) {
        for (FormOfEducation value : FormOfEducation.values()) {
            if (value.name().equalsIgnoreCase(choice.replace(" ", "_"))) {
                return true;
            }
        }
        System.out.println("Неверное название формы обучения. Пожалуйста, повторите попытку.");
        return false;
    }
    /**
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
        String locationName = readString();
        location = new Location(x, y, z, locationName);
        return new Person(name, height, weight, passportID, location);
    }

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

    private String readStringOrNull() {
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
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