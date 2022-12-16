package com.appium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class Component extends ElementHandler {
    protected final String xPathToSelf;

    protected Component(WebDriver driver, String xPath) {
        super(driver);
        this.xPathToSelf = xPath;
    }

    public String getText() {
        return super.getText(selfBy());
    }

    public String waitForText(Duration maxWait) {
        return super.waitForText(selfBy(), maxWait);
    }

    protected String getChildText(String xSubPath) {
        return super.getText(subPath(xSubPath));
    }

    protected String waitForChildText(String xSubPath, Duration maxWait) {
        return super.waitForText(subPath(xSubPath), maxWait);
    }

    protected int countChild(String xSubPath) {
        return super.getElements(subPath(xSubPath)).size();
    }

    protected int waitToCountChild(String xSubPath, Duration maxWait) {
        return super.waitForElements(subPath(xSubPath), maxWait).size();
    }

    public void click() {
        super.click(selfBy());
    }

    public void waitToClick(Duration maxWait) {
        super.waitToClick(selfBy(), maxWait);
    }

    protected void clickChild(String xSubPath) {
        super.click(subPath(xSubPath));
    }

    protected void waitToClickChild(String xSubPath, Duration maxWait) {
        super.waitToClick(subPath(xSubPath), maxWait);
    }

    public void sendKey(String keys) {
        super.sendKey(selfBy(), keys);
    }

    public void waitToSendKey(String keys, Duration maxWait) {
        super.waitToSendKey(selfBy(), keys, maxWait);
    }

    protected void sendKeyToChild(String xSubPath, String keys) {
        super.sendKey(subPath(xSubPath), keys);
    }

    protected void waitToSendKeyToChild(String xSubPath, String keys, Duration maxWait) {
        super.waitToSendKey(subPath(xSubPath), keys, maxWait);
    }

    public boolean isPresent() {
        return super.isPresent(selfBy());
    }

    public boolean waitToBePresent(Duration maxWait) {
        return super.waitToBePresent(selfBy(), maxWait);
    }

    protected boolean isChildPresent(String xSubPath) {
        return super.isPresent(subPath(xSubPath));
    }

    protected boolean waitForChildToBePresent(String xSubPath, Duration maxWait) {
        return super.waitToBePresent(subPath(xSubPath), maxWait);
    }

    public boolean isNotPresent() {
        return super.isNotPresent(selfBy());
    }

    public boolean waitToBeNotPresent(Duration maxWait) {
        return super.waitToBeNotPresent(selfBy(), maxWait);
    }

    protected boolean isChildNotPresent(String xSubPath) {
        return super.isNotPresent(subPath(xSubPath));
    }

    protected boolean waitForChildToBeNotPresent(String xSubPath, Duration maxWait) {
        return super.waitToBeNotPresent(subPath(xSubPath), maxWait);
    }

    private By selfBy() {
        return new By.ByXPath(xPathToSelf);
    }

    private By subPath(String xSubPath) {
        return new By.ByXPath(xPathToSelf + xSubPath);
    }
}
