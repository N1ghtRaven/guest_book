package works.red_eye.guest_book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import works.red_eye.guest_book.entity.user.Role;
import works.red_eye.guest_book.entity.user.User;
import works.red_eye.guest_book.repos.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User get(long id) {
        return userRepo.findById(id);
    }
    public User get(String username) {
        return userRepo.findByUsername(username);
    }
    public Iterable<User> get() {
        return userRepo.findAll();
    }

    public User add(String username, String password, String role) {
        if (userRepo.findByUsername(username) != null) {
            return null;
        }

        Role userRole = Role.valueOf(role);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRoles(Collections.singleton(userRole));
        user.setActive(true);

        return userRepo.save(user);
    }
    
    public User add(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return null;
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public boolean remove(long id) {
        User userFromDb = userRepo.findById(id);
        if (userFromDb != null) {
            return false;
        }

        userRepo.delete(userFromDb);
        return true;
    }

}
