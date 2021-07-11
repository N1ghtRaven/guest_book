package works.red_eye.guest_book.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException() {
        super("Some error while creation message.");
    }
    public MessageNotFoundException(long id) {
        super("Message with id = " + id + ", not found.");
    }
}
