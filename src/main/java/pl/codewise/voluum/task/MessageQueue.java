package pl.codewise.voluum.task;

public interface MessageQueue {

    void add(Message message);

    Snapshot getSnapshot();

    Snapshot getSnapshot(Message.Type type);
}
