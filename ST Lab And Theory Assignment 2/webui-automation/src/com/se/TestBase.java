package com.se;

import com.se.api.ApiHelper;
import com.se.config.ConfigHelper;
import com.se.config.Constants;
import com.se.utils.LoginUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.annotations.DisabledRetryAnalyzer;

import java.util.Arrays;

import static com.se.utils.UtilsSet.launchBrowserAndUrl;

public abstract class TestBase {

    protected boolean _tearDownBrowserAfterEachTest;

    @BeforeSuite( alwaysRun = true )
    public void setUpSuite(ITestContext context) {
        Arrays.stream(context.getAllTestMethods())
                .filter(method -> method.getRetryAnalyzerClass() == DisabledRetryAnalyzer.class
                        || method.getRetryAnalyzerClass() == null)
                .forEach(method -> {
                    method.setRetryAnalyzerClass(SeRetryAnalyzer.class);

                    method.setInvocationCount(ConfigHelper.getInstance().getTestInvocationCount());
                });
    }

    public TestBase() {
        this(true);

    }

    public TestBase(boolean tearDownBrowserAfterEachTest) {
        _tearDownBrowserAfterEachTest = tearDownBrowserAfterEachTest;

        //TODO: Activate the following for default DB Status.
        //ApiHelper.ensureDatabaseSetupPerformed();
    }

    @AfterMethod( alwaysRun = true )
    public void tearDownTestBaseMethod() {
        if (_tearDownBrowserAfterEachTest) {
            TestDriver.tearDown();
        }
    }

    @Step
    public static void launchSubjectExpertAndLogin(Constants.LoginParameters loginParameters) {
        launchBrowserAndLogin(loginParameters);
    }

    @Step
    public static void launchBrowserAndLogin(
            Constants.LoginParameters loginParameters
    ) {
        try {
            launchBrowserAndUrl(loginParameters.getUrl(),Constants.Tags.BY_BODY);
            LoginUtil.login(loginParameters);
        } catch (Exception | AssertionError ex) {
            System.out.println("Failed to log in, will attempt again");
            ex.printStackTrace();
            Allure.addAttachment("Initial login failure stack", "text/plain", ExceptionUtils.getStackTrace(ex));

            // Tear down and try again to handle the case where the browser session gets messed up
            TestDriver.tearDown();

            launchBrowserAndUrl(loginParameters.getUrl(),Constants.Login.BY_LOGIN_BUTTON);
            LoginUtil.login(loginParameters);
        }
    }
}
