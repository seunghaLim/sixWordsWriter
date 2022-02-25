package toyProject.sixWordsWriter;

public class FailedLoginEx extends RuntimeException{
    public FailedLoginEx() {
        super();
    }

    public FailedLoginEx(String message) {
        super(message);
    }

    public FailedLoginEx(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedLoginEx(Throwable cause) {
        super(cause);
    }

    protected FailedLoginEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
