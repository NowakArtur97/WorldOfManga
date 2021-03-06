package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.feature.manga.details.MangaDTO;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Author_Tests")
@Tag("Unit_Tests")
@Tag("AuthorController_Tests")
class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorValidator authorValidator;

    @BeforeEach
    private void setUp() {

        AuthorController authorController = new AuthorController(authorService, authorValidator);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void when_load_add_or_update_author_page_should_show_author_form() {

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("FirstName LastName"));

        when(authorService.findAll()).thenReturn(authors);

        assertAll(() -> mockMvc.perform(get("/admin/addOrUpdateAuthor")).andExpect(status().isOk())
                        .andExpect(view().name("views/manga-form")).andExpect(model().attribute("mangaDTO", new MangaDTO()))
                        .andExpect(model().attribute("authorDTO", new AuthorDTO()))
                        .andExpect(model().attribute("authors", authors)),
                () -> verify(authorService, times(1)).findAll(),
                () -> verifyNoMoreInteractions(authorService), () -> verifyNoInteractions(authorValidator));
    }

    @Test
    void when_add_author_with_correct_data_should_save_author() {

        String fullName = "Firstname LastName";

        AuthorDTO authorDTO = new AuthorDTO(fullName);

        assertAll(
                () -> mockMvc
                        .perform(post("/admin/addOrUpdateAuthor").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .flashAttr("authorDTO", authorDTO))
                        .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/addOrUpdateAuthor"))
                        .andExpect(model().hasNoErrors()),
                () -> verify(authorService, times(1)).addOrUpdate(authorDTO),
                () -> verifyNoMoreInteractions(authorService));
    }

    @Test
    void when_add_author_with_incorrect_data_should_show_author_form() {

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
                () -> verify(authorService, times(1)).findAll(), () -> verifyNoMoreInteractions(authorService));
    }
}
