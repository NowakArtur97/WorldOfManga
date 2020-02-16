package com.NowakArtur97.WorldOfManga.controller.author;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.validation.author.AuthorValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Author Controller Tests")
@Tag("AuthorController_Tests")
public class AuthorControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private AuthorController authorController;

	@Mock
	private AuthorService authorService;

	@Mock
	private AuthorValidator authorValidator;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
	}

	@Test
	@DisplayName("when load add or update author page")
	public void when_load_add_or_update_author_page_should_show_author_form() {

		assertAll(() -> mockMvc.perform(get("/admin/addOrUpdateAuthor")).andExpect(status().isOk())
				.andExpect(view().name("views/manga-form")).andExpect(model().attribute("mangaDTO", new MangaDTO()))
				.andExpect(model().attribute("authorDTO", new AuthorDTO())));
	}

	@Test
	@DisplayName("when add author with correct data")
	public void when_add_author_with_correct_data_should_save_author() {

		String fullName = "Firstname LastName";

		AuthorDTO authorDTO = new AuthorDTO(fullName);

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateAuthor").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("authorDTO", authorDTO))
						.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/addOrUpdateAuthor"))
						.andExpect(model().hasNoErrors()),
				() -> verify(authorService, times(1)).addOrUpdate(authorDTO));
	}

	@Test
	@DisplayName("when add author with incorrect data")
	public void when_add_author_with_incorrect_data_should_show_author_form() {

		String fullName = "";

		AuthorDTO authorDTO = new AuthorDTO(fullName);

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateAuthor").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("authorDTO", authorDTO))
						.andExpect(status().isOk()).andExpect(forwardedUrl("views/manga-form"))
						.andExpect(model().attribute("authorDTO", authorDTO))
						.andExpect(model().attribute("mangaDTO", new MangaDTO()))
						.andExpect(model().attributeHasFieldErrors("authorDTO", "fullName"))
						.andExpect(model().attribute("authorDTO", hasProperty("fullName", is(fullName)))),
				() -> verify(authorService, never()).addOrUpdate(authorDTO));
	}
}