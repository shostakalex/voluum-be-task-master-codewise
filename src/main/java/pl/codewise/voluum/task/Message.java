package pl.codewise.voluum.task;

import java.time.LocalDateTime;

/**
 * You can modify this class
 */
public class Message {

    public enum Type {
        INFO, WARN, ERROR
    }

    private final String content;
    private final Type type;
    private LocalDateTime dateReceived;

    public Message(String content, Type type) {
        this.content = content;
        this.type = type;
        this.setDateReceived(LocalDateTime.now());
    }

    public String getContent() {
        return content;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDateTime dateReceived) {
        this.dateReceived = dateReceived;
    }
}
