/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2019.
 */
package dev.galasa.zosmf.internal.properties;

import java.util.Arrays;
import java.util.List;

import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.framework.spi.cps.CpsProperties;
import dev.galasa.zosmf.ZosmfManagerException;

/**
 * zOSMF Server images
 * <p>
 * The zOSMF server images active on the supplied cluster 
 * </p><p>
 * The property is:<br>
 * {@code zosmf.server.[clusterid].images=SYSA,SYSB} 
 * </p>
 * <p>
 * There is no default value
 * </p>
 *
 */
public class ServerImages extends CpsProperties {

    public static List<String> get(String clusterId) throws ZosmfManagerException {
        try {
            String serverImages = getStringNulled(ZosmfPropertiesSingleton.cps(), "server", "images", clusterId);

            if (serverImages == null) {
                throw new ZosmfManagerException("Value for zOSMF server port not configured for zOS cluster "  + clusterId);
            }
            return Arrays.asList(serverImages.split(","));
        } catch (ConfigurationPropertyStoreException e) {
            throw new ZosmfManagerException("Problem asking the CPS for the zOSMF server port for zOS cluster "  + clusterId, e);
        }
    }

}
