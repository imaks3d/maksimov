package exceptions;
/**
 * exception class shows that studyGroup to validate contains error
 */

public class StudyGroupValidateException extends RuntimeException {
    public StudyGroupValidateException(String message) {
        super(message);
    }
}