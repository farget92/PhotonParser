package com.farget93.photon;

import com.farget93.photon.protocol.ProtocolResult;
import com.farget93.photon.protocol.ProtocolSerializer;
import com.farget93.photon.protocol.protocol16.Protocol16Serializer;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProtocolParserTest {

    @Test
    public void ProtocolParserTest() throws ReflectiveOperationException, IOException, ProtocolSerializer.ProtocolParserException {
        ProtocolParser protocolParser = new ProtocolParser(Protocol16Serializer.class);
        protocolParser.build();

        Path sourceFile = Paths.get("src","test","resources",this.getClass().getSimpleName() + ".bin");

        byte[] data = Files.readAllBytes(sourceFile);
        ProtocolResult result = protocolParser.parse(data);

        System.out.println(result.getProtocolHeader().getValue("commandCount"));


    }
}