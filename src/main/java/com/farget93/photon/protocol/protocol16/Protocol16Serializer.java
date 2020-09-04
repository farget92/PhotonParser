package com.farget93.photon.protocol.protocol16;

import com.farget93.photon.protocol.ProtocolBody;
import com.farget93.photon.protocol.ProtocolHeader;
import com.farget93.photon.protocol.ProtocolResult;
import com.farget93.photon.protocol.ProtocolSerializer;
import com.farget93.photon.serializer.Serializer;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class Protocol16Serializer extends ProtocolSerializer{

    public Protocol16Serializer(){
        super.registerSerializer(Protocol16HeaderSerializer.class, new Protocol16HeaderSerializer());
        super.registerSerializer(Protocol16BodySerializer.class, new Protocol16BodySerializer());
    }

    @Override
    public ProtocolResult deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolParserException{
        ProtocolHeader protocolHeader = (ProtocolHeader) protocolSerializer.deserialize(buffer, Protocol16HeaderSerializer.class);
        ProtocolBody protocolBody = (ProtocolBody) protocolSerializer.deserialize(buffer, Protocol16BodySerializer.class,
                protocolHeader.getValue("commandCount"));

        return new ProtocolResult(protocolHeader, null);
    }

    protected static class Protocol16HeaderSerializer implements Serializer<ProtocolHeader>{

        public static final int HEADER_LENGTH = 12;
        public static final byte HEADER_FLAG_ENCRYPTED = 0x01;
        public static final byte HEADER_FLAG_CRC = -0x34; // -0x34 signed = 0xCC unsigned

        @Override
        public ProtocolHeader deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolParserException {
            if(buffer.remaining() < HEADER_LENGTH)
                throw new ProtocolParserException("Header is too short", new BufferUnderflowException());

            ProtocolHeader protocolHeader = new ProtocolHeader();

            short peerID = buffer.getShort();
            protocolHeader.setValue("peerID", peerID);

            byte flag = buffer.get();
            protocolHeader.setValue("flag", flag);

            byte commandCount = buffer.get();
            protocolHeader.setValue("commandCount", commandCount);

            int timestamp = buffer.getInt();
            protocolHeader.setValue("timestamp", timestamp);

            int challenge = buffer.getInt();
            protocolHeader.setValue("challenge", challenge);

            if(flag == HEADER_FLAG_ENCRYPTED)
                throw new ProtocolParserException("Can not parser encrypted packets");

            if(flag == HEADER_FLAG_CRC)
                throw new UnsupportedOperationException("TO BE IMPLEMENTED");
            return protocolHeader;
        }
    }

    protected static class Protocol16BodySerializer implements Serializer<ProtocolBody> {

        @Override
        public ProtocolBody deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolParserException {
            ProtocolBody protocolBody = new ProtocolBody();



            return protocolBody;
        }

    }
}