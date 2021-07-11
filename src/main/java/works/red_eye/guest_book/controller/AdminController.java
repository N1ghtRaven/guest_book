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
import works.red_eye.guest_book.entity.message.Color;
import works.red_eye.guest_book.entity.message.Message;
import works.red_eye.guest_book.entity.message.Status;
import works.red_eye.guest_book.exception.MessageNotFoundException;
import works.red_eye.guest_book.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String index() {
        return "admin/index";
    }

    @RequestMapping(value="/add", method= RequestMethod.GET)
    public String add() {
        return "admin/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String add(@RequestParam("username") String username,
                      @RequestParam("message") String message,
                      @RequestParam("color") Color color) {

        Message messageFromDb = messageService.add(username, message, color);
        if (messageFromDb == null) {
            throw new MessageNotFoundException();
        }

        return "redirect:/admin/view?id=" + messageFromDb.getId();
    }

    @RequestMapping(value="/remove", method= RequestMethod.GET)
    public String remove(Model model) {
        List<ContainerDto> messages = ImmutableList.copyOf(messageService.get()).stream().map((message) -> (new ContainerDto(message.getId(), message.getMessage()))).collect(Collectors.toList());

        model.addAttribute("messages", messages);
        return "admin/remove";
    }

    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public String remove(@RequestParam("id") long id) {
        if (!messageService.remove(id)) {
            throw new MessageNotFoundException(id);
        }

        return "redirect:/admin/remove";
    }

    @RequestMapping(value="/get", method=RequestMethod.GET)
    public String get(Model model) {
        List<ContainerDto> messages = ImmutableList.copyOf(messageService.get()).stream().map((message) -> (new ContainerDto(message.getId(), message.getMessage()))).collect(Collectors.toList());

        model.addAttribute("messages", messages);
        return "admin/get";
    }

    @RequestMapping(value="/change_status", method= RequestMethod.GET)
    public String changeStatus(Model model) {
        List<ContainerDto> messages = ImmutableList.copyOf(messageService.get()).stream().map((message) -> (new ContainerDto(message.getId(), message.getMessage()))).collect(Collectors.toList());

        model.addAttribute("messages", messages);
        return "admin/change_status";
    }

    @RequestMapping(value="/change_status", method=RequestMethod.POST)
    public String changeStatus(@RequestParam("id") long id, @RequestParam("status") Status status) {
        if (!messageService.updateStatus(id, status)) {
            throw new MessageNotFoundException(id);
        }

        return "redirect:/admin/view?id=" + id;
    }

    @RequestMapping(value="/view", method=RequestMethod.GET)
    public String view(@RequestParam("id") long id, Model model) {
        Message message = messageService.get(id);
        if (message == null) {
            throw new UsernameNotFoundException("User with id " + id + " not found.");
        }

        model.addAttribute("message", message);
        return "admin/view";
    }
}
