package com.se;

import com.se.config.ConfigHelper;
import com.se.utils.FileUtils;
import com.se.utils.WebDriverUtil;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class TestDriver {
    private static WebDriver _webDriver;
    private static final Dimension DESKTOP_DIMENSION = new Dimension(1920, 1080);

    private TestDriver() {
    }

    public static String getDownloadDirectory() {
        if (ConfigHelper.getInstance().isOverrideBrowserDefaultDownloadDirectoryEnabled()) {
            return getOverrideDownloadDirectory();
        }
        return FileUtils.buildFilePath(System.getProperty("user.home"), "Downloads");
    }

    public static String getOverrideDownloadDirectory() {
        var overrideDownloadsDirectory = FileUtils.buildFilePath(System.getProperty("user.dir"), "Downloads");

        FileUtils.createDirectoryIfNotExists(overrideDownloadsDirectory);
        var directory = new File(overrideDownloadsDirectory);
        return overrideDownloadsDirectory;
    }

    public static boolean hasDriver() {
        return _webDriver != null;
    }

    public synchronized static WebDriver getDriver() {
        final var browser = ConfigHelper.getInstance().getBrowser();

        if (_webDriver == null || _webDriver.toString().contains("(null)")) {
            _webDriver = WebDriverUtil.createWebDriver(DESKTOP_DIMENSION);

            // Edge crashes with this option enabled
            if (!browser.equals("EDGE") && !browser.equals("SAFARI")) {
                _webDriver.manage().deleteAllCookies();
            }

            _webDriver.manage().window().setSize(DESKTOP_DIMENSION);

            _webDriver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
            _webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }

        return _webDriver;
    }

    public static Object runScriptSync(String script) {
        return getExecutor().executeScript(script);
    }

    private static JavascriptExecutor getExecutor() {
        return (JavascriptExecutor) getDriver();
    }

    public static void clearCookies() {
        if (_webDriver == null) {
            return;
        }

        if (_webDriver instanceof WebStorage) {
            try {
                var webStorage = (WebStorage) _webDriver;
                webStorage.getSessionStorage().clear();
                webStorage.getLocalStorage().clear();
            } catch (Exception ex) {
                var isPageNotLoaded = ex.getMessage()
                        .contains("Failed to read the 'sessionStorage' property from 'Window'");

                if (!isPageNotLoaded) {
                    ex.printStackTrace();
                }
            }
        }

        _webDriver.manage().deleteAllCookies();
    }

    public static void tearDown() {
        if (_webDriver != null) {
            clearCookies();

            _webDriver.close();
            _webDriver.quit();

            _webDriver = null;
        }
    }
}
