package works.red_eye.guest_book.entity.message;

public enum Color {
    Red("Угличский индустриально-педагогический коллежд"),
    Green("Зелёный"),
    Blue("Синий"),
    Yellow("Желтый");

    private final String displayValue;
    Color(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
