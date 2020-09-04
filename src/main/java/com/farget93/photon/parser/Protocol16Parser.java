package com.farget93.photon.parser;

import com.farget93.photon.protocol.Protocol16;
import com.farget93.photon.protocol.result.ProtocolResult;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.BiFunction;

public class Protocol16Parser extends ProtocolParser {

    protected Map<Integer, BiFunction<ByteBuffer, Integer, ProtocolResult>> commandDeserializers = new HashMap<>();

    public Protocol16Parser(){
        this.deserializerInitializer();
    }

    @Override
    public Collection<ProtocolResult> parse(byte[] rawData) {
        return parseHeader(ByteBuffer.wrap(rawData));
    }

    protected void deserializerInitializer(){
        commandDeserializers.put(0, (buffer, commandLength) -> null);

    }

    protected Collection<ProtocolResult> parseHeader(ByteBuffer buffer){
        if(buffer.remaining() < Protocol16.HEADER_LENGTH)
            throw new BufferUnderflowException();

        short peerID = buffer.getShort();
        int flag = buffer.get() & 0xFF;
        int commandCount = buffer.get() & 0xFF;
        int timestamp = buffer.getInt();
        int challenge = buffer.getInt();

        if(flag == Protocol16.HEADER_FLAG_ENCRYPTED)
            throw new UnsupportedOperationException("Encrypted packets can not be parsed");

        if(flag == Protocol16.HEADER_FLAG_CRC)
            throw new UnsupportedOperationException();

        List<ProtocolResult> commands = new ArrayList<>();
        for(int i = 0; i < commandCount; i++){
            ProtocolResult temp = parseCommand(buffer);
            if(temp != null)
                commands.add(temp);
        }

        return commands;
    }

    protected ProtocolResult parseCommand(ByteBuffer buffer){
        int commandType = buffer.get() & 0xFF;
        int channelID = buffer.get() & 0xFF;
        int commandFlags = buffer.get() & 0xFF;

        buffer.position(buffer.position() + 1);
        int commandLength = buffer.getInt();
        int sequenceNumber = buffer.getInt();

        commandLength -= Protocol16.COMMAND_HEADER_LENGTH;

        BiFunction<ByteBuffer, Integer, ProtocolResult> commandDeserializer = commandDeserializers.getOrDefault(commandType,
                (innerBuffer, innerCommandLength) -> {
                    buffer.position(buffer.position() + innerCommandLength);
                    return null;
                });

        return commandDeserializer.apply(buffer, commandLength);
    }



}
