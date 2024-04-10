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
        /**
         *return a string representing the name
         *@return a string representing the name
         */
        public String getName() {
                return name;
        }
        /**
         *return a string representing the height
         *@return a string representing the height
         */
        public Long getHeight() {
                return height;
        }
        /**
         *return a string representing the weight
         *@return a string representing the weight
         */
        public double getWeight() {
                return weight;
        }
        /**
         *return a string representing the passport ID
         * @return a string representing the passport ID
         */
        public String getPassportID() {
        return passportID;
        }
        /**
         *return a string representing the Location
         * @return a string representing the Location
         */
        public Location getLocation() {
                return location;
        }

        /**
         *represent an object as a string
         * @return an object as a string
         */
        @Override
        public String toString() {
                String s = location.toString();
                return "{" + "имя: " + name + ", " + "рост: " + height + ", " + "вес: " + weight + ", " + "ID паспорта: " + passportID + ", "+ "локация: " + s + "}";
        }
}
