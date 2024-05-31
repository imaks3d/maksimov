package commands;

import data.StudyGroup;
import dataBase.DataBase;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Optional;

public class MinByCoordinatesCommand extends GenericCommand {
    private final DataBase db;

    public MinByCoordinatesCommand(PrintStream printStream, DataBase db) {
        super(printStream);
        this.db = db;
    }

    @Override
    public void Execute() {
        Optional<StudyGroup> minStudyGroup = db.getStudyGroups().stream()
                .min(Comparator.comparing(StudyGroup::getCoordinates));

        if (minStudyGroup.isPresent()) {
            printStream.println("StudyGroup with minimum coordinates: " + minStudyGroup.get());
        } else {
            printStream.println("The collection is empty, no StudyGroup to display.");
        }
    }

    @Override
    public String Description() {
        return "Displays the StudyGroup with the minimum coordinates.";
    }
}
