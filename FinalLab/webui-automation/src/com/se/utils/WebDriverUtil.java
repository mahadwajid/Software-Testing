package com.se.utils;

import com.se.TestDriver;
import com.se.config.ConfigHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

public class WebDriverUtil {
    public static class BrowserCleanup implements Runnable {
        public void run() {
            System.out.println("Cleaning up the browser");
            try {
                TestDriver.getDriver().quit();
            } catch (NullPointerException e) {
                System.out.println("Browser already shut down.");
            }
        }
    }

    public static WebDriver createWebDriver(Dimension requestedDimensions) {
        var capabilities = PlatformUtil.getPlatformCapabilities();

        switch (ConfigHelper.getInstance().getSeleniumEnv()) {
            case "LOCALBROWSER":
                return selectLocalBrowser(requestedDimensions);
            case "SELENIUMGRID":
                capabilities.merge(SeleniumGridUtil.setBrowserCapabilities());
        }

        capabilities.merge(
                getBrowserCapabilities()
        );

        try {
            var remoteDriver = new RemoteWebDriver(new URL(ConfigHelper.getInstance().getSeleniumHub()), capabilities);
            remoteDriver.setFileDetector(new LocalFileDetector());
            return remoteDriver;
        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
        }
    }

    public static DesiredCapabilities getBrowserCapabilities() {
        var desiredCapabilities = new DesiredCapabilities();
        switch (ConfigHelper.getInstance().getBrowser()) {
            case "NONE":
                break;
            case "CHROME":
                desiredCapabilities.setCapability("browserName", "chrome");
                break;
            case "EDGE":
                desiredCapabilities.setCapability("browserName", "Edge");
                desiredCapabilities.setCapability("browserstack.ie.enablePopups", "true");
                break;
            case "FIREFOX":
                desiredCapabilities.setCapability("marionette", true);
                desiredCapabilities.setCapability("browserName", "firefox");
                break;
            case "IE":
                desiredCapabilities.setCapability("browserName", "internet explorer");
                desiredCapabilities.setCapability("browserstack.ie.enablePopups", "true");
                break;
            case "SAFARI":
                desiredCapabilities.setCapability("browserName", "safari");
                desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                break;
            default:
                throw new WebDriverException("No browser specified");

        }

        desiredCapabilities.setCapability("version", ConfigHelper.getInstance().getBrowserVersion());
        return desiredCapabilities;

    }

    public static WebDriver selectLocalBrowser(Dimension requestedDimensions) {
        var loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        switch (ConfigHelper.getInstance().getBrowser()) {
            case "CHROME":
                var caps = new DesiredCapabilities();
                caps.setCapability("goog:loggingPrefs", loggingPreferences);

                WebDriverManager.chromedriver().setup();

                var args = new ArrayList<String>();
                args.add("--disable-site-isolation-trials");
                if (ConfigHelper.getInstance().shouldRunTestsHeadless()) {
                    args.add("--headless");
                    args.add("--window-size=" + requestedDimensions.width + "," + requestedDimensions.height);
                }
                if (ConfigHelper.getInstance().isWebInspectorEnabled()) {
                    args.add("--remote-debugging-port=9222");
                }

                var chromeOptions = new ChromeOptions();
                chromeOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

                if (ConfigHelper.getInstance().isOverrideBrowserDefaultDownloadDirectoryEnabled()) {
                    var prefs = new HashMap<>();
                    prefs.put("download.default_directory", TestDriver.getOverrideDownloadDirectory());
                    chromeOptions.setExperimentalOption("prefs", prefs);
                }

                chromeOptions.addArguments(args);
                chromeOptions.merge(caps);

                return new ChromeDriver(chromeOptions);
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                var firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                if (ConfigHelper.getInstance().shouldRunTestsHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }

                if (ConfigHelper.getInstance().isOverrideBrowserDefaultDownloadDirectoryEnabled()) {
                    firefoxOptions.addPreference("browser.download.dir", TestDriver.getOverrideDownloadDirectory());
                }

                return new FirefoxDriver(firefoxOptions);
            case "IE":
                WebDriverManager.iedriver().setup();
                var internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                if (ConfigHelper.getInstance().isOverrideBrowserDefaultDownloadDirectoryEnabled()) {
                    internetExplorerOptions.setCapability(
                            "se:iePrefs",
                            Collections.singletonMap("Download Directory", TestDriver.getOverrideDownloadDirectory())
                    );
                }
                return new InternetExplorerDriver(internetExplorerOptions);
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                //TODO: Upgrade to selenium 4 so we can use edgeoptions.addarguments
                var edgeOptions = new EdgeOptions();
                if (ConfigHelper.getInstance().isOverrideBrowserDefaultDownloadDirectoryEnabled()) {
                    edgeOptions.setCapability(
                            "ms:edgeOptions",
                            Collections.singletonMap(
                                    "prefs",
                                    Collections.singletonMap("download.default_directory", TestDriver.getOverrideDownloadDirectory())
                            )
                    );
                }
                return new EdgeDriver(edgeOptions);
            case "SAFARI":
                System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
                var safariOptions = new SafariOptions();
                safariOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                if (ConfigHelper.getInstance().isOverrideBrowserDefaultDownloadDirectoryEnabled()) {
                    safariOptions.setCapability(
                            SafariOptions.CAPABILITY,
                            Collections.singletonMap(
                                    "prefs",
                                    Collections.singletonMap("download.default_directory", TestDriver.getOverrideDownloadDirectory())
                            )
                    );
                }
                return new SafariDriver(safariOptions);
            default:
                throw new WebDriverException("No browser specified");
        }
    }
}
