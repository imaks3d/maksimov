package data;
import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
/**
 * The Person class is represented by the name height weight passport Id and location
 */
public class Person {
        @NotNull
        @Expose
        private final String name; //Поле не может быть null, Строка не может быть пустой
        @Expose
        private final Long height; //Поле может быть null, Значение поля должно быть больше 0
        @Expose
        private final double weight; //Значение поля должно быть больше 0
        @Expose
        private final String passportID; //Длина строки должна быть не меньше 5, Поле может быть null
        @Expose
        private final Location location; //Поле не может быть null
public Person(String name, Long height, double weight, String passportID, Location location){
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.passportID = passportID;
        this.location = location;
        }
        public String getName() {
                return name;
        }
        public Long getHeight() {
                return height;
        }
        public double getWeight() {
                return weight;
        }
        public String getPassportID() {
        return passportID;
        }
        public Location getLocation() {
                return location;
        }
        @Override
        public String toString() {
                String s = location.toString();
                return "{" + "имя: " + name + ", " + "рост: " + height + ", " + "вес: " + weight + ", " + "ID паспорта: " + passportID + ", "+ "локация: " + s + "}";
        }
}
