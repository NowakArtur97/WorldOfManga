package com.NowakArtur97.WorldOfManga.feature.language;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByLocale(String locale);
}
