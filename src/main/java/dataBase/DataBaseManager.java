package dataBase;

import commands.PasswordManager;
import data.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataBaseManager {
    private Connection connection;

    public DataBaseManager(String url, Properties info) {
        try {
            this.connection = DriverManager.getConnection(url, info);
            this.connection.setAutoCommit(true);
            System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error with connection to DataBase: " + e.getMessage());
        }
    }

    public void initDataBase() {
        this.createUserTable();
        this.createStudyGroupTable();
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (" +
                "user_name TEXT PRIMARY KEY, " +
                "password TEXT);";
        executeUpdate(sql, "User table - successfully.");
    }

    public void createStudyGroupTable() {
        this.createIdSeq();
        try {
            Statement statement = this.connection.createStatement(1000, 1000);
            String sql = "CREATE TABLE IF NOT EXISTS STUDYGROUP (" +
                    "id INT PRIMARY KEY DEFAULT nextval('ID_SEQ'), " +
                    "name VARCHAR(255) NOT NULL CHECK (name <> ''), " +
                    "study_x INT NOT NULL CHECK (study_x > (-301)), " +
                    "study_y DOUBLE PRECISION NOT NULL, " +
                    "creationDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "countStudents INT CHECK (countStudents > 0), " +
                    "expelled INT CHECK (expelled > 0), " +
                    "transfer DOUBLE PRECISION CHECK (transfer > 0), " +
                    "forms TEXT NOT NULL, " +
                    "person TEXT NOT NULL, " +
                    "weight DOUBLE PRECISION CHECK (weight > 0), " +
                    "passport TEXT NOT NULL CHECK (length(passport) > 6), " +
                    "local_x DOUBLE PRECISION NOT NULL, " +
                    "local_y DOUBLE PRECISION NOT NULL, " +
                    "local_z DOUBLE PRECISION NOT NULL, " +
                    "local_name TEXT CHECK (length(local_name) < 836), " +
                    "user_name TEXT, " +
                    "FOREIGN KEY (user_name) REFERENCES users(user_name));";
            statement.executeUpdate(sql);
            statement.close();
            executeUpdate(sql, "StudyGroup table - successfully.");
        } catch (SQLException var3) {
            System.out.println("Error sending the request");
        }
    }

    private void createIdSeq() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS ID_SEQ START WITH 1 INCREMENT BY 1;";
        executeUpdate(sql, "ID_SEQ - successfully.");
    }

    public boolean checkUser(String user_name) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) AS count FROM users WHERE user_name = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user_name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    if (count > 0) {
                        exists = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error with sql");
            e.printStackTrace();
        }
        return exists;
    }

    public void registerUser(String user_name, String pswd) {
        String sql = "INSERT INTO users (user_name, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, pswd);
            preparedStatement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user registration");
            e.printStackTrace();
        }
    }

    public boolean checkPassword(String user_name, String pswd) {
        String sql = "SELECT password FROM users WHERE user_name = ?";
        try (PreparedStatement prepareStatement = this.connection.prepareStatement(sql)) {
            prepareStatement.setString(1, user_name);
            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    String hashedInputPassword = PasswordManager.hashPassword(pswd);
                    return hashedInputPassword.equals(hashedPassword);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ArrayList<String> getUsers() {
        String sql = "SELECT user_name FROM users;";
        ArrayList<String> users = new ArrayList<>();
        try (PreparedStatement prepareStatement = this.connection.prepareStatement(sql);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(resultSet.getString("user_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (users.isEmpty()) {
            users.add("There are no users yet");
        }
        return users;
    }

    private void executeUpdate(String sql, String successMessage) {
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println(successMessage);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing update: " + e.getMessage());
            System.out.println("SQL Query: " + sql); // Вывод SQL-запроса при ошибке
        }
    }

    public LinkedHashSet<StudyGroup> readFromDataBase() {
        LinkedHashSet<StudyGroup> studyGroups = new LinkedHashSet<>();
        try {
        String sql = "SELECT id, name, study_x, study_y, creationDateTime, countStudents, expelled, transfer, forms, person, weight, passport, local_x, local_y, local_z, local_name, user_name FROM STUDYGROUP ORDER BY id ASC";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                StudyGroup studyGroup = new StudyGroup();
                Coordinates coordinates = new Coordinates(); // Using no-argument constructor
                Person person = new Person();
                Location location = new Location();
                studyGroup.setId(rs.getInt("id"));
                IdManager.AddId(rs.getInt("id"));
                studyGroup.setName(rs.getString("name"));
                coordinates.setX(rs.getInt("study_x"));
                coordinates.setY(rs.getDouble("study_y"));
                studyGroup.setCoordinates(coordinates);
                studyGroup.setCreationDateTime(rs.getTimestamp("creationDateTime"));
                studyGroup.setStudentsCount(rs.getInt("countStudents"));
                studyGroup.setStudentsExpelled(rs.getInt("expelled"));
                studyGroup.setTransferredStudents(rs.getLong("transfer"));
                studyGroup.setFormOfEducation(FormOfEducation.valueOf(rs.getString("forms")));
                person.setName(rs.getString("person"));
                person.setWeight(rs.getDouble("weight"));
                person.setPassportID(rs.getString("passport"));
                location.setX(rs.getDouble("local_x"));
                location.setY(rs.getDouble("local_y"));
                location.setZ(rs.getDouble("local_z"));
                location.setName(rs.getString("local_name"));
                person.setLocation(location);
                studyGroup.setGroupAdmin(person);
                studyGroup.setUser_name(rs.getString("user_name"));
                studyGroups.add(studyGroup);
            }
                rs.close();
                preparedStatement.close();
            } catch (SQLException var8) {
                var8.printStackTrace();
            }

            return studyGroups;
        }

        public Connection getConnection() {
        return connection;
    }

    private static String getValue(StudyGroup studyGroup) {
        String value = "INSERT INTO StudyGroup(id, name, study_x, study_y, creationDateTime, countStudents, expelled, transfer, forms, person, weight, passport, local_x, local_y, local_z, local_name, user_name) VALUES";
        value = value + "(" + studyGroup.getId() + ",'" + studyGroup.getName() + "'," + studyGroup.getCoordinates().getX() + "," + studyGroup.getCoordinates().getY() + ",'" + studyGroup.getCreationDateTime() + "'," + studyGroup.getStudentsCount() + "," + studyGroup.getShouldBeExpelled() + ","  + studyGroup.getTransferredStudents() + ",'" + studyGroup.getFormOfEducation() + "','" + studyGroup.getGroupAdmin().getName()+ "'," + studyGroup.getGroupAdmin().getWeight() + ",'" + studyGroup.getGroupAdmin().getPassportID()+"',"+ studyGroup.getGroupAdmin().getLocation().getX()+","+studyGroup.getGroupAdmin().getLocation().getY()+ "," + studyGroup.getGroupAdmin().getLocation().getZ() + ",'" +studyGroup.getGroupAdmin().getLocation().getName()+"','"+studyGroup.getUser_name()+"');";
        return value;
    }
//    public void saveToDataBase(LinkedHashSet<StudyGroup> studyGroups, String user_name) {
//        String deleteSql = "DELETE FROM STUDYGROUP WHERE user_name = ?;";
//
//        try (PreparedStatement deleteStatement = this.connection.prepareStatement(deleteSql)) {
//            deleteStatement.setString(1, user_name);
//            deleteStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error deleting existing study groups for user: " + e.getMessage());
//        }
//
//        String insertSql = "INSERT INTO STUDYGROUP (id, name, study_x, study_y, creationDateTime, countStudents, expelled, transfer, forms, person, weight, passport, local_x, local_y, local_z, local_name, user_name) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//
//        try (PreparedStatement insertStatement = this.connection.prepareStatement(insertSql)) {
//            for (StudyGroup studyGroup : studyGroups) {
//                if (studyGroup.getUser_name().equals(user_name) && !studyGroup.getUser_name().isEmpty()) {
//                    // Проверка всех ограничений
//                    if (!validateStudyGroup(studyGroup)) {
//                        continue;
//                    }
//
//                    // Добавление данных для вставки
//                    insertStatement.setInt(1, studyGroup.getId());
//                    insertStatement.setString(2, studyGroup.getName());
//                    insertStatement.setInt(3, studyGroup.getCoordinates().getX());
//                    insertStatement.setDouble(4, studyGroup.getCoordinates().getY());
//                    insertStatement.setTimestamp(5, new java.sql.Timestamp(studyGroup.getCreationDateTime().getTime()));
//                    insertStatement.setInt(6, studyGroup.getStudentsCount());
//                    insertStatement.setInt(7, studyGroup.getShouldBeExpelled());
//                    insertStatement.setDouble(8, studyGroup.getTransferredStudents());
//                    insertStatement.setString(9, studyGroup.getFormOfEducation().name());
//                    insertStatement.setString(10, studyGroup.getGroupAdmin().getName());
//                    insertStatement.setDouble(11, studyGroup.getGroupAdmin().getWeight());
//                    insertStatement.setString(12, studyGroup.getGroupAdmin().getPassportID());
//                    insertStatement.setDouble(13, studyGroup.getGroupAdmin().getLocation().getX());
//                    insertStatement.setDouble(14, studyGroup.getGroupAdmin().getLocation().getY());
//                    insertStatement.setDouble(15, studyGroup.getGroupAdmin().getLocation().getZ());
//                    insertStatement.setString(16, studyGroup.getGroupAdmin().getLocation().getName());
//                    insertStatement.setString(17, studyGroup.getUser_name());
//                    insertStatement.addBatch(); // Добавляем в батч
//                }
//            }
//            insertStatement.executeBatch(); // Выполняем все вставки
//            System.out.println("GROUP IS SAVED");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error saving study groups to database: " + e.getMessage());
//        }
//    }
//public void saveToDataBase(LinkedHashSet<StudyGroup> studyGroups, String user_name) {
//    StringBuilder sql = new StringBuilder("DELETE FROM StudyGroup WHERE user_name = ?;");
//    Iterator var5 = studyGroups.iterator();
//
//    while(var5.hasNext()) {
//        StudyGroup studyGroup = (StudyGroup) var5.next();
//        if (studyGroup.getUser_name().equals(user_name) && !studyGroup.getUser_name().isEmpty()) {
//            String value = getValue(studyGroup);
//            sql.append(value);
//        }
//    }
//
//    try {
//        PreparedStatement prepareStatement = this.connection.prepareStatement(sql.toString());
//        prepareStatement.setString(1, user_name);
////        prepareStatement.executeQuery();
//        prepareStatement.executeUpdate();
//        prepareStatement.close();
//    } catch (SQLException var8) {
//        System.out.println("save was successfully");
//    }
//
//}
public void saveToDataBase(LinkedHashSet<StudyGroup> studyGroups, String user_name) {
    // Начальный SQL запрос для удаления записей
    StringBuilder sql = new StringBuilder("DELETE FROM StudyGroup WHERE user_name = ?;");

    // Итератор для обхода коллекции studyGroups
    Iterator<StudyGroup> iterator = studyGroups.iterator();

    // Построение SQL запроса для вставки новых записей
    while (iterator.hasNext()) {
        StudyGroup studyGroup = iterator.next();

        // Проверка имени пользователя
        if (studyGroup.getUser_name().equals(user_name) && !studyGroup.getUser_name().isEmpty()) {
            String value = getValue(studyGroup);
            sql.append(value);
        }
    }

    PreparedStatement preparedStatement = null;
    try {
        // Подготовка SQL запроса
        preparedStatement = this.connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, user_name);

        // Выполнение запроса
        preparedStatement.executeUpdate();
        System.out.println("save was successfully");
    } catch (SQLException e) {
        // Вывод сообщения об ошибке
        System.out.println("An error occurred while saving: " + e.getMessage());
    } finally {
        // Закрытие preparedStatement в блоке finally
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // Вывод сообщения об ошибке при закрытии
                System.out.println("An error occurred while closing the statement: " + e.getMessage());
            }
        }
    }
}


    private boolean validateStudyGroup(StudyGroup studyGroup) {
        if (studyGroup.getName() == null || studyGroup.getName().isEmpty()) {
            System.out.println("Error: Study group name cannot be empty.");
            return false;
        }
        if (studyGroup.getCoordinates().getX() <= (-301)) {
            System.out.println("Error: study_x must be greater than -301.");
            return false;
        }
        if (studyGroup.getCoordinates().getY() <= 0) {
            System.out.println("Error: study_y must be greater than 0.");
            return false;
        }
        if (studyGroup.getStudentsCount() <= 0) {
            System.out.println("Error: countStudents must be greater than 0.");
            return false;
        }
        if (studyGroup.getShouldBeExpelled() <= 0) {
            System.out.println("Error: expelled must be greater than 0.");
            return false;
        }
        if (studyGroup.getTransferredStudents() <= 0) {
            System.out.println("Error: transfer must be greater than 0.");
            return false;
        }
        if (studyGroup.getGroupAdmin().getWeight() <= 0) {
            System.out.println("Error: weight must be greater than 0.");
            return false;
        }
        if (studyGroup.getGroupAdmin().getPassportID().length() < 7) {
            System.out.println("Error: Passport ID length must be greater than 6.");
            return false;
        }
        if (studyGroup.getGroupAdmin().getLocation().getName() != null && studyGroup.getGroupAdmin().getLocation().getName().length() >= 836) {
            System.out.println("Error: Local name length must be less than 836.");
            return false;
        }
        return true;
    }
}