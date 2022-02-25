package toyProject.sixWordsWriter;

public class NoAuthorizationEx extends RuntimeException{
    public NoAuthorizationEx() {
        super();
    }

    public NoAuthorizationEx(String message) {
        super(message);
    }

    public NoAuthorizationEx(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAuthorizationEx(Throwable cause) {
        super(cause);
    }

    protected NoAuthorizationEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
