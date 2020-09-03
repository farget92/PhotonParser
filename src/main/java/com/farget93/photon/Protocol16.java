package com.farget93.photon;

import com.farget93.photon.events.ReceivedEvent;
import com.farget93.photon.events.RequestEvent;
import com.farget93.photon.events.ResponseEvent;

import java.nio.ByteBuffer;

public class Protocol16 {

    public static RequestEvent deserializeOperationRequest(ByteBuffer buffer){
        return null;
    }

    public static ResponseEvent deserializeOperationResponse(ByteBuffer buffer){
        return null;
    }

    public static ReceivedEvent deserializeEventData(ByteBuffer buffer){
        return null;
    }
}