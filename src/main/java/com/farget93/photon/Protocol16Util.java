package com.farget93.photon;

public class Protocol16Util {

    public enum CommandType{
        DISCONNECT(0x04),
        SEND_RELIABLE(0x06),
        SEND_UNRELIABLE(0x07),
        SEND_FRAGMENT(0x08);

        private final int value;

        CommandType(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }

    public enum MessageType{
        OPERATION_REQUEST(0x02),
        OPERATION_RESPONSE(0x03),
        EVENT(0x04);

        private final int value;

        MessageType(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }

}