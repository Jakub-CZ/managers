/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2019.
 */
package dev.galasa.selenium.internal;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;

import dev.galasa.framework.spi.ConfigurationPropertyStoreException;
import dev.galasa.selenium.SeleniumManagerException;
import dev.galasa.selenium.internal.properties.SeleniumGeckoProfile;
import dev.galasa.selenium.internal.properties.SeleniumWebDriver;
import dev.galasa.selenium.internal.properties.SeleniumWebDriverPath;

public enum Browser {
  GECKO, IE, CHROME, EDGE;

  final static Log logger = LogFactory.getLog(Browser.class);

  public static WebDriver getWebDriver(String instance) throws IOException, SeleniumManagerException {
    try {
      String driver = SeleniumWebDriver.get(instance);
      switch (getBrowser(driver)) {
        case GECKO:
          return getGeckoDriver(instance);
        case CHROME:
          return getChromeDriver(instance);
        case EDGE:
          return getEdgeDriver(instance);
        case IE:
          return getIEDriver(instance);
        default:
          throw new SeleniumManagerException("Unknown/Unsupported driver instance: " + driver);
      }
    } catch (Exception e) {
      throw new SeleniumManagerException("Unable to get driver instance", e);
    }
  }

  private static WebDriver getGeckoDriver(String instance) throws IOException, SeleniumManagerException {
    FirefoxOptions options = new FirefoxOptions();

    try {
      System.setProperty("webdriver.gecko.driver", SeleniumWebDriverPath.get(instance, "gecko"));
    } catch (Exception e) {
      throw new SeleniumManagerException("Unable to get Gecko path from CPS for instance: " + instance, e);
    }

    ProfilesIni profile = new ProfilesIni();
    String geckoProfile = null;
    try {
      geckoProfile = SeleniumGeckoProfile.get(instance);
    } catch (ConfigurationPropertyStoreException e) {
      throw new SeleniumManagerException("Unable to get Gecko profile from CPS for instance: " + instance, e);
    }

    FirefoxProfile ffProfile = null;
    if(geckoProfile != null && !geckoProfile.trim().isEmpty()) {
      ffProfile = profile.getProfile(geckoProfile);
      if(ffProfile == null) {
        logger.info("Gecko profile " + geckoProfile + " unavaiable, creating new profile");
        ffProfile = new FirefoxProfile();
      } else {
        logger.info("Gecko profile " + geckoProfile + " found and available");
      }
    } else {
      logger.info("Gecko profile not found in CPS, creating new profile");
      ffProfile = new FirefoxProfile();
    }

    // accept SSL certs
    FirefoxOptions capabilities = new FirefoxOptions();
    capabilities.setAcceptInsecureCerts(true);
    capabilities.setCapability("moz:firefoxOptions", options);
    capabilities.setProfile(ffProfile);

    return new FirefoxDriver(capabilities);
  }

  public static WebDriver getChromeDriver(String instance) throws SeleniumManagerException {
    try {
      System.setProperty("webdriver.chrome.driver", SeleniumWebDriverPath.get(instance, "chrome"));
    } catch (Exception e) {
      throw new SeleniumManagerException("Unable to get Chrome path from CPS for instance: " + instance, e);
    }

    ChromeOptions capabilities = new ChromeOptions();
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

    return new ChromeDriver(capabilities);
  }

  public static WebDriver getEdgeDriver(String instance) throws SeleniumManagerException {
    try {
      System.setProperty("webdriver.edge.driver", SeleniumWebDriverPath.get(instance, "edge"));
    } catch (Exception e) {
      throw new SeleniumManagerException("Unable to get Edge path from CPS for instance: " + instance, e);
    }

    EdgeOptions capabilities = new EdgeOptions();
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

    return new EdgeDriver(capabilities);
  }

  public static WebDriver getIEDriver(String instance) throws SeleniumManagerException {
    try {
      System.setProperty("webdriver.ie.driver", SeleniumWebDriverPath.get(instance, "ie"));
    } catch (Exception e) {
      throw new SeleniumManagerException("Unable to get IE path from CPS for instance: " + instance, e);
    }

    InternetExplorerOptions capabilities = new InternetExplorerOptions();
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

    return new InternetExplorerDriver(capabilities);
  }

  public static @NotNull Browser getBrowser(@NotNull String browser) throws SeleniumManagerException {
    browser = browser.trim();

    for (Browser d : values()) {
      if (browser.equalsIgnoreCase(d.name())) {
        return d;
      }
    }
    throw new SeleniumManagerException("Unsupported browser type: " + browser);
  }
}
