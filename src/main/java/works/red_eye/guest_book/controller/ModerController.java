package works.red_eye.guest_book.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/moderate", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModerController {
    @GetMapping
    public String index() {
        return "general/moder";
    }
}
