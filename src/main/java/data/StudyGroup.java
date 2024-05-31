package data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main class that stored in collection
 */
public class StudyGroup implements Comparable<StudyGroup> {
    private static final AtomicInteger nextId = new AtomicInteger(0);
    @NotNull
    @Min(1)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Coordinates coordinates;
    @NotNull
    private Location location;
    @NotNull
    private Date creationDateTime;
    @NotNull
    private int studentsCount;
    @NotNull
    private int shouldBeExpelled;
    @NotNull
    private long transferredStudents;
    @NotNull
    private FormOfEducation formOfEducation;
    @NotNull
    private Person groupAdmin;
    private String user_name;

    public StudyGroup(String name, Coordinates coordinates, int studentsCount, int shouldBeExpelled, long transferredStudents, FormOfEducation formOfEducation, Person person) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        if (studentsCount <= 0) {
            throw new IllegalArgumentException("Students count must be greater than 0");
        }
        if (shouldBeExpelled <= 0) {
            throw new IllegalArgumentException("Should be expelled must be greater than 0");
        }
        if (transferredStudents <= 0) {
            throw new IllegalArgumentException("Transferred students must be greater than 0");
        }
        if (formOfEducation == null) {
            throw new IllegalArgumentException("Form of education cannot be null");
        }
        if (person == null) {
            throw new IllegalArgumentException("Group admin cannot be null");
        }
        this.id = nextId.incrementAndGet();
        this.creationDateTime = new Date();
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.transferredStudents = transferredStudents;
        this.formOfEducation = formOfEducation;
        this.groupAdmin = person;
    }

    public StudyGroup() {}

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getTransferredStudents() {
        return transferredStudents;
    }

    public int getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setTransferredStudents(long transferredStudents) {
        this.transferredStudents = transferredStudents;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setStudentsExpelled(int shouldBeExpelled) {
        this.shouldBeExpelled = shouldBeExpelled;
    }

    @Override
    public String toString() {
        return "Id = " + id + ", name = " + name + ", coordinates = " + coordinates + ", creation date = " + getCreationDateTime().toString() + ", count of students = " + studentsCount + ", should be expelled = " + shouldBeExpelled + ", transferred students = " + transferredStudents + ", form of education = " + formOfEducation.getDescription() +  ", GroupAdmin = " + groupAdmin;
    }

    @Override
    public int compareTo(StudyGroup other) {
        return Integer.compare(this.studentsCount, other.studentsCount);
    }
}
