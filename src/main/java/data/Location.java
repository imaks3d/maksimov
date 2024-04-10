package data;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
/**
 * Location of StudyGroup
 */
public class Location {
    @NotNull
    @Expose
    private Long x; //Поле не может быть null
    @NotNull
    @Expose
    private Double y; //поле не может быть null
    @NotNull
    @Expose
    private double z; //Поле не может быть null
    @NotNull
    @Expose
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Location(Long x, Double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    /**
     * return x y z coordinates of Location
     * @return x y z coordinates of Location
     */
        @Override
        public String toString() {return "{" + x + ", " + y + ", " + z + ", название: " + name + "}";
    }
}