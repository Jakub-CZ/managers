/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2019.
 */
package dev.galasa.zos.internal.properties;

import javax.validation.constraints.NotNull;

import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.framework.spi.cps.CpsProperties;
import dev.galasa.zos.ZosManagerException;

/**
 * Developer Supplied Environment zOS Image Cluster ID
 * <p>
 * The Cluster ID for the specified tag 
 * </p><p>
 * The property is:<br>
 * {@code zos.dse.tag.[tag].clusterid=PLEXA} 
 * </p>
 * <p>
 * There is no default
 * </p>
 *
 */
public class DseClusterIdForTag extends CpsProperties {
    
    public static String get(@NotNull String tag) throws ZosManagerException {
        try {
            return getStringNulled(ZosPropertiesSingleton.cps(), "dse.tag", "clusterid", tag);
        } catch (ConfigurationPropertyStoreException e) {
            throw new ZosManagerException("Problem asking the CPS for the DSE cluster id for tag '"  + tag + "'", e);
        }
    }
}
