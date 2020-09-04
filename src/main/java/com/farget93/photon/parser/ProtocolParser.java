package com.farget93.photon.parser;

import com.farget93.photon.protocol.result.ProtocolResult;

import java.util.Collection;

public abstract class ProtocolParser {

    public abstract Collection<ProtocolResult> parse(byte[] rawData);

    public boolean parseAndExecute(byte[] rawData, Object eventExecutor){
        throw new UnsupportedOperationException();
        //return false; // If parse failed -> exception or empty collection
    }

}
