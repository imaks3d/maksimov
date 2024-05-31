package commands;

import dataBase.DataBase;

import java.io.PrintStream;

public class CountLessThanTransferredStudentsCommand extends GenericCommand {
    private final DataBase db;
    private long transferredStudents;

    public CountLessThanTransferredStudentsCommand(PrintStream printStream, DataBase db) {
        super(printStream);
        this.db = db;
    }

    @Override
    public void Execute() {
        long count = db.getStudyGroups().stream()
                .filter(studyGroup -> studyGroup.getTransferredStudents() < transferredStudents)
                .count();

        printStream.println("Number of StudyGroups with transferredStudents less than " + transferredStudents + ": " + count);
    }

    @Override
    public boolean VerifyInputParameters(String[] tokens) {
        if (tokens.length != 2) {
            printStream.println("Usage: count_less_than_transferred_students <transferredStudents>");
            return false;
        }
        try {
            transferredStudents = Long.parseLong(tokens[1]);
            return true;
        } catch (NumberFormatException e) {
            printStream.println("The argument must be a valid number.");
            return false;
        }
    }

    @Override
    public String Description() {
        return "Displays the number of StudyGroups with transferredStudents less than a specified value.";
    }
}
