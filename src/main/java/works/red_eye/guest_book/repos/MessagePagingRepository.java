package works.red_eye.guest_book.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import works.red_eye.guest_book.entity.message.Message;
import works.red_eye.guest_book.entity.message.Status;

public interface MessagePagingRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findAllByStatus(Pageable pageable, Status status);
}
