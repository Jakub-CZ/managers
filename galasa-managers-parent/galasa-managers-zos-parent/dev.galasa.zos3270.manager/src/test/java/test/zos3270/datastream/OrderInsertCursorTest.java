/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2019.
 */
package test.zos3270.datastream;

import org.junit.Assert;
import org.junit.Test;

import dev.galasa.zos3270.internal.datastream.OrderInsertCursor;
import dev.galasa.zos3270.spi.DatastreamException;

public class OrderInsertCursorTest {

    @Test
    public void testRA() throws DatastreamException {

        String result = new OrderInsertCursor().toString();

        String shouldbe = "IC";
        Assert.assertEquals("IC not translating correctly", shouldbe, result);
    }

}
