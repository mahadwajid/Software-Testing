package com.se;

import com.se.config.ConfigHelper;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class SeRetryAnalyzer implements IRetryAnalyzer {
    private int _currentRetryCount = 0;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (iTestResult.isSuccess()) {
            return false;
        }

        iTestResult.setStatus(ITestResult.FAILURE);

        _currentRetryCount++;
        return _currentRetryCount <= ConfigHelper.getInstance().getMaxRetriesOnFailure();
    }
}
