package сommands;

import com.google.gson.*;
import data.StudyGroup;
import exceptions.FileReadPermissionException;
import exceptions.StudyGroupValidateException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
/**
 * Class that operates all files
 */
public class FileManager {
    private final File file;

    public FileManager(File file) throws FileNotFoundException {
        this.file = file;
        if (!file.exists()) {
            throw new FileNotFoundException("Файла с таким названием не существует");
        }
        if (file.isDirectory()) {
            throw new FileNotFoundException("По введенному пути находится директория, а не файл");
        }
        if (!file.canRead()) {
            throw new FileReadPermissionException("Нет прав для чтения файла");
        }
    }
    /**
     * turn input file to list of string contains files insides
     * @return list of string
     * @throws FileNotFoundException if file doesn't exist
     */
    public List<String> fileToStringList() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        return list;
    }

    /**
     * reads and return studyGroup from json file using JsonParser utility class
     * @return list of studyGroups from file
     */
    public List<StudyGroup> readElementsFromFile() throws FileNotFoundException {
        StringBuilder inputArray = new StringBuilder();
        for (String string : fileToStringList()) {
            inputArray.append(string);
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonPrimitive) -> {
            try {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
            } catch (DateTimeParseException e) {
                // Логирование ошибки парсинга даты
                System.err.println("Ошибка парсинга даты: " + e.getMessage());
                return null;
            }
        }).create();
        try {
            StudyGroup[] studyGroups = gson.fromJson(inputArray.toString(), StudyGroup[].class);
            if (studyGroups != null) {
                return new ArrayList<>(Arrays.asList(studyGroups));
            } else {
                // Логирование, если studyGroups равен null
                System.err.println("studyGroups равен null");
                return new ArrayList<>(); // Возвращаем пустой список, чтобы избежать NullPointerException
            }
        } catch (JsonSyntaxException | NumberFormatException e) {
            // Логирование ошибки синтаксиса JSON или ошибки преобразования номера
            System.err.println("Ошибка синтаксиса JSON или ошибки преобразования номера: " + e.getMessage());
            throw new StudyGroupValidateException("В исходном JSON-файле содержатся ошибки");
        }
    }
    /**
     * save collection to json file
     * @param set collection to save
     */
//    public void saveToFile(Set<StudyGroup> set) {
//        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, jsonSerializationContext) -> {
//            if (date != null) {
//                return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
//            } else {
//                return JsonNull.INSTANCE;
//            }
//        }).setPrettyPrinting().create();
        public void saveToFile(List<StudyGroup> set) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, jsonSerializationContext) -> {
                        if (date != null) {
                            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
                        } else {
                            return JsonNull.INSTANCE;
                        }
                    })
                    .setPrettyPrinting()
                    .create();

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            String toPrint = gson.toJson(set);
            out.write(toPrint.getBytes());
            System.out.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            System.out.println("Нет прав записи в файл. Коллекция не сохранена.");
        }
    }
}
