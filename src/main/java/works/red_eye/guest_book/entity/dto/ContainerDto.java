package works.red_eye.guest_book.entity.dto;

import lombok.Getter;

@Getter
public class ContainerDto {
    private final long id;
    private final String value;

    public ContainerDto(long id, String value) {
        this.id = id;
        this.value = value;
    }
}
