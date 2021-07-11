package works.red_eye.guest_book.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MODER("Moderator"),
    ADMIN("Admin");

    private final String displayValue;

    Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}