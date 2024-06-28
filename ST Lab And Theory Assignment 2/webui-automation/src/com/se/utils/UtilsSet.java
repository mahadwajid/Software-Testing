package com.se.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.TestDriver;
import com.se.config.Constants;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.se.TestDriver.runScriptSync;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsSet {

    private static final int WAIT_DEFAULT_TIMEOUT_SECONDS = 27;
    private static final int WAIT_FOR_GRID_LOADER_SECONDS = 5;
    public static final int IMPLICIT_WAIT_TIMEOUT_SECONDS = 20;
    public static SoftAssertions _softly = new SoftAssertions();

    public static WebDriverWait setupWait() {
        return setupWait(WAIT_DEFAULT_TIMEOUT_SECONDS);
    }

    public static WebDriverWait setupWait(int timeoutSeconds) {
        return new WebDriverWait(TestDriver.getDriver(), timeoutSeconds);
    }

    @Step
    public static void switchToNextWindow() {
        var windowHandles = TestDriver.getDriver().getWindowHandles();
        String mainWindowHandle = TestDriver.getDriver().getWindowHandle();

        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(mainWindowHandle)) {
                TestDriver.getDriver().switchTo().window(windowHandle);
                break;
            }
        }
    }

    @Step
    public static String getPageTitle() {
        try {
            return TestDriver.getDriver().getTitle();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get page title", e);
        }
    }

    @Step
    public static void launchBrowserAndUrl(String url, By elementToBeClickable) {
        var driver = TestDriver.getDriver();

        var driverOptions = driver.manage();
        driverOptions
                .timeouts()
                .pageLoadTimeout(40, TimeUnit.SECONDS)
                .implicitlyWait(0, TimeUnit.SECONDS);

        goToUrlAndWaitForInitialPageLoad(url, elementToBeClickable);
    }

    public static void goToUrlAndWaitForInitialPageLoad(String url, By elementToBeClickable) {
        goToUrl(url);
        waitForInitialPageLoad(elementToBeClickable);
    }

    @Step("go to {0}")
    public static void goToUrl(String url) {
        try {
            TestDriver.getDriver().get(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to go to url: \"" + url + "\"", e);
        }
    }

    @Step
    public static void waitForInitialPageLoad(By elementToBeClickable) {
        waitForElementToBeClickable(elementToBeClickable);
    }

    @Step("element {0} to be clickable")
    public static WebElement waitForElementToBeClickable(By by) {
        var wait = setupWait();
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    @Step
    public static <T> T waitUntilNotNullOrFalse(String message, Supplier<T> func) {
        var wait = setupWait();
        return (message != null ? wait.withMessage(message) : wait).until(unused -> func.get());
    }

    @Step
    public static <T> T waitUntilNotNullOrFalse(Supplier<T> func) {
        return waitUntilNotNullOrFalse(null, func);
    }

    public static Object mapJsonToObject(String jsonSource, Class<?> expectedClass) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var mappedObject = mapper.readTree(jsonSource);
        return mapper.treeToValue(mappedObject, expectedClass);
    }

    public static String getElementsText(List<WebElement> elements) {
        return elements.stream().map(UtilsSet::getElementText).collect(Collectors.joining());
    }

    @Step("get text of {0}")
    public static String getElementText(WebElement element) {
        return element.getText();
    }

    @Step("get text of {0}")
    public static String getElementText(By by) {
        return findElement(by).getText().trim();
    }

    public static List<WebElement> getWebElementsBysForIdPattern(String idPattern) {
        return getElements(Constants.Common.generateByForIdPattern(idPattern));
    }

    @Step("get {0}")
    public static List<WebElement> getElements(By by) {
        var wait = setupWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return TestDriver.getDriver().findElements(by);
    }

    @Step
    public static int getElementCount(By by) {
        return TestDriver.getDriver().findElements(by).size();
    }

    public static void clickOnElement(By by) {
        clickOnElement(by, by + " did not become clickable");
    }

    public static void clickOnElement(By by, String errorMessage) {
        try {
            var wait = setupWait();
            wait.until(ExpectedConditions.elementToBeClickable(by));
            executePrimitive(() -> findElement(by).click());
        } catch (TimeoutException | NoSuchElementException ex) {
            Assert.fail(errorMessage, ex);
        }
    }

    public static void waitForElementToBeVisible(By byQuizTitlesSection, int timeoutSeconds) {
    }

    private interface ITask {
        void task();
    }

    public static void executePrimitive(ITask task) {
        try {
            task.task();
        } catch (StaleElementReferenceException | ElementClickInterceptedException exception) {
            task.task();
        }
    }

    public static WebElement findElement(By by) {
        try {
            var wait = setupWait();
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException | NoSuchElementException ex) {
            Assert.fail(by + " did not become visible", ex);
        }

        return TestDriver.getDriver().findElement(by);
    }

    public static void clearAndSetElementText(By by, String text) {
        var element = findElement(by);
        element.clear();
        element.sendKeys(text);

        var valueOfElement = element.getText();
        valueOfElement = valueOfElement.length() < 1 ? getElementValue(by) : valueOfElement;
        if (!text.equals(valueOfElement)) {
            element.clear();
            sendCharactersToElement(element, text.toCharArray());
        }
    }

    @Step("get value of {0}")
    public static String getElementValue(By by) {
        return findElement(by).getAttribute("value");
    }

    private static void sendCharactersToElement(WebElement element, char[] charsOfText) {
        for (var ch : charsOfText) {
            element.sendKeys(String.valueOf(ch));
        }
    }

    @Step
    public static void clickBrowserBackButton() {
        TestDriver.getDriver().navigate().back();
    }

    @Step
    public static void clearField(By byElement) {
        var textField = findElement(byElement);
        textField.clear();
    }

    @Step
    public static void clearFields(By... bys) {
        for (By by : bys) {
            clearField(by);
        }
    }

    @Step
    public static void clearTextArea(By by) {
        sendTextToElement(by, Keys.CONTROL + "a");
        sendTextToElement(by, Keys.DELETE.toString());
    }

    @Step
    public static void sendTextToElement(By by, String text) {
        var element = findElement(by);
        element.sendKeys(text);
    }

    @Step
    public static String getImageBase64(By by, String attribute) {
        return findElement(by)
                .getAttribute(attribute)
                .replaceAll("^.*base64,", "");
    }

    @Step
    public static String getFileBase64(File file) {
        try {
            return Base64
                    .getEncoder()
                    .encodeToString(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Step
    public static void chooseFile(By byFilePicker, File file) {
        var filePicker = TestDriver.getDriver().findElement(byFilePicker);
        var path = file.getAbsolutePath();
        filePicker.sendKeys(path);
    }

    @Step("Right Click {0}")
    public static void rightClickOnElement(By byButton) {
        var actions = new Actions(TestDriver.getDriver());
        var elementLocator = findElement(byButton);
        actions.contextClick(elementLocator).perform();
    }

    @Step("wait for {0} to disappear")
    public static void waitForElementToDisappear(By by) {
        try {
            var wait = setupWait();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException timeoutException) {
            Assert.fail(by + " did not disappear", timeoutException);
        }
    }

    @Step
    public static boolean isDisplayed(By by) {
        var elementsMatchingDisplayed = TestDriver.getDriver().findElements(by);
        if (elementsMatchingDisplayed.isEmpty()) {
            return false;
        } else {
            return elementsMatchingDisplayed
                    .stream()
                    .allMatch(WebElement::isDisplayed);
        }
    }

    @Step
    public static void jsClick(By by) {
        runScriptSync("arguments[0].click();", TestDriver.getDriver().findElement(by));
    }

    @Step
    public static void waitForGridLoader() {
        try {
            var gridLoaderSelector = By.cssSelector(".slds-spinner_brand[role='status']");
            var wait = setupWait(WAIT_FOR_GRID_LOADER_SECONDS);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(gridLoaderSelector));
        } catch (TimeoutException timeoutException) {
            Assert.fail("Grid loader did not disappear", timeoutException);
        }
    }

    @Step
    public static void switchToLastWindow() {
        var windowHandles = TestDriver.getDriver().getWindowHandles();
        var currentWindowHandle = TestDriver.getDriver().getWindowHandle();

        var iterator = windowHandles.iterator();
        String lastWindowHandle = null;
        while (iterator.hasNext()) {
            lastWindowHandle = iterator.next();
        }

        if (lastWindowHandle != null && !lastWindowHandle.equals(currentWindowHandle)) {
            TestDriver.getDriver().switchTo().window(lastWindowHandle);
        }
    }

    @Step
    public static void switchToFirstWindow() {
        var windowHandles = TestDriver.getDriver().getWindowHandles();
        var iterator = windowHandles.iterator();

        if (iterator.hasNext()) {
            var firstWindowHandle = iterator.next();
            TestDriver.getDriver().switchTo().window(firstWindowHandle);
        }
    }

    @Step
    public static void selectDropdownOptionByText(By byDropdown, String visibleText) {
        var dropdown = findElement(byDropdown);
        var select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    @Step
    public static void selectDropdownOptionByValue(By byDropdown, String value) {
        var dropdown = findElement(byDropdown);
        var select = new Select(dropdown);
        select.selectByValue(value);
    }

    @Step
    public static void selectDropdownOptionByIndex(By byDropdown, int index) {
        var dropdown = findElement(byDropdown);
        var select = new Select(dropdown);
        select.selectByIndex(index);
    }

    @Step
    public static List<String> getDropdownOptions(By byDropdown) {
        var dropdown = findElement(byDropdown);
        var select = new Select(dropdown);
        return select.getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    public static void closeAllWindowsExceptCurrent() {
        var currentWindowHandle = TestDriver.getDriver().getWindowHandle();
        var windowHandles = TestDriver.getDriver().getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(currentWindowHandle)) {
                TestDriver.getDriver().switchTo().window(handle);
                TestDriver.getDriver().close();
            }
        }

        TestDriver.getDriver().switchTo().window(currentWindowHandle);
    }

    @Step
    public static void scrollToElement(By by) {
        var element = findElement(by);
        ((JavascriptExecutor) TestDriver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
