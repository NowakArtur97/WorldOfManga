package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.service.api.MangaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Favourite Controller Tests")
@Tag("MangaFavouriteController_Tests")
public class MangaFavouriteControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaFavouriteController mangaFavouriteController;

	@Mock
	private MangaService mangaService;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaFavouriteController).build();
	}

	@Test
	@DisplayName("when add or remove manga from favourites")
	public void when_add_or_remove_manga_from_favourites_should_redirect_to_last_page() {

		Long mangaId = 1L;

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.get("/auth/addOrRemoveFromFavourites/{id}", mangaId).header("Referer", "/");

		assertAll(
				() -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
				() -> verify(mangaService, times(1)).addOrRemoveFromFavourites(mangaId));
	}
}
