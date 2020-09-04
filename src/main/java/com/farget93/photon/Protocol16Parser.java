package com.farget93.photon;

import com.farget93.photon.events.ReceivedEvent;
import com.farget93.photon.events.RequestEvent;
import com.farget93.photon.events.ResponseEvent;
import com.farget93.photon.protocol.Protocol16;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Protocol16Parser {

    protected int HEADER_LENGTH = 12;
    protected int HEADER_FLAG_ENCRYPTED = 1;
    protected int HEADER_FLAG_CRC = 204;

    protected int COMMAND_HEADER_LENGTH = 12;

    protected Map<Integer, BiConsumer<ByteBuffer, Integer>> commandTypeAction = new HashMap<>();
    protected Map<Integer, Consumer<ByteBuffer>> messageTypeAction = new HashMap<>();

    protected Consumer<RequestEvent> onRequest = (event) -> {};
    protected Consumer<ResponseEvent> onResponse = (event) -> {};
    protected Consumer<ReceivedEvent> onReceive = (event) -> {};

    protected final Protocol16 protocol16;

    public Protocol16Parser(){
        this.protocol16 = new Protocol16();
        initializeActions();
    }

    public void parse(byte[] rawData){
        parseHeader(ByteBuffer.wrap(rawData));
    }

    protected void initializeActions(){
        commandTypeAction.put(Protocol16Util.CommandType.DISCONNECT.getValue(), (buffer, commandLength) -> {});
        commandTypeAction.put(Protocol16Util.CommandType.SEND_RELIABLE.getValue(), (buffer, commandLength) -> {
            processReliableMessage(buffer, commandLength);
        });
        commandTypeAction.put(Protocol16Util.CommandType.SEND_UNRELIABLE.getValue(), (buffer, commandLength) -> {
            buffer.position(buffer.position() + 4);
            commandLength -= 4;
            processReliableMessage(buffer, commandLength);
        });
        commandTypeAction.put(Protocol16Util.CommandType.SEND_FRAGMENT.getValue(), (buffer, commandLength) -> {
            throw new UnsupportedOperationException("Not implemented yet");
        });

        messageTypeAction.put(Protocol16Util.MessageType.OPERATION_REQUEST.getValue(), (buffer) -> {
            RequestEvent event = protocol16.deserializeOperationRequest(buffer);
            onRequest.accept(event);
        });
        messageTypeAction.put(Protocol16Util.MessageType.OPERATION_RESPONSE.getValue(), (buffer) -> {
            ResponseEvent event = protocol16.deserializeOperationResponse(buffer);
            onResponse.accept(event);
        });
        messageTypeAction.put(Protocol16Util.MessageType.EVENT.getValue(), (buffer) -> {
            ReceivedEvent event = protocol16.deserializeEventData(buffer, null);
            onReceive.accept(event);
        });
    }

    protected void parseHeader(ByteBuffer buffer){
        /*if(buffer.remaining() < HEADER_LENGTH)
            throw new BufferUnderflowException();

        short peerID = buffer.getShort();
        int flag = buffer.get() & 0xFF;
        int commandCount = buffer.get() & 0xFF;
        int timestamp = buffer.getInt();
        int challenge = buffer.getInt();

        if(flag == HEADER_FLAG_ENCRYPTED)
            throw new UnsupportedOperationException("Encrypted packets can not be parsed");

        if(flag == HEADER_FLAG_CRC){
            /* */
       // }
/*
        for(int i = 0; i < commandCount; i++)
            parseCommand(buffer);*/
    }



    protected void processReliableMessage(ByteBuffer buffer, int commandLength){
        buffer.position(buffer.position() + 1);
        commandLength--;

        int messageType = buffer.get() & 0xFF;
        commandLength--;

        int operationLength = commandLength;

        byte[] messageRawData = new byte[operationLength];
        buffer.get(messageRawData, buffer.position(), operationLength);
        ByteBuffer messageBuffer = ByteBuffer.wrap(messageRawData);

        Consumer<ByteBuffer> messageAction = messageTypeAction.getOrDefault(messageType, (innerBuffer) -> {
            throw new IllegalArgumentException("Message type can not be resolved");
        });
        messageAction.accept(messageBuffer);
    }
}