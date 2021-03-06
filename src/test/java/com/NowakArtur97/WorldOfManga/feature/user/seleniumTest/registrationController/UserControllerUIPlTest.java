package com.NowakArtur97.WorldOfManga.feature.user.seleniumTest.registrationController;

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
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(ScreenshotWatcher.class)
@TestPropertySource({"classpath:/validation/messages_pl.properties", "classpath:/pageContent/messages_pl.properties"})
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("RegistrationControllerUIPl_Tests")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerUIPlTest extends UserControllerUITest {

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_username_is_already_in_use_should_have_error(Browser browserForTest) {

        String username = "user";
        String email = "email@email.com";
        boolean areTermsAccepted = true;

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        registrationPage.loadRegistrationView(languageVersion);

        registrationPage.fillMandatoryRegistrationFields(username, "password", "password", email, areTermsAccepted);

        assertAll(() -> assertTrue(registrationPage.isUserOnRegistrationPage(), () -> "should show registration page"),
                () -> assertEquals(registrationPage.countFailureMessages(), 1, () -> "should have error"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(usernameInUseMessage),
                        () -> "should show username is already in use message"),
                () -> assertEquals(username, registrationPage.getUsername(), () -> "should show incorrect username"),
                () -> assertEquals(email, registrationPage.getEmail(), () -> "should show email"),
                () -> assertTrue(registrationPage.getConfirmTerms(), () -> "should show accepted terms"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_email_is_already_in_use_should_have_error(Browser browserForTest) {

        String username = "username";
        String email = "user@email.com";
        boolean areTermsAccepted = true;

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        registrationPage.loadRegistrationView(languageVersion);

        registrationPage.fillMandatoryRegistrationFields(username, "password", "password", email, areTermsAccepted);

        assertAll(() -> assertTrue(registrationPage.isUserOnRegistrationPage(), () -> "should show registration page"),
                () -> assertEquals(registrationPage.countFailureMessages(), 1, () -> "should have error"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(emailInUseMessage),
                        () -> "should show email is already in use message"),
                () -> assertEquals(username, registrationPage.getUsername(), () -> "should show username"),
                () -> assertEquals(email, registrationPage.getEmail(), () -> "should show incorrect email"),
                () -> assertTrue(registrationPage.getConfirmTerms(), () -> "should show accepted terms"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_incorrect_registration_with_mandatory_fields_should_have_errors(Browser browserForTest) {

        String username = "";
        String email = "";
        boolean areTermsAccepted = false;

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        registrationPage.loadRegistrationView(languageVersion);

        registrationPage.fillMandatoryRegistrationFields(username, "password", "password1", email, areTermsAccepted);

        assertAll(() -> assertTrue(registrationPage.isUserOnRegistrationPage(), () -> "should show registration page"),
                () -> assertEquals(registrationPage.countFailureMessages(), 4, () -> "should have three errors"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(usernameNotBlankMessage),
                        () -> "should show username is a required field message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(passwordMatchingFieldsMessage),
                        () -> "should password fields must match message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(emailNotBlankMessage),
                        () -> "should show email is a required field message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(usernameTermsMessage),
                        () -> "should show terms not accepted message"),
                () -> assertEquals(username, registrationPage.getUsername(), () -> "should show incorrect username"),
                () -> assertEquals(email, registrationPage.getEmail(), () -> "should show incorrect email"),
                () -> assertFalse(registrationPage.getConfirmTerms(), () -> "should show not accepted terms"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_incorrect_registration_fields_size_with_mandatory_fields_should_have_errors(Browser browserForTest) {

        String username = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp";
        String email = "email,";
        boolean areTermsAccepted = false;

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        registrationPage.loadRegistrationView(languageVersion);

        registrationPage.fillMandatoryRegistrationFields(username, "", "password", email, areTermsAccepted);

        assertAll(() -> assertTrue(registrationPage.isUserOnRegistrationPage(), () -> "should show registration page"),
                () -> assertEquals(registrationPage.countFailureMessages(), 4, () -> "should have four errors"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(usernameSizeMessage),
                        () -> "should show username size exceed message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(passwordMatchingFieldsMessage),
                        () -> "should password fields must match message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(emailFormatMessage),
                        () -> "should show invalid email format message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(usernameTermsMessage),
                        () -> "should show terms not accepted message"),
                () -> assertEquals(username, registrationPage.getUsername(), () -> "should show incorrect username"),
                () -> assertEquals(email, registrationPage.getEmail(), () -> "should show incorrect email"),
                () -> assertFalse(registrationPage.getConfirmTerms(), () -> "should show not accepted terms"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_incorrect_registration_with_all_fields_should_have_errors(Browser browserForTest) {

        String username = "";
        String email = "";
        String password = "";
        String matchingPassword = "";
        boolean areTermsAccepted = true;
        String firstName = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp";
        String lastName = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp";

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        launchBrowser();

        registrationPage.loadRegistrationView(languageVersion);

        registrationPage.fillAllRegistrationFields(username, email, password, matchingPassword, firstName, lastName,
                areTermsAccepted);

        assertAll(() -> assertTrue(registrationPage.isUserOnRegistrationPage(), () -> "should show registration page"),
                () -> assertEquals(registrationPage.countFailureMessages(), 6, () -> "should have six errors"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(usernameNotBlankMessage),
                        () -> "should show username is a required field message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(passwordFieldsNotBlankMessage),
                        () -> "should password fields must match message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(firstNameSizeMessage),
                        () -> "should show exceeded maximal length of first name message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(lastNameSizeMessage),
                        () -> "should show exceeded maximal length of last name message"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(emailNotBlankMessage),
                        () -> "should show email is a required field message"),
                () -> assertEquals(username, registrationPage.getUsername(), () -> "should show incorrect username"),
                () -> assertEquals(email, registrationPage.getEmail(), () -> "should show incorrect email"),
                () -> assertEquals(firstName, registrationPage.getFirstName(),
                        () -> "should show incorrect first name"),
                () -> assertEquals(lastName, registrationPage.getLastName(), () -> "should show incorrect last name"),
                () -> assertTrue(registrationPage.getConfirmTerms(), () -> "should show accepted terms"));
    }

    @ParameterizedTest(name = "{index}: Browser: {0}")
    @MethodSource("setBrowserBasedOnProfile")
    void when_correct_registration_with_all_fields_should_register_user(Browser browserForTest) {

        languageVersion = LanguageVersion.PL;
        browser = browserForTest;

        String username = "user name 111" + browser.name();

        launchBrowser();

        registrationPage.loadRegistrationView(languageVersion);

        registrationPage.fillAllRegistrationFields(username, "password", "password",
                browser.name() + "user111@email.com", "firstName", "lastName", true);

        assertAll(() -> assertTrue(registrationPage.isUserOnRegistrationPage(), () -> "should show registration page"),
                () -> assertTrue(userService.isUsernameAlreadyInUse(username), () -> "should save user in database"),
                () -> assertTrue(registrationPage.getFormBoxText().contains(afterRegistrationMessage),
                        () -> "should show success registration message"));
    }
}