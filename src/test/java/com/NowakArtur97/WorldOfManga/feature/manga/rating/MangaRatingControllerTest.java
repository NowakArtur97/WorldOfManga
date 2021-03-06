package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("Unit_Tests")
@Tag("MangaRatingController_Tests")
class MangaRatingControllerTest {

    private MockMvc mockMvc;

    private MangaRatingController mangaRatingController;

    @Mock
    private MangaRatingService mangaRatingService;

    @BeforeEach
    void setUp() {

        mangaRatingController = new MangaRatingController(mangaRatingService);
        mockMvc = MockMvcBuilders.standaloneSetup(mangaRatingController).build();
    }

    @Test
    void when_rate_manga_should_rate_manga_and_redirect_to_last_page() {

        Long mangaId = 1L;
        int rating = 5;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/rateManga")
                .param("id", mangaId.toString()).param("rating", String.valueOf(rating)).header("Referer", "/");

        assertAll(
                () -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
                () -> verify(mangaRatingService, times(1)).rateManga(mangaId, rating));
    }
}
