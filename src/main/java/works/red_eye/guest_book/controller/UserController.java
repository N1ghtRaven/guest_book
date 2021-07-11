package works.red_eye.guest_book.controller;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import works.red_eye.guest_book.entity.dto.ContainerDto;
import works.red_eye.guest_book.entity.user.User;
import works.red_eye.guest_book.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
        return "user/index";
    }

    @RequestMapping(value="/add", method= RequestMethod.GET)
    public String add() {
        return "user/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String add(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("role") String role ) {

        User userInDb = userService.add(username, password, role);
        if (userInDb == null) {
            throw new UsernameNotFoundException(username);
        }

        return "redirect:/user/view?id=" + userInDb.getId();
    }

    @RequestMapping(value="/remove", method= RequestMethod.GET)
    public String remove(Model model) {
        List<ContainerDto> users = ImmutableList.copyOf(userService.get()).stream().map((user) -> (new ContainerDto(user.getId(), user.getUsername()))).collect(Collectors.toList());

        model.addAttribute("users", users);
        return "user/remove";
    }

    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public String remove(@RequestParam("id") long id) {
        if (!userService.remove(id)) {
            throw new UsernameNotFoundException("User with id " + id + " not found.");
        }

        return "redirect:/user/remove";
    }

    @RequestMapping(value="/get", method=RequestMethod.GET)
    public String get(Model model) {
        List<ContainerDto> users = ImmutableList.copyOf(userService.get()).stream().map((user) -> (new ContainerDto(user.getId(), user.getUsername()))).collect(Collectors.toList());

        model.addAttribute("users", users);
        return "user/get";
    }

    @RequestMapping(value="/view", method=RequestMethod.GET)
    public String view(@RequestParam("id") long id, Model model) {
        User user = userService.get(id);

        if (user == null) {
            throw new UsernameNotFoundException("User with id " + id + " not found.");
        }

        model.addAttribute("user", user);
        return "user/view";
    }

}
