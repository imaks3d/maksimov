package utility;

import data.*;

import java.util.Scanner;

public class StudyGroupReader {
    private Scanner scanner;

    public StudyGroupReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public StudyGroup readStudyGroupFromConsole() {
        return new StudyGroup(readName(), readCoordinates(), readCountStudents(), readShouldBeExpelled(), readTransferredStudents(), readFormOfEducation(), readPerson());
    }

    public StudyGroup readStudyGroupFromScript() {
        return new StudyGroup(readName(), readCoordinates(), readCountStudents(), readShouldBeExpelled(), readTransferredStudents(), readFormOfEducation(), readPerson());
    }

    public String readName() {
        System.out.print("Enter name of StudyGroup: ");
        String name = scanner.nextLine();
        while (name == null || name.trim().isEmpty()) {
            System.out.print("The name of the study group cannot be empty, please try again: ");
            name = scanner.nextLine();
        }
        return name;
    }

    public int readCountStudents() {
        final int countMinValue = 1;
        int countStudents;
        System.out.print("Enter the number of students: ");
        countStudents = readInt();
        while (countStudents < countMinValue) {
            System.out.print("The number of students must be greater than zero: ");
            countStudents = readInt();
        }
        return countStudents;
    }

    public int readShouldBeExpelled() {
        final int expelledMinValue = 1;
        int shouldBeExpelled;
        System.out.print("Enter the number of students who will be expelled: ");
        shouldBeExpelled = readInt();
        while (shouldBeExpelled < expelledMinValue) {
            System.out.print("The number of students who will be expelled must be greater than zero: ");
            shouldBeExpelled = readInt();
        }
        return shouldBeExpelled;
    }

    public long readTransferredStudents() {
        final int transMinValue = 1;
        long transferredStudents;
        System.out.print("Enter the number of transferred students: ");
        transferredStudents = readLong();
        while (transferredStudents < transMinValue) {
            System.out.print("The number of transferred students must be greater than zero: ");
            transferredStudents = readLong();
        }
        return transferredStudents;
    }

    public Coordinates readCoordinates() {
        final int xMinValue = -301;
        int x;
        Double y;
        System.out.print("Enter the X coordinate: ");
        x = readInt();
        while (x <= xMinValue) {
            System.out.print("The X coordinate must be greater than -301, try again: ");
            x = readInt();
        }
        System.out.print("Enter the Y coordinate: ");
        y = readDouble();
        return new Coordinates(x, y);
    }

    public FormOfEducation readFormOfEducation() {
        System.out.println("Choose the form of education:");
        int index = 1;
        for (FormOfEducation value : FormOfEducation.values()) {
            System.out.println(index + ". " + value);
            index++;
        }

        int choice;
        do {
            System.out.print("Enter the number of the selected form of study: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Enter a number!");
                System.out.print("Enter the number of the selected form of study: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer
        } while (choice < 1 || choice > FormOfEducation.values().length);

        return FormOfEducation.values()[choice - 1];
    }

    public Person readPerson() {
        String name;
        double weight;
        final int minWeight = 1;
        String passportID;

        System.out.print("Enter the student's name: ");
        name = readString();
        while (name == null || name.isEmpty()) {
            System.out.print("You have not entered the student's name, please try again: ");
            name = readString();
        }

        System.out.print("Enter the weight: ");
        weight = readDouble();
        while (weight < minWeight) {
            System.out.print("The weight must be greater than zero: ");
            weight = readDouble();
        }

        System.out.print("Enter passport ID: ");
        passportID = readString();
        while (passportID.length() <= 6) {
            System.out.print("Passport ID must contain more than 6 characters. Please try again: ");
            passportID = readString();
        }

        System.out.println("Enter the location coordinates:");
        System.out.print("Enter coordinate X: ");
        Double x = readDouble();
        System.out.print("Enter coordinate Y: ");
        Double y = readDouble();
        System.out.print("Enter coordinate Z: ");
        Double z = readDouble();
        System.out.print("Enter location name: ");
        String locationName = readString();
        while (locationName != null && locationName.length() >= 836) {
            System.out.print("Location name must be less than 836 characters. Please try again: ");
            locationName = readString();
        }

        Location location = new Location();
        location.setX(x);
        location.setY(y);
        location.setZ(z);
        location.setName(locationName);

        Person person = new Person();
        person.setName(name);
        person.setWeight(weight);
        person.setPassportID(passportID);
        person.setLocation(location);

        return person;
    }

    private String readString() {
        String value;
        while (true) {
            value = scanner.nextLine();
            if (value != null && !value.trim().isEmpty()) {
                break;
            }
            System.out.print("Input cannot be empty, please try again: ");
        }
        return value;
    }

    private int readInt() {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
        return value;
    }

    private long readLong() {
        long value;
        while (true) {
            try {
                value = Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a long integer: ");
            }
        }
        return value;
    }

    private double readDouble() {
        double value;
        while (true) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a double: ");
            }
        }
        return value;
    }
}
