package works.red_eye.guest_book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import works.red_eye.guest_book.entity.message.Message;
import works.red_eye.guest_book.entity.message.Status;
import works.red_eye.guest_book.entity.user.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findById(long id);
    List<Message> findAllByStatus(Status status);
}
