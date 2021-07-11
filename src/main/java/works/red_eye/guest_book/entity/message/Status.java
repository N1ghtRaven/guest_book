package works.red_eye.guest_book.entity.message;

public enum Status {
    New("Новое"),
    Approved("Одобрено"),
    Decline("Отклонено");

    private final String displayValue;
    Status(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
