package com.se.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Objects;

public class ConfigHelper {
    public static ConfigHelper _singleton;
    public static String _url;

    private HashMap<String, String> _configs;

    private ConfigHelper(HashMap<String, String> configs) {
        _configs = configs;
    }

    public static ConfigHelper getInstance() {
        if (_singleton == null) {
            synchronized (ConfigHelper.class) {
                if (_singleton == null) {
                    var env = Dotenv.configure()
                            .directory("./")
                            .filename("config.env")
                            .load();
                    var developmentEnv = Dotenv.configure()
                            .directory("./")
                            .filename("config-dev.env")
                            .ignoreIfMissing()
                            .load();

                    var configs = new HashMap<String, String>() {
                        @Override
                        public String get(Object key) {
                            if (!containsKey(key)) {
                                throw new IllegalArgumentException(
                                        "Environment & config files were missing the following required key: "
                                                + key);
                            }
                            return super.get(key);
                        }
                    };

                    for (var entry : env.entries()) {
                        configs.put(entry.getKey(), entry.getValue());
                    }
                    for (var entry : developmentEnv.entries()) {
                        configs.put(entry.getKey(), entry.getValue());
                    }
                    _singleton = new ConfigHelper(configs);
                }
            }
        }
        return _singleton;
    }




    public String getApiRoot() {
        return _configs.get("API_ROOT");
    }

    public String getSubjectExpertUrl() {
        return _configs.get("SUBJECT_EXPERT_URL");
    }



    public String getStudentPassword() {
        return _configs.get("STUDENT_PASSWORD");
    }

    public String getStudentUsername() {
        return _configs.get("STUDENT_USERNAME");
    }

    public String getNonSystemUserCustomerName() {
        return _configs.get("NON_SYSTEM_USER_ENTERPRISE_CUSTOMER_NAME");
    }

    public String getNonSystemUserAlarmProtocolName() {
        return _configs.get("NON_SYSTEM_USER_ALARM_PROTOCOL_NAME");
    }

    public String getSystemUserEnterpriseName() {
        return _configs.get("SYSTEM_USER_ENTERPRISE_NAME");
    }

    public String getSystemUserPassword() {
        return _configs.get("SYSTEM_USER_PASSWORD");
    }

    public String getSystemUserUsername() {
        return _configs.get("SYSTEM_USER_USERNAME");
    }

    public String getSystemUserCustomerName() {
        return _configs.get("SYSTEM_USER_ENTERPRISE_CUSTOMER_NAME");
    }

    public String getSystemUserAlarmProtocolName() {
        return _configs.get("SYSTEM_USER_ALARM_PROTOCOL_NAME");
    }

    public String getOtherNonSystemUserEnterpriseName() {
        return _configs.get("OTHER_NON_SYSTEM_USER_ENTERPRISE_NAME");
    }

    public String getOtherNonSystemUserPassword() {
        return _configs.get("OTHER_NON_SYSTEM_USER_PASSWORD");
    }

    public String getOtherNonSystemUserUsername() {
        return _configs.get("OTHER_NON_SYSTEM_USER_USERNAME");
    }

    public String getOtherNonSystemUserCustomerName() {
        return _configs.get("OTHER_NON_SYSTEM_USER_ENTERPRISE_CUSTOMER_NAME");
    }

    public String getOtherNonSystemUserAlarmProtocolName() {
        return _configs.get("OTHER_NON_SYSTEM_USER_ALARM_PROTOCOL_NAME");
    }

    public boolean shouldRunTestsHeadless() {
        return Boolean.parseBoolean(_configs.get("HEADLESS_TESTS"));
    }

    public String getTestObjectZone() {
        return _configs.get("TESTOBJECT_ZONE");
    }

    public String getTestObjectAccesskey() {
        return _configs.get("TESTOBJECT_ACCESSKEY");
    }

    public boolean shouldOpenBrowserConsole() {
        return Boolean.parseBoolean(_configs.get("BROWSER_DEBUG_ENABLED"));
    }

    public String getBrowser() {
        return _configs.get("BROWSER");
    }

    public String getBrowserVersion() {
        return _configs.get("BROWSER_VERSION");
    }

    public String getSeleniumEnv() {
        return _configs.get("SELENIUM_ENV");
    }

    public String getOsPlatform() {
        return _configs.get("PLATFORM");
    }

    public String getPlatformVersion() {
        return _configs.get("PLATFORM_VERSION");
    }

    public boolean isMobileTestingEnabled() {
        return Boolean.parseBoolean(_configs.get("MOBILE_TESTING_ENABLED"));
    }

    public String getSeleniumHub() {
        return _configs.get("SELENIUM_HUB");
    }

    public String getBrowserstackProject() {
        return _configs.get("BROWSERSTACK_PROJECT");
    }

    public void setTestObjectSeleniumHub() {
        final var zone = getTestObjectZone();

        if (zone.equals("Europe")) {
            _url = "https://eu1.appium.testobject.com/wd/hub";

        } else {
            _url = "https://us1.appium.testobject.com/wd/hub";

        }

        _singleton._configs.put("TESTOBJECT_SELENIUM_HUB", _url);
    }

    public boolean isWebInspectorEnabled() {
        return Boolean.parseBoolean(_configs.get("WEB_INSPECTOR_ENABLED"));
    }

    public boolean isServerLogCaptureEnabled() {
        return Objects.equals(_configs.get("ENABLE_SERVER_LOG_CAPTURE"), "true");
    }

    public int getMaxRetriesOnFailure() {
        return Integer.parseInt(_configs.get("FAIL_RETRIES"));
    }

    public int getTestInvocationCount() {
        return Integer.parseInt(_configs.get("TEST_INVOCATION_COUNT"));
    }

    public boolean isOverrideBrowserDefaultDownloadDirectoryEnabled() {
        return Objects.equals(_configs.get("OVERRIDE_BROWSER_DEFAULT_DOWNLOAD_DIRECTORY"), "true");
    }
}
