package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.language.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorDTO;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorService;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenreService;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
class MangaController {

    private final MangaService mangaService;

    private final MangaTranslationService mangaTranslationService;

    private final MangaGenreService mangaGenreService;

    private final MangaValidator mangaValidator;

    private final AuthorService authorService;

    private final LocaleResolver cookieLocaleResolver;

    @GetMapping(path = "/addOrUpdateManga")
    String showAddMangaPage(Model theModel, HttpServletRequest request) {

        theModel.addAttribute("mangaDTO", new MangaDTO());
        theModel.addAttribute("authorDTO", new AuthorDTO());
        theModel.addAttribute("authors", authorService.findAll());
        theModel.addAttribute("genres", mangaGenreService.findAll());

        Locale locale = cookieLocaleResolver.resolveLocale(request);

        if (locale != null) {
            theModel.addAttribute("locale", locale.getLanguage());
        }

        return "views/manga-form";
    }

    @GetMapping(path = "/addOrUpdateManga/{id}")
    String showEditMangaPage(Model theModel, @PathVariable("id") Long mangaId, HttpServletRequest request)
            throws MangaNotFoundException {

        MangaDTO mangaToEdit = mangaService.getMangaDTOById(mangaId);
        theModel.addAttribute("mangaDTO", mangaToEdit);
        theModel.addAttribute("authorDTO", new AuthorDTO());
        theModel.addAttribute("authors", authorService.findAll());
        theModel.addAttribute("genres", mangaGenreService.findAll());

        Locale locale = cookieLocaleResolver.resolveLocale(request);

        if (locale != null) {
            theModel.addAttribute("locale", locale.getLanguage());
        }

        return "views/manga-form";
    }

    @PostMapping(path = "/addOrUpdateManga")
    String processAddMangaPage(HttpServletRequest request, Model theModel,
                               @ModelAttribute("mangaDTO") @Valid MangaDTO mangaDTO, BindingResult result)
            throws LanguageNotFoundException, MangaNotFoundException {

        mangaValidator.validate(mangaDTO, result);

        Locale locale = cookieLocaleResolver.resolveLocale(request);

        if (locale != null) {
            theModel.addAttribute("locale", locale.getLanguage());
        }

        if (result.hasErrors()) {

            theModel.addAttribute("authorDTO", new AuthorDTO());
            theModel.addAttribute("mangaDTO", mangaDTO);
            theModel.addAttribute("authors", authorService.findAll());
            theModel.addAttribute("genres", mangaGenreService.findAll());

            return "views/manga-form";
        }

        Manga manga = mangaTranslationService.addOrUpdate(mangaDTO);

        mangaService.addOrUpdate(mangaDTO, manga);

        return "redirect:/admin/addOrUpdateManga";
    }

    @GetMapping(path = "/deleteManga/{id}")
    String deleteManga(HttpServletRequest request, @PathVariable("id") Long mangaId)
            throws MangaNotFoundException {

        mangaService.deleteManga(mangaId);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }
}
