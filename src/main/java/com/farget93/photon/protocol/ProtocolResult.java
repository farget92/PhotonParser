package com.farget93.photon.protocol;

public class ProtocolResult {

    private final ProtocolHeader protocolHeader;
    private final ProtocolBody protocolBody;

    public ProtocolResult(ProtocolHeader protocolHeader, ProtocolBody protocolBody){
        this.protocolHeader = protocolHeader;
        this.protocolBody = protocolBody;
    }

    public final ProtocolHeader getProtocolHeader(){
        return this.protocolHeader;
    }

    public final ProtocolBody getProtocolBody(){
        return this.protocolBody;
    }
}