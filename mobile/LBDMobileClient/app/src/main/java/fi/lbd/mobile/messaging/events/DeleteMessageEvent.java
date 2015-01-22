package fi.lbd.mobile.messaging.events;

import fi.lbd.mobile.events.AbstractEvent;

/**
 * Tries to delete message with the given id from the backend.
 * Created by Tommi on 3.1.2015.
 */
public class DeleteMessageEvent extends AbstractEvent {
    private final String messageId;

    public DeleteMessageEvent(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
