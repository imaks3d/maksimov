package utility;

import data.StudyGroup;
import exceptions.StudyGroupValidateException;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
/**
 * Utility class that validate StudyGroup
 * This class should not be instantiated
 */
public final class StudyGroupValidator {
    private static final Map<Integer, Integer> ID_MAP;
    private static ValidatorFactory factory;
    private static Validator validator;

    static {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        ID_MAP = new HashMap<>();
    }

    private StudyGroupValidator() {
        throw new UnsupportedOperationException("This class should not be instantiated");
    }
    /**
     * validate input studyGroups using hibernate validator
     * @param studyGroups input routes to validate
     */
    public static void validateStudyGroup(StudyGroup... studyGroups) {
        Integer maxId = 1;
        for (StudyGroup studyGroup : studyGroups) {
            ID_MAP.put(studyGroup.getId(), ID_MAP.getOrDefault(studyGroup.getId(), 0) + 1);

            // Проверяем валидность группы и её атрибутов
            if (validator.validate(studyGroup).size() > 0 ||
                    validator.validate(studyGroup.getName()).size() > 0 ||
                    validator.validate(studyGroup.getCoordinates()).size() > 0 ||
                    ID_MAP.get(studyGroup.getId()) > 1) {
                throw new StudyGroupValidateException("В исходном JSON-файле содержатся ошибки");
            }

            // Находим максимальный ID группы
            maxId = Math.max(maxId, studyGroup.getId());
        }
        // Устанавливаем следующий доступный ID для группы
        StudyGroup.setNextId(maxId + 1);
    }
}