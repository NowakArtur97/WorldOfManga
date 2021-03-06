package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumPageObjectModel {

    protected final static int TIME_TO_WAIT = 10;
    private final String mainUrl;

    protected final WebDriver webDriver;
    protected final WebDriverWait webDriverWait;

    protected SeleniumPageObjectModel(WebDriver webDriver, String mainUrl) {

        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(webDriver, TIME_TO_WAIT);
        PageFactory.initElements(factory, this);

        this.webDriver = webDriver;
        this.mainUrl = mainUrl;
        this.webDriverWait = new WebDriverWait(webDriver, TIME_TO_WAIT);
    }

    protected void connectTo(final String RESOURCE_PATH) {

        webDriver.navigate().to(mainUrl + RESOURCE_PATH);
    }

    protected void useJavaScriptToClickElement(WebElement element) {

        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    protected boolean isElementFound(WebElement element) {

        if (element == null) {
            return false;
        }

        boolean isElementFound;

        try {

            element.isDisplayed();

            isElementFound = true;

        } catch (NoSuchElementException ignored) {

            isElementFound = false;
        }

        return isElementFound;
    }
}
