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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
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
        // Store the handles of all open windows
        var windowHandles = TestDriver.getDriver().getWindowHandles();
        String mainWindowHandle = TestDriver.getDriver().getWindowHandle();

        // Iterate through the handles to switch to the new window
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
    public static void launchBrowserAndUrl(
            String url,
            By elementToBeClickable
    ) {
        var driver = TestDriver.getDriver();

        // Note: Below line unexpectedly logout ITK-WebUI when we switch to Inventory for device creation
        //TestDriver.clearCookies();

        var driverOptions = driver.manage();

        driverOptions
                .timeouts()
                .pageLoadTimeout(40, TimeUnit.SECONDS)
                .implicitlyWait(0, TimeUnit.SECONDS);

        goToUrlAndWaitForInitialPageLoad(url, elementToBeClickable);
    }

    public static void goToUrlAndWaitForInitialPageLoad(
            String url,
            By elementTobeClickable
    ) {
        goToUrl(url);
        waitForInitialPageLoad(elementTobeClickable);
    }

    @Step( "go to {0}" )
    public static void goToUrl(String url) {
        try {
            TestDriver.getDriver().get(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to go to url: \"" + url + "\"", e);
        }
    }

    @Step
    public static void waitForInitialPageLoad(By elementTobeClickable) {
        waitForElementToBeClickable(elementTobeClickable);
    }

    @Step( "element {0} to be clickable" )
    public static WebElement waitForElementToBeClickable(By by) {
        var wait = setupWait();
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }



    @Step
    public static <T> T waitUntilNotNullOrFalse(
            String message,
            Supplier<T> func
    ) {
        var wait = setupWait();
        return (
                message != null
                        ? wait.withMessage(message)
                        : wait
        )
                .until(unused -> func.get());
    }

    @Step
    public static <T> T waitUntilNotNullOrFalse(Supplier<T> func) {
        return waitUntilNotNullOrFalse(null, func);
    }



    public static Object mapJsonToObject(
            String jsonSource,
            Class expectedClass
    ) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var mappedObject = mapper.readTree(jsonSource);
        return mapper.treeToValue(mappedObject, expectedClass);
    }

    public static String getElementsText(List<WebElement> elements) {
        return elements.stream().map(webElement -> getElementText(webElement)).collect(Collectors.joining());
    }
    @Step( "get text of {0}" )
    public static String getElementText(WebElement element) {
        return element.getText();
    }
    @Step( "get text of {0}" )
    public static String getElementText(By by) {
        return findElement(by).getText().trim();
    }

    public static List<WebElement> getWebElementsBysForIdPattern(String idPattern) {
        return getElements(Constants.Common.generateByForIdPattern(idPattern));
    }

    @Step( "get {0}" )
    public static List<WebElement> getElements(By by) {
        var wait = setupWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return TestDriver.getDriver().findElements(by);
    }

    @Step
    public static int getElementCount(By by) {
        // Don't wait so that it will not fail if no results are expected
        return TestDriver.getDriver()
                .findElements(by)
                .size();
    }

    public static void clickOnElement(By by) {
        clickOnElement(by, by + " did not become clickable");
    }

    public static void clickOnElement(
            By by,
            String errorMessage
    ) {
        try {
            var wait = setupWait();
            wait.until(ExpectedConditions.elementToBeClickable(by));

            executePrimitive(() -> findElement(by).click());
        } catch (TimeoutException | NoSuchElementException ex) {
            Assert.fail(errorMessage, ex);
        }
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


    public static void clearAndSetElementText(
            By by,
            String text
    ) {
        var element = findElement(by);
        element.clear();
        element.sendKeys(text);

        // Workaround for https://github.com/angular/protractor/issues/3196#issuecomment-227788976
        var valueOfElement = element.getText();
        valueOfElement = valueOfElement.length() < 1
                ? getElementValue(by)
                : valueOfElement;
        if (!text.equals(valueOfElement)) {
            element.clear();
            sendCharactersToElement(element, text.toCharArray());
        }
    }
    @Step( "get value of {0}" )
    public static String getElementValue(By by) {
        return findElement(by).getAttribute("value");
    }
    private static void sendCharactersToElement(
            WebElement element,
            char[] charsOfText
    ) {
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
        List.of(bys)
                .forEach(by ->
                        clearField(by));
    }

    @Step
    public static void clearTextArea(By by) {
        sendTextToElement(by, Keys.CONTROL + "a");
        sendTextToElement(by, Keys.DELETE.toString());
    }
    @Step
    public static void sendTextToElement(
            By by,
            String text
    ) {
        var element = findElement(by);
        element.sendKeys(text);
    }


    @Step
    public static String getImageBase64(
            By by,
            String attribute
    ) {
        return findElement(by)
                .getAttribute(attribute)
                .replaceAll("^.*base64,", "");
    }

    @Step
    public static String getFileBase64(File file) {
        try {
            return Base64
                    .getEncoder()
                    .encodeToString(
                            FileUtils.readFileToByteArray(file)
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Step
    public static void chooseFile(
            By byFilePicker,
            File file
    ) {
        // Don't use findElement method since this won't be visible
        var filePicker = TestDriver.getDriver().findElement(byFilePicker);

        var path = file.getAbsolutePath();
        filePicker.sendKeys(path);
    }

    @Step( "Right Click {0}" )
    public static void rightClickOnElement(By byButton) {
        var actions = new Actions(TestDriver.getDriver());
        var elementLocator = findElement(byButton);
        actions.contextClick(elementLocator).perform();
    }

    @Step( "wait for {0} to disappear" )
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
        // Don't use UtilsSet.findElements as we don't want to wait for it to be visible
        var elementsMatchingDisplayed = TestDriver.getDriver()
                .findElements(by)
                .stream()
                .filter(WebElement::isDisplayed)
                .count();
        return elementsMatchingDisplayed > 0;
    }
    @Step( "wait for {0} to disappear" )
    public static void waitForElementToDisappear(
            By by,
            int timeoutSeconds
    ) {
        try {
            var wait = setupWait(timeoutSeconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (TimeoutException timeoutException) {
            Assert.fail(by + " did not disappear after " + timeoutSeconds + "s", timeoutException);
        }
    }

    @Step( "wait for element {0} to be visible and continue even if visibility failed" )
    public static void waitForElementToBeVisibleAndContinue(
            By by
    ) {
        try {
            var wait = setupWait(UtilsSet.WAIT_FOR_GRID_LOADER_SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException | NoSuchElementException ignored) {
            System.out.println(by + " did not become visible and will continue");
        }
    }

    @Step( "wait for {0} to have a value" )
    public static void waitForElementToPopulateWithSomeValue(By by) {
        try {
            var wait = setupWait();
            wait.until((ExpectedCondition<Boolean>) driver -> !getElementText(by).isEmpty());
        } catch (TimeoutException timeoutException) {
            Assert.fail(by + " did not get populated", timeoutException);
        }
    }

    @Step( "wait for {0} to have attribute {1}" )
    public static void waitForElementToHaveAttribute(
            By by,
            String attribute,
            String value
    ) {
        var wait = setupWait();
        wait.until(ExpectedConditions.attributeToBe(by, attribute, value));
    }

    @Step( "wait for {0} to contain class {1}" )
    public static void waitForElementToContainClass(
            WebElement element,
            String value
    ) {
        var wait = setupWait();
        wait.until(ExpectedConditions.attributeContains(element, "class", value));
    }

    @Step( "wait for {0} to contain style {1}" )
    public static void waitForElementToContainStyle(
            By by,
            String value
    ) {
        var wait = setupWait();
        wait.until(ExpectedConditions.attributeContains(findElement(by), "style", value));
    }

    @Step( "wait for {0} to be disabled" )
    public static void waitForElementToBeDisabled(By by) {
        var wait = setupWait();
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOfElementLocated(by),
                        ExpectedConditions.not(
                                ExpectedConditions.elementToBeClickable(by)
                        )
                )
        );
    }

    @Step( "wait for {0} to be visible" )
    public static void waitForElementToBeVisible(
            By by,
            int timeoutSeconds
    ) {
        waitForElementToBeVisible(by, timeoutSeconds, null);
    }

    @Step( "wait for element {0}" )
    public static void waitForElementToBeVisible(
            By by,
            int timeoutSeconds,
            Duration pollingInterval
    ) {
        try {
            var wait = setupWait(timeoutSeconds);
            if (pollingInterval != null) {
                wait.pollingEvery(pollingInterval);
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException | NoSuchElementException ex) {
            if (isDisplayed(by)) {
                return;
            }
            Assert.fail(by + " did not become visible", ex);
        }
    }

    @Step( "wait for stale {0} to be visible" )
    public static void waitForStaleElementToBeVisible(By by) {
        var wait = setupWait();
        var element = TestDriver.getDriver().findElement(by);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
    }

    @Step( "wait for {0} to have value" )
    public static void waitForValueToBePresentInElement(By byElement) {
        var wait = setupWait();
        wait.until(
                (ExpectedCondition<Boolean>) driver -> findElement(byElement).getAttribute("value").length() != 0);
    }
    @Step( "wait for {0} to have text" )
    public static void waitForElementToHaveTextPresent(By byElement) {
        setupWait()
                .until(driver -> getElementText(byElement).length() != 0);
    }
    public static void waitForElementToHaveExpectedText(
            By byElement,
            String expectedText
    ) {
        setupWait()
                .until(driver -> getElementText(byElement).equalsIgnoreCase(expectedText));
    }

    @Step( "click {0}" )
    public static void clickOnElementOnceClickable(By by) {
        waitForElementToBeClickable(by);
        clickOnElement(by);
    }

    @Step
    public static boolean isClickable(By by) {
        return findElement(by).isEnabled();
    }
    @Step
    public static boolean isVisible(By by) {
        return isDisplayed(by);
    }

    @Step
    public static void assertElementTextIsNotEqual(
            By by,
            String text
    ) {
        assertThat(getElementText(by)).isNotEqualTo(text);
    }

    @Step
    public static void assertElementLabelText(
            By labeledElementBy,
            String expectedLabel
    ) {
        var idOfElement = findElement(labeledElementBy).getAttribute("id");
        assertThat(getElementText(By.xpath("//label[@for='"
                + idOfElement
                + "']"))).isEqualToIgnoringCase(expectedLabel);
    }

    @Step( "get element list text {0}" )
    public static List<String> getElementListTexts(By by) {
        return findElements(by)
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }
    @Step
    public static List<WebElement> findElements(By by) {
        try {
            var wait = setupWait();
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException | NoSuchElementException ex) {
            System.out.println(by + " did not become visible");
            return new ArrayList<>();
        }

        return TestDriver.getDriver().findElements(by);
    }

    @Step
    public static void clickOnElementWithJS(By element) {
        var jsExecutor = (JavascriptExecutor) TestDriver.getDriver();
        var jsElement = TestDriver.getDriver().findElement(element);
        jsExecutor.executeScript("arguments[0].click();", jsElement);
    }

    @Step
    public static void clearBrowserStorageForKey(String keyToClear) {
        var jsExecutor = (JavascriptExecutor) TestDriver.getDriver();
        jsExecutor.executeScript("localStorage.removeItem('" + keyToClear + "')");
        jsExecutor.executeScript("sessionStorage.removeItem('" + keyToClear + "')");
    }

    @Step
    public static void scrollToTop() {
        TestDriver.getDriver()
                .findElement(Constants.Tags.BY_BODY)
                .sendKeys(Keys.HOME);

        waitUntilNotNullOrFalse(UtilsSet::isAtTopOfPage);
    }
    @Step
    public static void scrollToBottom() {
        TestDriver.getDriver()
                .findElement(Constants.Tags.BY_BODY)
                .sendKeys(Keys.END);

        runScriptSync("window.scrollTo(0,document.body.scrollHeight);");

        waitUntilNotNullOrFalse(UtilsSet::isAtBottomOfPage);
    }
    private static boolean isAtBottomOfPage() {
        // https://stackoverflow.com/a/40370876/877306
        return (boolean) runScriptSync(
                "return (window.innerHeight + window.pageYOffset) >= document.body.offsetHeight - 2;");
    }
    private static boolean isAtTopOfPage() {
        return (boolean) runScriptSync("return window.pageYOffset == 0;");
    }

    @Step
    public static void setInputValue(
            String byNoteId,
            String note
    ) {
        var jsExecutor = (JavascriptExecutor) TestDriver.getDriver();
        jsExecutor.executeScript("document.getElementById('" + byNoteId + "').value='" + note + "'");
        jsExecutor.executeScript("return document.getElementById('" + byNoteId + "').value");
    }

    @Step
    public static void sendTextToElementId(
            String idValue,
            String text
    ) {
        var jsExecutor = (JavascriptExecutor) TestDriver.getDriver();
        jsExecutor.executeScript("document.getElementById('" + idValue + "').value='" + text + "'");
        jsExecutor.executeScript("return document.getElementById('" + idValue + "').value");
    }



    @Step
    public static String getDropDownValue(By by) {
        var comboBox = new Select(getFirstElement(by));
        return comboBox.getFirstSelectedOption().getText();
    }
    @Step( "get first {0}" )
    public static WebElement getFirstElement(By by) {
        var elements = getElements(by);
        if (elements.size() == 0) {
            Assert.fail("Failed to locate " + by);

        }
        return elements.get(0);
    }
}
