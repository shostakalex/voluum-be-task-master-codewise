package pl.codewise.voluum.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SnapshotImpl implements Snapshot {

    private final List<Message> messages = new ArrayList<>();

    @Override
    public Collection<Message> getMessages() {
        return this.messages;
    }
}
