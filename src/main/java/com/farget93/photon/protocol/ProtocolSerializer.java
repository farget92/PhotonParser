package com.farget93.photon.protocol;

import com.farget93.photon.serializer.Serializer;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class ProtocolSerializer implements Serializer<ProtocolResult>{

    protected Map<Class<?>, Serializer> serializerMap;

    protected ProtocolSerializer(){
        serializerMap = new HashMap<>();
        registerSerializer(ProtocolResult.class, this);
    }

    protected void registerSerializer(Class<?> type, Serializer serializer){
        serializerMap.put(type, serializer);
    }

    public Object deserialize(ByteBuffer buffer, Class<?> type, Object... args) throws ProtocolParserException{
        if(!serializerMap.containsKey(type))
            throw new IllegalArgumentException("No serializer for type: " + type.getName());
        return serializerMap.get(type).deserialize(buffer, this, args);
    }

    public void setValueToMapContainer(ProtocolMapContainer protocolContainer, String key, Object value){
        protocolContainer.setValue(key, value);
    }

    public static class ProtocolMapContainer {

        private final Map<String, Object> values;

        protected ProtocolMapContainer(){
            this.values = new HashMap<>();
        }

        public final Object getValue(String key){
            return this.values.get(key);
        }

        private void setValue(String key, Object value){
            this.values.put(key, value);
        }
    }

    public static class ProtocolParserException extends Exception{

        public ProtocolParserException(String err){
            super(err);
        }

        public ProtocolParserException(String err, Exception cause){
            super(err, cause);
        }

    }
}