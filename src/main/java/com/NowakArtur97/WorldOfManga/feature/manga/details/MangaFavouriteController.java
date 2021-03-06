package com.NowakArtur97.WorldOfManga.feature.manga.details;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
class MangaFavouriteController {

    private final MangaService mangaService;

    @GetMapping(path = "/addOrRemoveFromFavourites/{id}")
    String addOrRemoveFromFavourites(HttpServletRequest request, @PathVariable("id") Long mangaId)
            throws MangaNotFoundException {

        mangaService.addOrRemoveFromFavourites(mangaId);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }
}
