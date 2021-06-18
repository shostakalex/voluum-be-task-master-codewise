package pl.codewise.voluum.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can modify this class
 */
public class MessageQueueTest {

    @DisplayName("101 received messages")
    @Test
    public void test101ReceivedMessages() {
        MessageQueueImpl messageQueue = new MessageQueueImpl();
        for (int i = 1; i <= 101; i++) {
            Message msg;
            if (i % 3 == 0) msg = new Message(String.valueOf(i), Message.Type.WARN);
            else if (i % 2 == 0) msg = new Message(String.valueOf(i), Message.Type.INFO);
            else msg = new Message(String.valueOf(i), Message.Type.ERROR);
            messageQueue.add(msg);
        }
        Snapshot snapshot = messageQueue.getSnapshot();
        assertEquals(100, snapshot.getMessages().size());
    }

    @DisplayName("30 received messages")
    @Test
    public void test30ReceivedMessages() {
        MessageQueueImpl messageQueue = new MessageQueueImpl();
        for (int i = 1; i <= 30; i++) {
            Message msg;
            if (i % 2 == 0) msg = new Message(String.valueOf(i), Message.Type.INFO);
            else if (i % 3 == 0) msg = new Message(String.valueOf(i), Message.Type.WARN);
            else msg = new Message(String.valueOf(i), Message.Type.ERROR);
            messageQueue.add(msg);
        }
        Snapshot snapshot = messageQueue.getSnapshot();
        assertEquals(30, snapshot.getMessages().size());
    }

    @DisplayName("30 received messages and 1 old")
    @Test
    public void test30ReceivedMessagesAnd1Old() {
        MessageQueueImpl messageQueue = new MessageQueueImpl();
        for (int i = 1; i <= 31; i++) {
            Message msg;
            if (i % 2 == 0) msg = new Message(String.valueOf(i), Message.Type.INFO);
            else if (i % 3 == 0) msg = new Message(String.valueOf(i), Message.Type.WARN);
            else msg = new Message(String.valueOf(i), Message.Type.ERROR);
            if (i == 31)
                msg.setDateReceived(LocalDateTime.now().minusMinutes(30));
            messageQueue.add(msg);
        }
        Snapshot snapshot = messageQueue.getSnapshot();
        assertEquals(30, snapshot.getMessages().size());
    }

    @DisplayName("Old messages")
    @Test
    public void testHalfMessages() {
        MessageQueueImpl messageQueue = new MessageQueueImpl();
        for (int i = 1; i <= 101; i++) {
            Message msg;
            if (i % 2 == 0) {
                msg = new Message(String.valueOf(i), Message.Type.INFO);
                msg.setDateReceived(LocalDateTime.now().minusMinutes(10));
            } else msg = new Message(String.valueOf(i), Message.Type.ERROR);
            messageQueue.add(msg);
        }
        Snapshot snapshot = messageQueue.getSnapshot();
        assertEquals(50, snapshot.getMessages().size());
    }

    @DisplayName("Type messages")
    @Test
    public void testTypeInfoMessages() {
        MessageQueueImpl messageQueue = new MessageQueueImpl();
        for (int i = 1; i <= 100; i++) {
            Message msg;
            if (i <= 33) msg = new Message(String.valueOf(i), Message.Type.INFO);
            else if (i <= 66) msg = new Message(String.valueOf(i), Message.Type.WARN);
            else msg = new Message(String.valueOf(i), Message.Type.ERROR);
            messageQueue.add(msg);
        }
        Snapshot snapshotInfo = messageQueue.getSnapshot(Message.Type.INFO);
        Snapshot snapshotWarn = messageQueue.getSnapshot(Message.Type.WARN);
        Snapshot snapshotError = messageQueue.getSnapshot(Message.Type.ERROR);
        assertAll(
                () -> assertEquals(33, snapshotInfo.getMessages().size()),
                () -> assertEquals(33, snapshotWarn.getMessages().size()),
                () -> assertEquals(34, snapshotError.getMessages().size())
        );
    }

    @DisplayName("Any number of messages")
    @Test
    public void testAnyNumberOfMessages() {
        MessageQueueImpl messageQueue = new MessageQueueImpl();
        int x = 999999;
        for (int i = 1; i < x; i++) {
            Message msg;
            if (i % 5 == 0) msg = new Message(String.valueOf(i), Message.Type.INFO);
            else if (i % 2 == 0) msg = new Message(String.valueOf(i), Message.Type.WARN);
            else msg = new Message(String.valueOf(i), Message.Type.ERROR);
            messageQueue.add(msg);
        }
        assertAll("Any number of messages",
                () -> assertEquals(100, messageQueue.getSnapshot().getMessages().size()),
                () -> assertEquals(20, messageQueue.getSnapshot(Message.Type.INFO).getMessages().size()),
                () -> assertEquals(40, messageQueue.getSnapshot(Message.Type.WARN).getMessages().size()),
                () -> assertEquals(40, messageQueue.getSnapshot(Message.Type.ERROR).getMessages().size())
        );
    }
}