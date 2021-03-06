package com.NowakArtur97.WorldOfManga.feature.manga.rating;

import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaService;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("Unit_Tests")
@Tag("MangaRatingService_Tests")
class MangaRatingServiceTest {

    private MangaRatingService mangaRatingService;

    @Mock
    private MangaRatingRepository mangaRatingRepository;

    @Mock
    private MangaService mangaService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {

        mangaRatingService = new MangaRatingService(mangaRatingRepository, mangaService, userService);
    }

    @Test
    void when_rate_manga_for_first_time_should_update_rating() throws IOException, MangaNotFoundException {

        Long mangaId = 1L;

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        String username = "principal";

        User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        int ratingExpected = 5;

        MangaRating mangaRatingExpected = new MangaRating();

        when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected);

        MangaRating mangaRatingActual = mangaRatingService.rateManga(mangaId, ratingExpected);

        assertAll(
                () -> assertEquals(mangaExpected, mangaRatingActual.getManga(),
                        () -> "should return manga rating with manga: " + mangaExpected + ", but was: "
                                + mangaRatingActual.getManga()),
                () -> assertEquals(userExpected, mangaRatingActual.getUser(),
                        () -> "should return manga rating with user: " + userExpected + ", but was: "
                                + mangaRatingActual.getUser()),
                () -> assertEquals(ratingExpected, mangaRatingActual.getRating(),
                        () -> "should return manga rating with rating: " + mangaRatingExpected.getRating()
                                + ", but was: " + mangaRatingActual.getRating()),
                () -> verify(mangaService, times(1)).findById(mangaId),
                () -> verify(userService, times(1)).loadLoggedInUsername());
    }

    @Test
    void when_rate_manga_for_another_time_should_update_rating() throws IOException, MangaNotFoundException {

        Long mangaId = 1L;

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        String username = "principal";

        User userExpected = User.builder().username(username).firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        int ratingExpected = 5;

        MangaRating mangaRatingExpected = MangaRating.builder().manga(mangaExpected).user(userExpected)
                .rating(ratingExpected).build();

        when(mangaService.findById(mangaId)).thenReturn(mangaExpected);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected);

        MangaRating mangaRatingActual = mangaRatingService.rateManga(mangaId, ratingExpected);

        assertAll(
                () -> assertEquals(mangaRatingExpected.getManga(), mangaRatingActual.getManga(),
                        () -> "should return manga rating with manga: " + mangaRatingExpected.getManga() + ", but was: "
                                + mangaRatingActual.getManga()),
                () -> assertEquals(mangaRatingExpected.getUser(), mangaRatingActual.getUser(),
                        () -> "should return manga rating with user: " + mangaRatingExpected.getUser() + ", but was: "
                                + mangaRatingActual.getUser()),
                () -> assertEquals(mangaRatingExpected.getRating(), mangaRatingActual.getRating(),
                        () -> "should return manga rating with rating: " + mangaRatingExpected.getRating()
                                + ", but was: " + mangaRatingActual.getRating()),
                () -> verify(mangaService, times(1)).findById(mangaId),
                () -> verify(userService, times(1)).loadLoggedInUsername());
    }

    @Test
    void whne_find_existing_manga_rating_by_user_and_manga_should_return_entity() throws IOException {

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        int ratingExpected = 5;

        MangaRating mangaRatingExpected = MangaRating.builder().manga(mangaExpected).user(userExpected)
                .rating(ratingExpected).build();

        when(mangaRatingRepository.findByUserAndManga(userExpected, mangaExpected))
                .thenReturn(Optional.of(mangaRatingExpected));

        MangaRating mangaRatingActual = mangaRatingService.findByUserAndManga(userExpected, mangaExpected);

        assertAll(
                () -> assertEquals(mangaRatingExpected.getManga(), mangaRatingActual.getManga(),
                        () -> "should return manga rating with manga: " + mangaRatingExpected.getManga() + ", but was: "
                                + mangaRatingActual.getManga()),
                () -> assertEquals(mangaRatingExpected.getUser(), mangaRatingActual.getUser(),
                        () -> "should return manga rating with user: " + mangaRatingExpected.getUser() + ", but was: "
                                + mangaRatingActual.getUser()),
                () -> assertEquals(mangaRatingExpected.getRating(), mangaRatingActual.getRating(),
                        () -> "should return manga rating with rating: " + mangaRatingExpected.getRating()
                                + ", but was: " + mangaRatingActual.getRating()),
                () -> verify(mangaRatingRepository, times(1)).findByUserAndManga(userExpected, mangaExpected));
    }

    @Test
    void whne_find_not_existing_manga_rating_by_user_and_manga_should_return_new_manga_rating()
            throws IOException {

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Author authorExpected = new Author("FirsName LastName");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        User userExpected = User.builder().username("username").firstName("first name").lastName("last name")
                .password("user").email("user@email.com").isEnabled(true).build();

        int defaultRatingExpected = 0;

        MangaRating mangaRatingExpected = new MangaRating();

        when(mangaRatingRepository.findByUserAndManga(userExpected, mangaExpected))
                .thenReturn(Optional.of(mangaRatingExpected));

        MangaRating mangaRatingActual = mangaRatingService.findByUserAndManga(userExpected, mangaExpected);

        assertAll(
                () -> assertNull(mangaRatingActual.getManga(),
                        () -> "should return manga rating with manga as null, but was: "
                                + mangaRatingActual.getManga()),
                () -> assertNull(mangaRatingActual.getUser(),
                        () -> "should return manga rating with user as null, but was: " + mangaRatingActual.getUser()),
                () -> assertEquals(defaultRatingExpected, mangaRatingActual.getRating(),
                        () -> "should return manga rating with default rating: " + defaultRatingExpected + ", but was: "
                                + mangaRatingActual.getRating()),
                () -> verify(mangaRatingRepository, times(1)).findByUserAndManga(userExpected, mangaExpected));
    }
}
