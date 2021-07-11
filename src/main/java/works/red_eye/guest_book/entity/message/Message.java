package works.red_eye.guest_book.entity.message;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Temporal(TemporalType.TIMESTAMP)
    private final Date creationTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.updateTime = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.updateTime = new Date();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updateTime = new Date();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.updateTime = new Date();
    }
}
