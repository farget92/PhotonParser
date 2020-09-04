package com.farget93.photon.protocol.protocol16;

import com.farget93.photon.protocol.parts.ProtocolBody;
import com.farget93.photon.protocol.parts.ProtocolCommand;
import com.farget93.photon.protocol.parts.ProtocolHeader;
import com.farget93.photon.protocol.ProtocolResult;
import com.farget93.photon.protocol.ProtocolSerializer;
import com.farget93.photon.serializer.Serializer;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class Protocol16Serializer extends ProtocolSerializer{

    public Protocol16Serializer(){
        super.registerSerializer(Protocol16HeaderSerializer.class, new Protocol16HeaderSerializer());
        super.registerSerializer(Protocol16BodySerializer.class, new Protocol16BodySerializer());
        super.registerSerializer(Protocol16CommandSerializer.class, new Protocol16CommandSerializer());
    }

    @Override
    public ProtocolResult deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolParserException{
        ProtocolHeader protocolHeader = (ProtocolHeader) protocolSerializer.deserialize(buffer, Protocol16HeaderSerializer.class);
        ProtocolBody protocolBody = (ProtocolBody) protocolSerializer.deserialize(buffer, Protocol16BodySerializer.class,
                protocolHeader.getValue("commandCount"));

        return new ProtocolResult(protocolHeader, protocolBody);
    }

    protected static class Protocol16HeaderSerializer implements Serializer<ProtocolHeader>{

        public static final int HEADER_LENGTH = 12;
        public static final byte HEADER_FLAG_ENCRYPTED = 0x01;
        public static final byte HEADER_FLAG_CRC = -0x34; // -0x34 signed = 0xCC unsigned

        @Override
        public ProtocolHeader deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolParserException {
            if(buffer.remaining() < HEADER_LENGTH)
                throw new ProtocolParserException("Packet header is too short", new BufferUnderflowException());

            ProtocolHeader protocolHeader = new ProtocolHeader();

            short peerID = buffer.getShort();
            protocolSerializer.setValueToMapContainer(protocolHeader, "peerID", peerID);

            byte flag = buffer.get();
            protocolSerializer.setValueToMapContainer(protocolHeader, "flag", flag);

            byte commandCount = buffer.get();
            protocolSerializer.setValueToMapContainer(protocolHeader, "commandCount", commandCount);

            int timestamp = buffer.getInt();
            protocolSerializer.setValueToMapContainer(protocolHeader, "timestamp", timestamp);

            int challenge = buffer.getInt();
            protocolSerializer.setValueToMapContainer(protocolHeader, "challenge", challenge);

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
            int commandCount = ((byte) args[0]) & 0xFF;
            for(int i = 0; i < commandCount; i++){
                ProtocolCommand protocolCommand = (ProtocolCommand) protocolSerializer.deserialize(buffer, Protocol16CommandSerializer.class);
                //if(!((boolean) protocolCommand.getValue("isPartial"))) -> change to commandType enum
                //    protocolSerializer.setValueToMapContainer(protocolBody, Integer.toString(i),protocolCommand);
            }

            return protocolBody;
        }
    }

    protected static class Protocol16CommandSerializer implements Serializer<ProtocolCommand>{

        @Override
        public ProtocolCommand deserialize(ByteBuffer buffer, ProtocolSerializer protocolSerializer, Object... args) throws ProtocolParserException {
            ProtocolCommand x;

            System.out.println("XX");
            
            return null;
        }
    }
}