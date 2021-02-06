package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.logoutController;

import com.NowakArtur97.WorldOfManga.feature.mainPage.seleniumPOM.MainPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LogoutPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;

class LogoutControllerUITest extends SeleniumUITest {

    @Value("${header.myMangaWorld}")
    String userLoggedInMangaListOption;

    @Value("${header.signOut}")
    String userLoggedInSignOutOption;

    @Value("${header.signUp}")
    String headerRegistrationOption;

    @Value("${header.signIn}")
    String headerLoginOption;

    LogoutPage logoutPage;

    LoginPage loginPage;

    MainPage mainPage;

    @BeforeEach
    void setupPOM() {

        logoutPage = new LogoutPage(webDriver, mainUrl + serverPort);
        loginPage = new LoginPage(webDriver, mainUrl + serverPort);
        mainPage = new MainPage(webDriver, mainUrl + serverPort);
    }
}