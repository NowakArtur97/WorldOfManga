package com.NowakArtur97.WorldOfManga.controller.manga.seleniumTest.mangaRating;

import org.junit.jupiter.api.BeforeEach;

import com.NowakArtur97.WorldOfManga.controller.manga.seleniumPOM.MangaList;
import com.NowakArtur97.WorldOfManga.controller.unloggedUser.seleniumPOM.LoginPage;
import com.NowakArtur97.WorldOfManga.testUtil.selenium.SeleniumUITest;

public class MangaRatingUITest extends SeleniumUITest {

	protected MangaList mangaList;

	protected LoginPage loginPage;

	@BeforeEach
	public void setupPOM() {

		mangaList = new MangaList(webDriver);

		loginPage = new LoginPage(webDriver);
	}
}
