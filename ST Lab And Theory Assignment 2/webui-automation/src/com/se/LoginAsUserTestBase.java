package com.se;

import com.se.config.Constants.*;
import io.qameta.allure.Allure;
import org.testng.annotations.BeforeMethod;

public class LoginAsUserTestBase extends TestBase {
    protected final LoginParameters _loginParameters;
    private boolean _hasLaunched;

    protected LoginAsUserTestBase(
            LoginParameters loginParameters,
            boolean tearDownBrowserAfterEachTest
    ) {
        super(tearDownBrowserAfterEachTest);
        _loginParameters = loginParameters;
    }
    @BeforeMethod( alwaysRun = true )
    public void launchSubjectExpertAndLogin() {
        if ((_tearDownBrowserAfterEachTest || !_hasLaunched)) {
            _hasLaunched = true;
            launchSubjectExpertAndLogin(_loginParameters);
        } else {
            Allure.step("skip login");
        }
    }
}
