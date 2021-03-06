/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2019.
 */
package dev.galasa.zos3270.internal.datastream;

import dev.galasa.zos3270.spi.NetworkException;

public class StructuredFieldReadPartition extends StructuredField {

    public static final byte QUERY = 0x02;

    public enum Type {
        QUERY
    }

    private final int  pid;
    private final Type type;

    public StructuredFieldReadPartition(byte[] structuredFieldData) throws NetworkException {
        this.pid = structuredFieldData[1];

        switch (structuredFieldData[2]) {
            case QUERY:
                this.type = Type.QUERY;
                break;
            default:
                throw new NetworkException("Unsupported Read Partition Type code = " + structuredFieldData[2]);
        }
    }

    public int getPartitionId() {
        return this.pid;
    }

    public Type getType() {
        return this.type;
    }

}
