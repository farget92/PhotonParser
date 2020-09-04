package com.farget93.photon;

import com.farget93.photon.protocol.ProtocolResult;
import com.farget93.photon.protocol.ProtocolSerializer;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;

public class ProtocolParser {

    private final Class<? extends ProtocolSerializer> protocolType;
    private ProtocolSerializer protocol;

    public ProtocolParser(Class<? extends ProtocolSerializer> protocolType){
        this.protocolType = protocolType;
    }

    public final void build() throws ReflectiveOperationException {
        Constructor<? extends ProtocolSerializer> constructor = protocolType.getConstructor();
        constructor.setAccessible(true);
        protocol = constructor.newInstance();
    }

    public ProtocolResult parse(byte[] data) throws ProtocolSerializer.ProtocolParserException {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        return (ProtocolResult) protocol.deserialize(buffer, ProtocolResult.class);
    }

    public void parseAndEmit(byte[] data){

    }
}