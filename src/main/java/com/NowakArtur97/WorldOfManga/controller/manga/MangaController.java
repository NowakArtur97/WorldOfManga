package com.NowakArtur97.WorldOfManga.controller.manga;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.service.api.MangaGenreService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.validation.manga.MangaValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MangaController {

	private final MangaService mangaService;

	private final MangaTranslationService mangaTranslationService;

	private final MangaGenreService mangaGenreService;

	private final MangaValidator mangaValidator;

	private final AuthorService authorService;

	private final LocaleResolver cookieLocaleResolver;
	
	@GetMapping(path = "/addOrUpdateManga")
	public String showAddMangaPage(Model theModel, HttpServletRequest request) {

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
	public String showEditMangaPage(Model theModel, @PathVariable("id") Long mangaId, HttpServletRequest request) throws MangaNotFoundException {

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
	public String processAddMangaPage(HttpServletRequest request, Model theModel, @ModelAttribute("mangaDTO") @Valid MangaDTO mangaDTO,
			BindingResult result) throws LanguageNotFoundException, MangaNotFoundException {

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
	public String deleteManga(HttpServletRequest request, @PathVariable("id") Long mangaId)
			throws MangaNotFoundException {

		mangaService.deleteManga(mangaId);

		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
}
