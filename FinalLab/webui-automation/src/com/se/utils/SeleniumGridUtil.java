package com.se.utils;

import com.se.config.ConfigHelper;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumGridUtil {
    private static String _browser;
    private static String _browserVersion;
    private static String _os;
    private static String _osVersion;

    public static DesiredCapabilities setBrowserCapabilities() {
        var caps = new DesiredCapabilities();
        _browser = ConfigHelper.getInstance().getBrowser();
        _browserVersion = ConfigHelper.getInstance().getBrowserVersion();
        _os = ConfigHelper.getInstance().getOsPlatform();
        _osVersion = ConfigHelper.getInstance().getPlatformVersion();

        caps.setCapability("browser", _browser);
        caps.setCapability("browser_version", _browserVersion);
        caps.setCapability("os", _os);
        caps.setCapability("os_version", _osVersion);

        return caps;

    }

}
