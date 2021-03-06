package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
class MangaRatingController {

    private final MangaRatingService mangaRatingService;

    @GetMapping(path = "/rateManga")
    String rateManga(HttpServletRequest request, @RequestParam("id") Long mangaId,
                     @RequestParam("rating") int rating) throws MangaNotFoundException {

        mangaRatingService.rateManga(mangaId, rating);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }
}
