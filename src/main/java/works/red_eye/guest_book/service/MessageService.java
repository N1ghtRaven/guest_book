package works.red_eye.guest_book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import works.red_eye.guest_book.entity.message.Color;
import works.red_eye.guest_book.entity.message.Message;
import works.red_eye.guest_book.entity.message.Status;
import works.red_eye.guest_book.repos.MessagePagingRepository;
import works.red_eye.guest_book.repos.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessagePagingRepository messagePagingRepository;

    public Message get(long id) { return messageRepository.findById(id); }
    public Iterable<Message> get() {
        return messageRepository.findAll();
    }

    public Iterable<Message> get(int page, int messageOnPage, Status status) {
        Pageable pageable = PageRequest.of(page, messageOnPage, Sort.by("creationTime").descending());
        return messagePagingRepository.findAllByStatus(pageable, status);
    }

    public Iterable<Message> get(Status status) {
        return messageRepository.findAllByStatus(status);
    }

    public Message add(String username, String message, Color color) {
        Message msg = new Message();
        msg.setUsername(username);
        msg.setMessage(message);
        msg.setColor(color);
        msg.setStatus(Status.New);

        return messageRepository.save(msg);
    }

    public boolean updateStatus(long messageId, Status newStatus) {
        Message messageFromDb = messageRepository.findById(messageId);
        if (messageFromDb == null) {
            return false;
        }

        messageFromDb.setStatus(newStatus);
        messageRepository.save(messageFromDb);
        return true;
    }

    public boolean remove(long id) {
        Message messageFromDb = messageRepository.findById(id);
        if (messageFromDb == null) {
            return false;
        }

        messageRepository.delete(messageFromDb);
        return true;
    }
}
