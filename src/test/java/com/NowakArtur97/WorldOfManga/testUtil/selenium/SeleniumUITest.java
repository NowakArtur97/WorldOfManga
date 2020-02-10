package com.NowakArtur97.WorldOfManga.testUtil.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;

public class SeleniumUITest {

	@Getter
	protected static WebDriver webDriver;

	@BeforeAll
	public static void setUp() {

		WebDriverManager.chromedriver().setup();
	}

	@AfterAll
	public static void tearDown() {

		if (webDriver != null) {

			webDriver.quit();
		}
	}
}