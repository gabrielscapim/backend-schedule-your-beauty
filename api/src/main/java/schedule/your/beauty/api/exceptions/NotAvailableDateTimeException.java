package schedule.your.beauty.api.exceptions;

public class NotAvailableDateTimeException extends RuntimeException {
    public NotAvailableDateTimeException(String message) {
        super(message);
    }
}
