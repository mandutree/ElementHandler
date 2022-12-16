package com.appium;

import org.openqa.selenium.WebDriver;

public abstract class Page extends ElementHandler {
    protected Page(WebDriver driver) {
        super(driver);
    }
}
