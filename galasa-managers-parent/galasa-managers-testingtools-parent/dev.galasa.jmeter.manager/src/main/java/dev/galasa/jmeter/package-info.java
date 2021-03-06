/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2020.
 */
/**
 * JMeter Manager
 * 
 * @galasa.manager JMeter
 * 
 * @galasa.release.state ALPHA - This Manager is being actively developed. It is subject to change and has not been extensively tested.
 * 
 * @galasa.description
 * 
 * This Manager enables a JMeter session to run inside a Docker Container. The JMeter Manager requests a container from the Docker Manager inside which the JMeter scripts, or JMX files can run.  
 * The test can access all JMeter-generated files inside the container without worrying about how the container is provisioned, maintained or shut down at the end of test.
 * By using a containerized environment, the test can benefit from the associated standards of scalability and uniformity.
 * <br><br>
 * 
 * <br><br> 
 * The logfiles and generated CSV files can be accessed once the JMeter tests are complete and the container becomes available for interaction.
 * The JMeter Manager allows as many JMeter sessions as you have available Docker container slots on your machine.
 * 
 * @galasa.limitations
 * 
 * JMeter tests cannot be run remotely on a target host.
 */
package dev.galasa.jmeter;
