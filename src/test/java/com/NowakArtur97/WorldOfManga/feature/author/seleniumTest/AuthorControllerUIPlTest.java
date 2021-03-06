package com.NowakArtur97.WorldOfManga.feature.author.seleniumTest;

import com.NowakArtur97.WorldOfManga.testUtil.enums.Browser;
import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.extension.ScreenshotWatcher;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource({"classpath:/validation/messages_pl.properties", "classpath:/pageContent/messages_pl.properties"})
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("AuthorControllerUIPl_Tests")
@DirtiesContext
class AuthorControllerUIPlTest extends AuthorControllerUITest {

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_correct_author_creation_with_all_fields_should_add_author(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        String fullName = "FirstName LastName " + browser.name();

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        authorFormPage.clickAddOrUpdateAuthorLinkButton();

        authorFormPage.fillMandatoryAuthorFormFields(fullName);

        assertAll(() -> assertEquals(0, authorFormPage.countFailureMessages(),
                () -> "shouldn't have errors"),
                () -> assertTrue(authorService.isAuthorAlreadyInDatabase(fullName),
                        () -> "should save author in database"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_incorrect_author_creation_with_full_name_blank_should_have_errors(Browser browserForTest) {

        String fullName = "";

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        authorFormPage.clickAddOrUpdateAuthorLinkButton();

        authorFormPage.fillMandatoryAuthorFormFields(fullName);

        assertAll(() -> assertEquals(1, authorFormPage.countFailureMessages(), () -> "should have one error"),
                () -> assertTrue(authorFormPage.getFormBoxText().contains(authorFulNameNotBlankMessage),
                        () -> "should show full name is a required field message"),
                () -> assertEquals(fullName, authorFormPage.getFullName(), () -> "should show incorrect full name"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_incorrect_author_creation_with_too_long_fields_should_have_errors(Browser browserForTest) {

        String fullName = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        loginPage.loadLoginView(languageVersion);

        loginPage.fillMandatoryLoginFields("admin", "admin");

        authorFormPage.clickAddOrUpdateAuthorLinkButton();

        authorFormPage.fillMandatoryAuthorFormFields(fullName);

        assertAll(() -> assertEquals(1, authorFormPage.countFailureMessages(), () -> "should have one error"),
                () -> assertTrue(authorFormPage.getFormBoxText().contains(authorFullNameSizeMessage),
                        () -> "should show full name is too long message"),
                () -> assertEquals(fullName, authorFormPage.getFullName(), () -> "should show incorrect full name"));
    }
}
