package works.red_eye.guest_book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import works.red_eye.guest_book.entity.message.Color;
import works.red_eye.guest_book.entity.message.Message;
import works.red_eye.guest_book.exception.MessageNotFoundException;
import works.red_eye.guest_book.service.MessageService;

@Controller
@RequestMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class GuestUserController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String index() {
        return "general/index";
    }

    @RequestMapping(value="/send", method=RequestMethod.GET)
    public String send() {
        return "general/send";
    }

    @RequestMapping(value="/send", method=RequestMethod.POST)
    public String send(@RequestParam("username") String username,
                      @RequestParam("message") String message,
                      @RequestParam("color") Color color) {

        Message messageFromDb = messageService.add(username, message, color);
        if (messageFromDb == null) {
            throw new MessageNotFoundException();
        }

        return "general/send";
    }
}
