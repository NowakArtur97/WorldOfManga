package com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends SeleniumPageObjectModel {

    private static final String LOGOUT_LINK = "//a[@href='/auth/logout']";

    @FindBy(xpath = LOGOUT_LINK)
    private WebElement logoutLink;

    public LogoutPage(WebDriver webDriver, String mainUrl) {

        super(webDriver, mainUrl);
    }

    public void signOut(Browser browser) {

        switch (browser) {
            case FIREFOX:
                logoutLink.click();
                break;
            case CHROME:
            default:
                useJavaScriptToClickElement(logoutLink);
        }
    }
}
