package data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
/**
 * Main class that stored in collection
 */

public class StudyGroup {
private static Integer nextId = 1;
    @NotNull
    @Min(1)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Coordinates coordinates; //Поле не может быть null
    @NotNull
    private Location location;
    @NotNull
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    private int studentsCount; //Значение поля должно быть больше 0
    @NotNull
    private int shouldBeExpelled; //Значение поля должно быть больше 0
    @NotNull
    private long transferredStudents; //Значение поля должно быть больше 0
    @NotNull
    private FormOfEducation formOfEducation; //Поле не может быть null
    @NotNull
    private Person groupAdmin;
    public StudyGroup(String name, Coordinates coordinates, int studentsCount, int shouldBeExpelled, long transferredStudents, FormOfEducation formOfEducation, Person person) {
        id = nextId++;
        creationDate = LocalDate.now();
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.transferredStudents = transferredStudents;
        this.name = name;
        this.coordinates = coordinates;
        this.groupAdmin = person;
        this.formOfEducation = formOfEducation;
    }
   public static void setNextId(Integer id) {
      nextId = id;
   }
    /**
     * @return id of StudyGroup
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return name of StudyGroup
     */
    public String getName() {
        return name;
    }
    /**
     * @return coordinates of StudyGroup
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * @return creation date of StudyGroup
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }
    /**
     * replace current studyGroup by new information
     * @param studyGroup studyGroup with new information
     */
    public void update(StudyGroup studyGroup) {
        name = studyGroup.name;
        coordinates = studyGroup.coordinates;
        location = studyGroup.location;
        studentsCount = studyGroup.studentsCount;
        shouldBeExpelled = studyGroup.shouldBeExpelled;
        transferredStudents = studyGroup.shouldBeExpelled;
        groupAdmin = studyGroup.groupAdmin;
        formOfEducation = studyGroup.formOfEducation;
    }
    /**
     * @return studyGroup represented by beautiful string
     */
    @Override
    public String toString() {
        return "Id = " + id + ", name = " + name + ", coordinates = " + coordinates + ", creation date = " + getCreationDate().toString() + ", count of students = " + studentsCount + ", should be expelled = " + shouldBeExpelled + ", transferred students = " + transferredStudents + ", form of education = " + formOfEducation.getDescription() +  ", GroupAdmin = " + groupAdmin;
    }
}

