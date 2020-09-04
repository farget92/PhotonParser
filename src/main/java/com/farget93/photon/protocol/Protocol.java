package com.farget93.photon.protocol;

import com.farget93.photon.events.ReceivedEvent;
import com.farget93.photon.events.RequestEvent;
import com.farget93.photon.events.ResponseEvent;

import java.nio.ByteBuffer;

public abstract class Protocol {

    // serializers

    public abstract RequestEvent deserializeOperationRequest(ByteBuffer buffer);

    public abstract ResponseEvent deserializeOperationResponse(ByteBuffer buffer);

    public abstract ReceivedEvent deserializeEventData (ByteBuffer buffer, ReceivedEvent target);

    public abstract Object deserialize (ByteBuffer buffer, byte type);

    public abstract byte deserializeByte(ByteBuffer buffer);

    public abstract short deserializeShort(ByteBuffer buffer);

}