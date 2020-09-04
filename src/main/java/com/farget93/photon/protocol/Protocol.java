package com.farget93.photon.protocol;

import com.farget93.photon.events.ReceivedEvent;
import com.farget93.photon.events.RequestEvent;
import com.farget93.photon.events.ResponseEvent;

import java.nio.ByteBuffer;

public interface Protocol {

    RequestEvent deserializeOperationRequest(ByteBuffer buffer);

    ResponseEvent deserializeOperationResponse(ByteBuffer buffer);

    ReceivedEvent deserializeEventData (ByteBuffer buffer, ReceivedEvent target);

    Object deserialize (ByteBuffer buffer, byte type);

    byte deserializeByte(ByteBuffer buffer);

    short deserializeShort(ByteBuffer buffer);

}