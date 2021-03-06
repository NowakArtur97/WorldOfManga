package com.NowakArtur97.WorldOfManga.feature.manga.seleniumTest;

import com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.feature.user.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("MangaRatingUI_Tests")
@DirtiesContext
class MangaRatingUITest extends SeleniumUITest {

    private MangaList mangaList;

    private LoginPage loginPage;

    private void launchBrowser(Browser browser, String language) {

        setUp(browser, language);

        mangaList = new MangaList(webDriver, mainUrl + localServerPort);

        loginPage = new LoginPage(webDriver, mainUrl + localServerPort);
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_rate_manga_on_main_page_should_show_updated_rating(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.chooseLastManga();

        int mangaRating = 5;

        mangaList.rateLastManga(mangaRating);

        assertAll(() -> assertTrue(mangaList.getLastMangaRating().contains(String.valueOf(mangaRating))
                        || mangaList.getLastMangaCardText().contains(String.valueOf(mangaRating))
                        || !mangaList.getLastMangaCardText().contains("0.0"),
                () -> "should show manga rating"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_rate_manga_should_show_rating_on_user_manga_list(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        int mangaRating = 5;

        mangaList.rateLastManga(mangaRating);

        assertAll(() -> assertTrue(mangaList.getLastMangaRating().contains(String.valueOf(mangaRating))
                        || mangaList.getLastMangaCardText().contains(String.valueOf(mangaRating))
                        || !mangaList.getLastMangaCardText().contains("0.0"),
                () -> "should show manga rating on manga list"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_rate_manga_second_time_should_show_updated_rating(Browser browser, String language) {

        launchBrowser(browser, language);

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("user", "user");

        mangaList.chooseLastManga();

        int firstMangaRating = 5;

        mangaList.rateLastManga(firstMangaRating);

        mangaList.chooseLastManga();

        int secondMangaRating = 4;

        mangaList.rateLastManga(secondMangaRating);

        assertAll(
                () -> assertFalse(mangaList.getLastMangaRating().contains(String.valueOf(firstMangaRating)),
                        () -> "shouldn't show old manga rating"),
                () -> assertTrue(mangaList.getLastMangaRating().contains(String.valueOf(secondMangaRating))
                                || mangaList.getLastMangaCardText().contains(String.valueOf(secondMangaRating))
                                || !mangaList.getLastMangaCardText().contains("0.0"),
                        () -> "should show updated manga rating"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0} | Language Version: {1}")
    @MethodSource("setBrowserAndLanguageBasedOnProfile")
    void when_not_logged_user_try_to_rate_manga_should_show_login_form(Browser browser, String language) {

        launchBrowser(browser, language);

        mangaList.loadMangaList(languageVersion);

        mangaList.chooseManga(0);

        int mangaRating = 5;

        mangaList.rateFirstManga(mangaRating);

        assertAll(() -> assertTrue(loginPage.isUserOnLoginPage(), () -> "should show login page"));
    }
}
