package com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/";

    public static final String MAIN_PAGE_CLASS = "page";
    private static final String HEADER_CLASS = "header";
    private static final String FOOTER_CLASS = "footer";

    @FindBy(className = MAIN_PAGE_CLASS)
    private WebElement mainPage;

    @FindBy(className = HEADER_CLASS)
    private WebElement header;

    @FindBy(className = FOOTER_CLASS)
    private WebElement footer;

    public MainPage(WebDriver webDriver, String mainUrl) {

        super(webDriver, mainUrl);
    }

    public void loadMainView(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    public String getHeaderText() {

        return header.getAttribute("textContent");
    }

    public String getFooterText() {

        return footer.getText();
    }

    public String getMainPageText() {

        return mainPage.getText();
    }
}
