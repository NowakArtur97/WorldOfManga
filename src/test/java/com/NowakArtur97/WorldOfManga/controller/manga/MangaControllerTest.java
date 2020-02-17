package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.validation.manga.MangaValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Controller Tests")
@Tag("MangaController_Tests")
public class MangaControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaController mangaController;

	@Mock
	private MangaService mangaService;

	@Mock
	private MangaTranslationService mangaTranslationService;

	@Mock
	private MangaValidator mangaValidator;

	@Mock
	private AuthorService authorService;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaController).build();
	}

	@Test
	@DisplayName("when load add or update manga page")
	public void when_load_add_or_update_manga_page_should_show_manga_form() {

		List<Author> authors = new ArrayList<>();
		authors.add(new Author("FirstName LastName"));

		when(authorService.findAll()).thenReturn(authors);

		assertAll(() -> mockMvc.perform(get("/admin/addOrUpdateManga")).andExpect(status().isOk())
				.andExpect(view().name("views/manga-form")).andExpect(model().attribute("mangaDTO", new MangaDTO()))
				.andExpect(model().attribute("authorDTO", new AuthorDTO()))
				.andExpect(model().attribute("authors", authors)), () -> verify(authorService, times(1)).findAll());
	}

	@Test
	@DisplayName("when add manga with correct data")
	public void when_add_manga_with_correct_data_should_save_manga() throws LanguageNotFoundException {

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
				.description("English description").build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
				.description("Polish description").build();

		Set<Author> authors = new HashSet<>();
		Author author = new Author("FirstName LastName");
		authors.add(author);

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.authors(authors).build();

		MangaTranslation mangaTranslationEn = MangaTranslation.builder().title("English title")
				.description("English description").build();
		MangaTranslation mangaTranslationPl = MangaTranslation.builder().title("Polish title")
				.description("Polish description").build();

		Set<MangaTranslation> mangaTranslations = new HashSet<>();
		mangaTranslations.add(mangaTranslationEn);
		mangaTranslations.add(mangaTranslationPl);

		mangaDTO.setAuthors(authors);

		when(mangaTranslationService.addOrUpdate(mangaDTO)).thenReturn(mangaTranslations);

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("mangaDTO", mangaDTO))
						.andExpect(status().is3xxRedirection()).andExpect(model().hasNoErrors())
						.andExpect(redirectedUrl("/admin/addOrUpdateManga")),
				() -> verify(mangaTranslationService, times(1)).addOrUpdate(mangaDTO),
				() -> verify(mangaService, times(1)).addOrUpdate(mangaDTO, mangaTranslations));
	}

	@Test
	@DisplayName("when add manga with incorrect data")
	public void when_add_manga_with_incorrect_data_should_show_manga_form() {

		String englishTitle = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";
		String englishDescription = "English description";
		String polishTitle = "Polish title";
		String polishDescription = "";

		List<Author> authors = new ArrayList<>();
		authors.add(new Author("FirstName LastName"));

		when(authorService.findAll()).thenReturn(authors);

		Set<Author> mangaAuthors = new HashSet<>();

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(englishTitle)
				.description(englishDescription).build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(polishTitle)
				.description(polishDescription).build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.authors(mangaAuthors).build();

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("mangaDTO", mangaDTO))
						.andExpect(status().isOk()).andExpect(forwardedUrl("views/manga-form"))
						.andExpect(model().attribute("mangaDTO", mangaDTO))
						.andExpect(model().attribute("authorDTO", new AuthorDTO()))
						.andExpect(model().attribute("authors", authors))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "authors"))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.title"))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.description"))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("enTranslation", hasProperty("title", is(englishTitle)))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("enTranslation", hasProperty("description", is(englishDescription)))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("plTranslation", hasProperty("title", is(polishTitle)))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("plTranslation", hasProperty("description", is(polishDescription))))),
				() -> verify(mangaTranslationService, never()).addOrUpdate(mangaDTO),
				() -> verify(authorService, times(1)).findAll());
	}
}
