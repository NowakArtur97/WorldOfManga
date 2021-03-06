package com.NowakArtur97.WorldOfManga.feature.manga.seleniumPOM;

import com.NowakArtur97.WorldOfManga.testUtil.enums.LanguageVersion;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumPageObjectModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MangaFormPage extends SeleniumPageObjectModel {

    private static final String RESOURCE_PATH = "/admin/addOrUpdateManga";

    private final static String projectPath = System.getProperty("user.dir");

    private final String exampleImagePath;

    private static final String TITLE_EN_ID = "enTranslation.title";
    private static final String DESCRIPTION_EN_ID = "enTranslation.description";
    private static final String TITLE_PL_ID = "plTranslation.title";
    private static final String DESCRIPTION_PL_ID = "plTranslation.description";
    private static final String IMAGE_ID = "image";
    private static final String AUTHORS_NAME = "authors";
    private static final String GENRES_NAME = "genres";
    private static final String FORM_GROUP_SHOW_CLASS = "form__label--show";
    private static final String FORM_MESSAGE_FAILURE_CLASS = "form__message--failure";
    private static final String FORM_BOX_NAME = "addOrUpdateMangaForm";
    private static final String SUBMIT_MANGA_NAME = "addOrUpdateMangaSubmitBtn";
    private static final String ADD_OR_UPDATE_MANGA_LINK = "//a[@href='/admin/addOrUpdateManga']";

    @FindBy(id = TITLE_EN_ID)
    private WebElement titleEnInput;

    @FindBy(id = DESCRIPTION_EN_ID)
    private WebElement descriptionEnInput;

    @FindBy(id = TITLE_PL_ID)
    private WebElement titlePlInput;

    @FindBy(id = DESCRIPTION_PL_ID)
    private WebElement descriptionPlInput;

    @FindBy(id = IMAGE_ID)
    private WebElement imageInput;

    @FindBy(name = AUTHORS_NAME)
    private List<WebElement> authorsCheckboxes;

    @FindBy(name = GENRES_NAME)
    private List<WebElement> genresCheckboxes;

    @FindBy(className = FORM_GROUP_SHOW_CLASS)
    private List<WebElement> groupHiddenOptions;

    @FindBy(className = FORM_MESSAGE_FAILURE_CLASS)
    private List<WebElement> failureMessages;

    @FindBy(name = FORM_BOX_NAME)
    private WebElement formBox;

    @FindBy(name = SUBMIT_MANGA_NAME)
    private WebElement submitButton;

    @FindBy(xpath = ADD_OR_UPDATE_MANGA_LINK)
    private WebElement addOrUpdateMangaLink;

    public MangaFormPage(WebDriver webDriver, String mainUrl, String exampleImagePath) {

        super(webDriver, mainUrl);
        this.exampleImagePath = exampleImagePath;
    }

    public void loadMangaForm(LanguageVersion ver) {

        super.connectTo(RESOURCE_PATH + ver.getLangUrl());
    }

    public String getEnTitle() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(TITLE_EN_ID)));

        return titleEnInput.getAttribute("value");
    }

    private void setEnTitle(String enTitle) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(TITLE_EN_ID)));

        titleEnInput.click();
        titleEnInput.clear();
        titleEnInput.sendKeys(enTitle);
    }

    public String getEnDescription() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DESCRIPTION_EN_ID)));

        return descriptionEnInput.getAttribute("value");
    }

    private void setEnDescription(String enDescription) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DESCRIPTION_EN_ID)));

        descriptionEnInput.click();
        descriptionEnInput.clear();
        descriptionEnInput.sendKeys(enDescription);
    }

    public String getPlTitle() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(TITLE_PL_ID)));

        return titlePlInput.getAttribute("value");
    }

    private void setPlTitle(String plTitle) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(TITLE_PL_ID)));

        titlePlInput.click();
        titlePlInput.clear();
        titlePlInput.sendKeys(plTitle);
    }

    public String getPlDescription() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DESCRIPTION_PL_ID)));

        return descriptionPlInput.getAttribute("value");
    }

    private void setPlDescription(String plDescription) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DESCRIPTION_PL_ID)));

        descriptionPlInput.click();
        descriptionPlInput.clear();
        descriptionPlInput.sendKeys(plDescription);
    }

    private void addImage() {

        imageInput.sendKeys(projectPath + exampleImagePath);
    }

    private void clickAuthorCheckbox(int index) {

        authorsCheckboxes.get(index).click();
    }

    private void showAuthors() {

        useJavaScriptToClickElement(groupHiddenOptions.get(0));
    }

    public boolean isFirstAuthorCheckboxSelected() {

        return authorsCheckboxes.get(0).isSelected();
    }

    public boolean isSecondAuthorCheckboxSelected() {

        return authorsCheckboxes.get(1).isSelected();
    }

    private void clickGenreCheckbox(int index) {

        genresCheckboxes.get(index).click();
    }

    private void showGenres() {

        groupHiddenOptions.get(1).click();
    }

    public boolean isSecondGenreCheckboxSelected() {

        return genresCheckboxes.get(1).isSelected();
    }

    private void clickSubmitMangaFormButton() {

        submitButton.click();
    }

    public void clickAddOrUpdateMangaLinkButton() {

        useJavaScriptToClickElement(addOrUpdateMangaLink);
    }

    public String getFormBoxText() {

        return formBox.getText();
    }

    public int countFailureMessages() {

        return failureMessages.size();
    }

    public void fillMandatoryMangaFormFields(String enTitle, String enDescription, String plTitle, String plDescription,
                                             boolean selectAuthor, boolean selectGenres, boolean addImage) {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(SUBMIT_MANGA_NAME)));

        setEnTitle(enTitle);

        setEnDescription(enDescription);

        setPlTitle(plTitle);

        setPlDescription(plDescription);

        if (selectAuthor) {

            showAuthors();

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(AUTHORS_NAME)));

            clickAuthorCheckbox(1);
        }

        if (selectGenres) {

            showGenres();

            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(GENRES_NAME)));

            clickGenreCheckbox(1);
        }

        if (addImage) {
            addImage();
        }

        clickSubmitMangaFormButton();
    }

    public boolean isUserOnMangaFormPage() {

        return webDriver.getCurrentUrl().contains(RESOURCE_PATH);
    }
}
