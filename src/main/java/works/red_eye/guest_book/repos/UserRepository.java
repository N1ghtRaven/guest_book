package works.red_eye.guest_book.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import works.red_eye.guest_book.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);
}
