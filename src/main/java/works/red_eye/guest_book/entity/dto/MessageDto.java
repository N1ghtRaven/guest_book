package works.red_eye.guest_book.entity.dto;

import lombok.*;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class MessageDto {
    private long id;
    private String username;
    private String message;
    private String color;
    private long timestamp;
}
