package com.NowakArtur97.WorldOfManga.testUtils;

import lombok.Getter;

public enum LanguageVersion {

	ENG("?lang=en"), PL("?lang=pl");

	@Getter
	private final String langUrl;

	private LanguageVersion(String langUrl) {
		this.langUrl = langUrl;
	}
}
