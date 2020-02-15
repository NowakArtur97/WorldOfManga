package com.NowakArtur97.WorldOfManga.controller.manga;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
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

	private final MangaValidator mangaValidator;

	@GetMapping(path = "/addOrUpdateManga")
	public String showAddMangaPage(Model theModel) {

		theModel.addAttribute("mangaDTO", new MangaDTO());
		theModel.addAttribute("authorDTO", new AuthorDTO());

		return "views/manga-form";
	}

	@PostMapping(path = "/addOrUpdateManga")
	public String processAddMangaPage(Model theModel, @ModelAttribute("mangaDTO") @Valid MangaDTO mangaDTO,
			BindingResult result) throws LanguageNotFoundException {

		mangaValidator.validate(mangaDTO, result);

		if (result.hasErrors()) {

			theModel.addAttribute("authorDTO", new AuthorDTO());
			theModel.addAttribute("mangaDTO", mangaDTO);

			return "views/manga-form";
		}

		Set<MangaTranslation> mangaTranslations = mangaTranslationService.addOrUpdate(mangaDTO);

		mangaService.addOrUpdate(mangaDTO, mangaTranslations);

		return "redirect:/admin/addOrUpdateManga";
	}
}