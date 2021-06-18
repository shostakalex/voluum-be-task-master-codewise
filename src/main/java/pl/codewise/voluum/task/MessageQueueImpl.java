package pl.codewise.voluum.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MessageQueueImpl implements MessageQueue {

    private final SnapshotImpl snapshot;

    public MessageQueueImpl() {
        this.snapshot = new SnapshotImpl();
    }

    @Override
    public void add(Message message) {
        if (snapshot.getMessages().size() < 100)
            snapshot.getMessages().add(message);
        else {
            List<Message> list = new ArrayList<>(snapshot.getMessages());
            list.remove(0);
            snapshot.getMessages().clear();
            snapshot.getMessages().addAll(list);
            snapshot.getMessages().add(message);
        }
    }

    @Override
    public Snapshot getSnapshot() {
        Collection<Message> collection = snapshot.getMessages().stream().
                filter(msg -> msg.getDateReceived().isAfter(LocalDateTime.now().minusMinutes(5))).
                collect(Collectors.toCollection(ArrayList::new));
        snapshot.getMessages().clear();
        snapshot.getMessages().addAll(collection);
        return snapshot;
    }

    @Override
    public Snapshot getSnapshot(Message.Type type) {
        Collection<Message> collection = snapshot.getMessages().stream().
                filter(msg -> msg.getDateReceived().isAfter(LocalDateTime.now().minusMinutes(5)) &&
                        msg.getType().equals(type)).
                collect(Collectors.toCollection(ArrayList::new));
        SnapshotImpl snapshotImpl = new SnapshotImpl();
        snapshotImpl.getMessages().addAll(collection);
        return snapshotImpl;
    }
}
