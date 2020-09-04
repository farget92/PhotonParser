package com.farget93.photon.protocol;

import com.farget93.photon.events.ReceivedEvent;
import com.farget93.photon.events.RequestEvent;
import com.farget93.photon.events.ResponseEvent;

import java.nio.ByteBuffer;

public class Protocol16 extends Protocol {

    public static final int HEADER_LENGTH = 12;
    public static final int HEADER_FLAG_ENCRYPTED = 1;
    public static final int HEADER_FLAG_CRC = 204;

    public static final int COMMAND_HEADER_LENGTH = 12;



    @Override
    public RequestEvent deserializeOperationRequest(ByteBuffer buffer) {
        return null;
    }

    @Override
    public ResponseEvent deserializeOperationResponse(ByteBuffer buffer) {
        return null;
    }

    @Override
    public ReceivedEvent deserializeEventData(ByteBuffer buffer, ReceivedEvent target) {
        return null;
    }

    @Override
    public Object deserialize(ByteBuffer buffer, byte type) {
        return null;
    }

    @Override
    public byte deserializeByte(ByteBuffer buffer) {
        return 0;
    }

    @Override
    public short deserializeShort(ByteBuffer buffer) {
        return 0;
    }
}
