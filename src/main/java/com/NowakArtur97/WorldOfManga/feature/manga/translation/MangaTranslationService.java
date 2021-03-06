package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import com.NowakArtur97.WorldOfManga.feature.language.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.language.Language;
import com.NowakArtur97.WorldOfManga.feature.language.LanguageService;
import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaDTO;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MangaTranslationService {

    private final MangaTranslationRepository mangaTranslationRepository;

    private final MangaTranslationMapper mangaTranslationMapper;

    private final LanguageService languageService;

    private final MangaService mangaService;

    public boolean isTitleAlreadyInUse(String title) {

        return mangaTranslationRepository.existsMangaTranslationByTitle(title);
    }

    public Manga addOrUpdate(MangaDTO mangaDTO) throws LanguageNotFoundException, MangaNotFoundException {

        Manga manga;

        MangaTranslation mangaTranslationEn = mangaTranslationMapper
                .mapMangaTranslationDTOToMangaTranslation(mangaDTO.getEnTranslation());

        MangaTranslation mangaTranslationPl = mangaTranslationMapper
                .mapMangaTranslationDTOToMangaTranslation(mangaDTO.getPlTranslation());

        if (mangaDTO.getId() != null) {

            manga = editTranslation(mangaDTO, mangaTranslationEn, mangaTranslationPl);

        } else {

            manga = addTranslation(mangaTranslationEn, mangaTranslationPl);
        }

        return manga;
    }

    private Manga addTranslation(MangaTranslation mangaTranslationEn,
                                 MangaTranslation mangaTranslationPl) throws LanguageNotFoundException {

        Language en = languageService.findByLocale("en");
        Language pl = languageService.findByLocale("pl");

        Manga manga = new Manga();

        en.addTranslation(mangaTranslationEn);
        pl.addTranslation(mangaTranslationPl);

        manga.addTranslation(mangaTranslationEn);
        manga.addTranslation(mangaTranslationPl);

        return manga;
    }

    private Manga editTranslation(MangaDTO mangaDTO, MangaTranslation mangaTranslationEn,
                                  MangaTranslation mangaTranslationPl) throws MangaNotFoundException {

        Manga manga = mangaService.findById(mangaDTO.getId());

        MangaTranslation enTranslation = manga.getTranslations().get(Manga.EN_TRANSLATION_INDEX);
        MangaTranslation plTranslation = manga.getTranslations().get(Manga.PL_TRANSLATION_INDEX);

        enTranslation.setTitle(mangaTranslationEn.getTitle());
        enTranslation.setDescription(mangaTranslationEn.getDescription());
        plTranslation.setTitle(mangaTranslationPl.getTitle());
        plTranslation.setDescription(mangaTranslationPl.getDescription());

        return manga;
    }
}
