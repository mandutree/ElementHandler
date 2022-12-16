package com.appium;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class ElementHandler {
    protected final WebDriver driver;

    protected ElementHandler(WebDriver driver) {
        this.driver = driver;
    }

    protected String getText(By by) {
        return waitForText(by, Duration.ZERO);
    }

    protected String waitForText(By by, Duration maxWait) {
        return waitForElement(by, maxWait).getText();
    }

    protected void click(By by) {
        waitToClick(by, Duration.ZERO);
    }

    protected void waitToClick(By by, Duration maxWait) {
        waitForCondition(ExpectedConditions.elementToBeClickable(by), maxWait).click();
    }

    protected void sendKey(By by, String keys) {
        waitToSendKey(by, keys, Duration.ZERO);
    }

    protected void waitToSendKey(By by, String keys, Duration maxWait) {
        waitForElement(by, maxWait).sendKeys(keys);
    }

    protected boolean isPresent(By by) {
        return waitToBePresent(by, Duration.ZERO);
    }

    protected boolean waitToBePresent(By by, Duration maxWait) {
        try {
            waitForElement(by, maxWait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isNotPresent(By by) {
        return waitToBeNotPresent(by, Duration.ZERO);
    }

    protected boolean waitToBeNotPresent(By by, Duration maxWait) {
        try {
            waitForCondition(ExpectedConditions.invisibilityOfElementLocated(by), maxWait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected WebElement getElement(By by) {
        return getElements(by).get(0);
    }

    protected List<WebElement> getElements(By by) {
        return waitForCondition(ExpectedConditions.presenceOfAllElementsLocatedBy(by), Duration.ZERO);
    }

    protected WebElement waitForElement(By by, Duration maxWait) {
        return waitForElements(by, maxWait).get(0);
    }

    protected List<WebElement> waitForElements(By by, Duration maxWait) {
        return waitForCondition(ExpectedConditions.presenceOfAllElementsLocatedBy(by), maxWait);
    }

    protected WebElement waitForElement(ExpectedCondition<WebElement> condition, Duration maxWait) {
        return waitForCondition(condition, maxWait);
    }

    protected List<WebElement> waitForElements(ExpectedCondition<List<WebElement>> condition, Duration maxWait) {
        return waitForCondition(condition, maxWait);
    }

    private <T> T waitForCondition(ExpectedCondition<T> condition, Duration maxWait) {
        WebDriverWait wait = new WebDriverWait(this.driver, maxWait);
        return wait.until(condition);
    }
}
