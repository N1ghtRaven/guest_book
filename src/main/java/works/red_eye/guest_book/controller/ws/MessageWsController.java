package works.red_eye.guest_book.controller.ws;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import works.red_eye.guest_book.entity.dto.MessageDto;
import works.red_eye.guest_book.entity.message.Status;
import works.red_eye.guest_book.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageWsController {
    @Autowired
    private MessageService messageService;

    // TODO: Fix me
    private final static int MESSAGE_ON_PAGE = 2;

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public String load(@RequestParam int count) {
        List<MessageDto> messages = ImmutableList.copyOf(messageService.get(Math.floorDiv(count, MESSAGE_ON_PAGE), MESSAGE_ON_PAGE, Status.Approved)).stream()
                .map((message) -> (new MessageDto(
                        message.getId(),
                        message.getUsername(),
                        message.getMessage(),
                        message.getColor().name(),
                        message.getCreationTime().getTime()))
                )
                .collect(Collectors.toList());

        return new Gson().toJson(messages);
    }

    @MessageMapping("/approve_message")
    @SendTo("/topic/message")
    public void approve(@RequestParam int id) {
        if (messageService.updateStatus(id, Status.Approved)) {
            // TODO: Fix me
            //return new Gson().toJson();
        }
    }

    @MessageMapping("/decline_message")
    @SendTo("/topic/message")
    public void decline(@RequestParam int id) {
        if (messageService.updateStatus(id, Status.Decline)) {
            // TODO: Fix me
            // Send one new message
        }
    }

    @MessageMapping("/new_message")
    @SendTo("/topic/moderate_message")
    public String fresh(@RequestParam int count) {
        List<MessageDto> messages = ImmutableList.copyOf(messageService.get(Math.floorDiv(count, MESSAGE_ON_PAGE), MESSAGE_ON_PAGE, Status.New)).stream()
                .map((message) -> (new MessageDto(
                        message.getId(),
                        message.getUsername(),
                        message.getMessage(),
                        message.getColor().name(),
                        message.getCreationTime().getTime()))
                )
                .collect(Collectors.toList());

        return new Gson().toJson(messages);
    }

}
