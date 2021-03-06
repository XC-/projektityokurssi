package fi.lbd.mobile.mapobjects.events;

import android.support.annotation.NonNull;

import fi.lbd.mobile.authorization.AuthorizedEvent;

/**
 * Requests for details about a single map object.
 */
public class RequestMapObjectEvent extends AuthorizedEvent {
    private final String id;

    public RequestMapObjectEvent(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
