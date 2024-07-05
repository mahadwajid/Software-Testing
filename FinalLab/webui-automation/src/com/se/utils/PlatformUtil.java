package com.se.utils;

import com.se.config.ConfigHelper;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PlatformUtil {
    //IUR-117 paring down enums as we only run on desktop
    public static DesiredCapabilities getPlatformCapabilities() {
        var capabilities = new DesiredCapabilities();
        switch (ConfigHelper.getInstance().getOsPlatform()) {
            case "OSX":
                capabilities.setCapability("os", "OS X");
                capabilities.setPlatform(org.openqa.selenium.Platform.MAC);
                break;
            case "WINDOWS":
                capabilities.setCapability("os", "Windows");
                capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
                break;
            case "UBUNTU":
                capabilities.setCapability("os", "Ubuntu");
                capabilities.setPlatform(org.openqa.selenium.Platform.LINUX);
                break;
            default:
                throw new WebDriverException("No platform specified");
        }

        capabilities.setCapability("os_version", ConfigHelper.getInstance().getPlatformVersion());

        return capabilities;
    }
}
