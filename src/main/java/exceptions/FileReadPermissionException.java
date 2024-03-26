package exceptions;

/**
 * exception class shows that file cannot be read
 */
public class FileReadPermissionException extends RuntimeException {
    public FileReadPermissionException(String message) {
        super(message);
    }
}