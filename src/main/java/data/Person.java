package data;

public class Person {
        private String name; //Поле не может быть null, Строка не может быть пустой
        private Double weight; //Поле может быть null, Значение поля должно быть больше 0
        private String passportID; //Строка не может быть пустой, Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 7, Поле не может быть null
        private  Location location; //Поле не может быть null
        public Person(String name, Double weight, String passportID) {
                if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be null or empty");
                }
                if (passportID == null || passportID.length() < 7) {
                        throw new IllegalArgumentException("Passport ID must be at least 7 characters long and not null");
                }
                if (weight != null && weight <= 0) {
                        throw new IllegalArgumentException("Weight must be greater than 0");
                }
                this.name = name;
                this.weight = weight;
                this.passportID = passportID;
                this.location = new Location(); // This needs to be properly set
        }

        public Person(){
        }

        public String getName() {
                return this.name;
        }


        public Double getWeight() {
                return this.weight;
        }

        public String getPassportID() {
                return this.passportID;
        }

        public void setName(String name) {
                if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be null or empty");
                }
                this.name = name;
        }

        public void setPassportID(String passportID) {
                if (passportID == null || passportID.length() < 6) {
                        throw new IllegalArgumentException("Passport ID must be at least 6 characters long and not null");
                }
                this.passportID = passportID;
        }


        public void setWeight(Double weight) {
                this.weight = weight;
        }

        public Location getLocation() {
                return this.location;
        }

        public void setLocation(Location location) {
                this.location = location;
        }
        @Override
        public String toString() {
                return "Person{" +
                        "name='" + name + '\'' +
                        ", weight=" + weight +
                        ", passportID='" + passportID + '\'' +
                        ", location=" + location +
                        '}';
        }
}