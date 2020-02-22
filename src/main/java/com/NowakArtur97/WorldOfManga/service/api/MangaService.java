package com.NowakArtur97.WorldOfManga.service.api;

import java.util.List;
import java.util.Set;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

public interface MangaService {

	Manga addOrUpdate(MangaDTO mangaDTO, Set<MangaTranslation> mangaTranslations);

	List<Manga> findAll();

	Manga findById(Long id) throws MangaNotFoundException;

	Long rateManga(Long id, Long rating);
}
