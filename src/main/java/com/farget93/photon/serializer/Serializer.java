package com.farget93.photon.serializer;

import com.farget93.photon.protocol.ProtocolSerializer;

import java.nio.ByteBuffer;

public interface Serializer<T> {

    T deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolSerializer.ProtocolParserException;

}
