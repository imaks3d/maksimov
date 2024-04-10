package data;
import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main class that stored in collection
 */

public class StudyGroup {
//private static Integer setId = 0;
private static final AtomicInteger nextId = new AtomicInteger(0);
//    @NotNull
//    @Min(1)
//    @Expose(serialize = false)
//    private final Integer id;
    @NotNull
    @Min(1)
    @Expose(serialize = false)
    private Integer id;//Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @Expose
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @Expose
    private Coordinates coordinates; //Поле не может быть null
    @NotNull
    @Expose
    private Location location;
    @NotNull
    @Expose
    private final LocalDateTime creationDateTime; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @Expose
    private int studentsCount; //Значение поля должно быть больше 0
    @NotNull
    @Expose
    private int shouldBeExpelled; //Значение поля должно быть больше 0
    @NotNull
    @Expose
    private long transferredStudents; //Значение поля должно быть больше 0
    @NotNull
    @Expose
    private FormOfEducation formOfEducation; //Поле не может быть null
    @NotNull
    @Expose
    private Person groupAdmin;
    public StudyGroup(String name, Coordinates coordinates, int studentsCount, int shouldBeExpelled, long transferredStudents, FormOfEducation formOfEducation, Person person) {
//        Id = ++setId;
        this.id = nextId.incrementAndGet();
        creationDateTime = LocalDateTime.now();
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.transferredStudents = transferredStudents;
        this.name = name;
        this.coordinates = coordinates;
        this.groupAdmin = person;
        this.formOfEducation = formOfEducation;
    }
//   public static void setNextId(Integer id) {
//      setId = id;
//   }

    /**
     * set object id
     */
    public void setId(int id) {
    this.id = id;
}


    /**
     * return id of StudyGroup
     * @return id of StudyGroup
     */
    public Integer getId() {
        return id;
    }
    /**
     * return name of StudyGroup
     * @return name of StudyGroup
     */
    public String getName() {
        return name;
    }

    /**
     *Return the number of students in the group
     * @return the number of students in the group
     */
    public int getStudentsCount() {
        return studentsCount;
    }
    /**
     * return coordinates of StudyGroup
     * @return coordinates of StudyGroup
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * return creation date of StudyGroup
     * @return creation date of StudyGroup
     */
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
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
     * return studyGroup represented by beautiful string
     * @return studyGroup represented by beautiful string
     */
    @Override
    public String toString() {
        return "Id = " + id + ", name = " + name + ", coordinates = " + coordinates + ", creation date = " + getCreationDateTime().toString() + ", count of students = " + studentsCount + ", should be expelled = " + shouldBeExpelled + ", transferred students = " + transferredStudents + ", form of education = " + formOfEducation.getDescription() +  ", GroupAdmin = " + groupAdmin;
    }
}

